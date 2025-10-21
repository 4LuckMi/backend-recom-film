package com.enigmacamp.recomcinema.controller;

import com.enigmacamp.recomcinema.model.request.UserRequest;
import com.enigmacamp.recomcinema.model.response.UserResponse;
import com.enigmacamp.recomcinema.service.GeminiService;
import com.enigmacamp.recomcinema.service.UserService;
import com.enigmacamp.recomcinema.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final GeminiService geminiService;

    @PostMapping
    public ResponseEntity<?> createHandler(
            @RequestBody UserRequest request){
        var user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<?> findAllHandler(UserResponse response){
        var users = userService.findAllUser(response);
        return ResponseUtil.buildSingleResponse(
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase(),
                users
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdHandler(@PathVariable Long id){
        var user = userService.findUserById(id);
        return ResponseUtil.buildSingleResponse(
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase(),
                user
        );
    }

    @PutMapping
    public ResponseEntity<?> updateHandler(@RequestBody UserRequest request){
        var user = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHandler(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/recommendations")
    public ResponseEntity<?> generateRecommendations(@PathVariable Long id) {
        var response = geminiService.generateRecommendations(id);

        return ResponseUtil.buildSingleResponse(
                HttpStatus.OK,
                "Recommendations generated successfully",
                response
        );
    }
}
