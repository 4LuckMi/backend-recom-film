package com.enigmacamp.recomcinema.config;

import feign.Logger;
import feign.RequestInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GeminiFeignConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Bean
    public Logger.Level feignLogggerLevel() {
        return Logger.Level.BASIC;
    }

    // interceptor
    @Bean
    public RequestInterceptor requestInterceptor(@Value("${gemini.api.key}") String apiKey) {
        return template -> template
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
}
