package com.enigmacamp.recomcinema.repository;

import com.enigmacamp.recomcinema.entity.Recommendations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomRepository extends JpaRepository<Recommendations, Long> {
    List<Recommendations> findByUserProfileId(Long userId);
}
