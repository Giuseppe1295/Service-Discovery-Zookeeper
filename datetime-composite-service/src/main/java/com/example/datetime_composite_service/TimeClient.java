package com.example.datetime_composite_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TimeClient {

    private final WebClient webClient;

    public TimeClient(WebClient.Builder webClientBuilder, @Value("${time.service.url}") String timeServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(timeServiceUrl).build();
    }

    public Mono<String> getCurrentTime() {
        return webClient.get()
                .uri("/time")
                .retrieve()
                .bodyToMono(String.class);
    }
}
