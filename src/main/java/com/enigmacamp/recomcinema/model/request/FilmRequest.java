package com.enigmacamp.recomcinema.model.request;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FilmRequest {
    private Long id;
    private String title;
    private String genre;
    private Integer year;
    private String description;
}
