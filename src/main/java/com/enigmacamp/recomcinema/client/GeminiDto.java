package com.enigmacamp.recomcinema.client;

import lombok.*;

import java.util.List;

public class GeminiDto {
    // Request ke Gemini API
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request {
        private String prompt; // contoh: "Berdasarkan hobi berikut: [music, travel], rekomendasikan 3 film..."
    }

    // Response dari Gemini API
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {
        private List<FilmRecommendation> recommendations;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class FilmRecommendation {
        private String title;
        private String genre;
        private int year;
    }
}
