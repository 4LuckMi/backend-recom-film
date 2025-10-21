package com.enigmacamp.recomcinema.service;

import com.enigmacamp.recomcinema.entity.Users;
import com.enigmacamp.recomcinema.exception.ResourceNotFoundException;
import com.enigmacamp.recomcinema.model.request.UserRequest;
import com.enigmacamp.recomcinema.model.response.UserResponse;
import com.enigmacamp.recomcinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    // inject from repo
    private final UserRepository userRepository;

    //create user
    @Transactional(rollbackFor = Exception.class)
    public Users createUser(UserRequest request){
        log.debug("createUser() - request: {}", request);

        Users user = Users.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .hobbies(request.getHobbies())
                .personalities(request.getPersonalities())
                .build();
        return userRepository.save(user);
    }

    public List<Users> findAllUser(UserResponse response) {
        return userRepository.findAll();
    }

    public Users findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :"));
    }

    public Users updateUser(UserRequest request){
        var ui = findUserById(request.getId());
        ui.setName(request.getName());
        ui.setBirthDate(request.getBirthDate());
        ui.setHobbies(request.getHobbies());
        ui.setPersonalities(request.getPersonalities());
        return userRepository.save(ui);
    }

    public void deleteUser(Long id){
        var p = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :"));
        p.setDeleted(true);
        userRepository.save(p);
    }

    @Transactional(readOnly = true)
    public Users findByUsernameEntity(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this username : " + username));
    }



}
