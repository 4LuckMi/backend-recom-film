package com.enigmacamp.recomcinema.service;

import com.enigmacamp.recomcinema.entity.Films;
import com.enigmacamp.recomcinema.exception.ResourceNotFoundException;
import com.enigmacamp.recomcinema.model.request.FilmRequest;
import com.enigmacamp.recomcinema.model.response.FilmResponse;
import com.enigmacamp.recomcinema.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FilmService {
    private final FilmRepository filmRepository;
    public Films addFilm(FilmRequest request){
        log.debug("addFilm() - request: {}", request);
        Films save = Films.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .year(request.getYear())
                .description(request.getDescription())
                .build();
        return filmRepository.save(save);
    }

    public Page<FilmResponse> findAllFilm(Pageable pageable){
        return filmRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Films findFilmById(Long id){
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found for this id : " + id));

    }

    public Films updateFilm(FilmRequest request){
        var film = findFilmById(request.getId());
        film.setTitle(request.getTitle());
        film.setGenre(request.getGenre());
        film.setYear(request.getYear());
        film.setDescription(request.getDescription());
        return filmRepository.save(film);
    }

    public void deleteFilm(Long id){
        var p = filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film not found for this id : " + id));
        p.setDeleted(true);
        filmRepository.save(p);

    }

    private FilmResponse mapToResponse(Films film) {
        return FilmResponse.builder()
                .id(film.getId())
                .title(film.getTitle())
                .genre(film.getGenre())
                .year(film.getYear())
                .description(film.getDescription())
                .build();
    }
}
