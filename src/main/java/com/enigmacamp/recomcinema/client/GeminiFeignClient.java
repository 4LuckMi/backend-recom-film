package com.enigmacamp.recomcinema.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "gemini-api",
        url = "${gemini.api.base-url}"
)
public interface GeminiFeignClient {
    @PostMapping("/v1/models/{model}:generateContent")    Map<String, Object> getRawRecommendations(
            @PathVariable("model") String model,
            @RequestBody Map<String, Object> request
    );
}
