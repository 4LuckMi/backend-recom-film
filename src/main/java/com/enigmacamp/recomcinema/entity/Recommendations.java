package com.enigmacamp.recomcinema.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private Users userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = true)
    private Films film; // optional if matched to DB film

    @Column(columnDefinition = "text")
    private String aiJson; // raw JSON returned from AI

    private Instant recommendedAt = Instant.now();

}
