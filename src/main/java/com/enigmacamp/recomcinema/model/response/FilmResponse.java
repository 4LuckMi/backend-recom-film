package com.enigmacamp.recomcinema.model.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FilmResponse {
    private Long id;
    private String title;
    private String genre;
    private Integer year;
    private String description;
}
