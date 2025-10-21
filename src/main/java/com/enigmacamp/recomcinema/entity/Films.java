package com.enigmacamp.recomcinema.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Films {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private Integer year;
    @Column(length = 2000)
    private String description;
    private boolean isDeleted = false;
}
