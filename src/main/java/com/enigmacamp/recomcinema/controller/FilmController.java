package com.enigmacamp.recomcinema.controller;

import com.enigmacamp.recomcinema.entity.Films;
import com.enigmacamp.recomcinema.model.request.FilmRequest;
import com.enigmacamp.recomcinema.service.FilmService;
import com.enigmacamp.recomcinema.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<?> createHandler(
            @RequestBody FilmRequest request){
        var film = filmService.addFilm(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(film);
    }

    @GetMapping
    public ResponseEntity<?> findAllHandler(
            Pageable pageable
    ){
        var page = filmService.findAllFilm(pageable);
        return ResponseUtil.buildPageResponse(
                HttpStatus.OK,
                "Film list retrieved successfully",
                page
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdHandler(@PathVariable Long id){
        var film = filmService.findFilmById(id);
        return ResponseUtil.buildSingleResponse(
                HttpStatus.OK,
                "Film retrieved successfully",
                film
        );
    }

    @PutMapping
    public ResponseEntity<?> updateHandler(@RequestBody FilmRequest request){
        var film = filmService.updateFilm(request);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHandler(@PathVariable Long id){
        filmService.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
