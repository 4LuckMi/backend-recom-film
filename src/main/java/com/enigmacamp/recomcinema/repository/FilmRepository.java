package com.enigmacamp.recomcinema.repository;

import com.enigmacamp.recomcinema.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Films, Long> {

}
