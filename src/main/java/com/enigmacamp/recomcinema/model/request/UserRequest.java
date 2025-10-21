package com.enigmacamp.recomcinema.model.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<String> hobbies;
    private String personalities;
}
