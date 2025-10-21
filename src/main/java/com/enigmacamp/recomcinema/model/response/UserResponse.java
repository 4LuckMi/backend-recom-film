package com.enigmacamp.recomcinema.model.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponse {
    private String name;
    private LocalDate birthDate;
    private List<String> hobbies;
    private String personality;
}
