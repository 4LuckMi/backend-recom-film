package com.enigmacamp.recomcinema.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusResponse {
    private Integer statusCode;
    private String message;
}
