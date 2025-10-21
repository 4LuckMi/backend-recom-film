package com.enigmacamp.recomcinema.service;

import com.enigmacamp.recomcinema.client.GeminiDto;
import com.enigmacamp.recomcinema.client.GeminiFeignClient;
import com.enigmacamp.recomcinema.exception.ResourceNotFoundException;
import com.enigmacamp.recomcinema.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final GeminiFeignClient geminiFeignClient;
    private final UserRepository userProfileRepository;

    @Value("${gemini.api.model}")
    private String geminiModel;

    @Transactional
    public GeminiDto.Response generateRecommendations(Long userId) {
        var user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String hobbiesList = String.join(", ", user.getHobbies());
        String prompt = "Berdasarkan hobi berikut: [" + hobbiesList +
                "], rekomendasikan 3 film menarik dengan genre yang sesuai. " +
                "Formatkan hasil dalam JSON dengan field title, genre, dan year.";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        ObjectMapper mapper = new ObjectMapper();
        GeminiDto.Response response;
        Map<String, Object> geminiResponse = geminiFeignClient.getRawRecommendations(geminiModel, requestBody);
        try {
            String textResponse = extractTextFromGeminiResponse(geminiResponse);

            textResponse = textResponse
                    .replaceAll("(?s)^.*?\\[", "[")  // buang teks sebelum [
                    .replaceAll("]([^]]*)$", "]");   // buang teks setelah ]

            List<GeminiDto.FilmRecommendation> films =
                    mapper.readValue(textResponse, new TypeReference<List<GeminiDto.FilmRecommendation>>() {});

            response = GeminiDto.Response.builder()
                    .recommendations(films)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }

        String recommendedFilms = response.getRecommendations().stream()
                .map(GeminiDto.FilmRecommendation::getTitle)
                .collect(Collectors.joining(", "));
        user.setRecommendations(recommendedFilms);
        userProfileRepository.save(user);

        return response;
    }

    private String extractTextFromGeminiResponse(Map<String, Object> response) {
        try {
            var candidates = (List<Map<String, Object>>) response.get("candidates");
            var content = (Map<String, Object>) candidates.get(0).get("content");
            var parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            throw new RuntimeException("Invalid Gemini response format", e);
        }
    }
}
