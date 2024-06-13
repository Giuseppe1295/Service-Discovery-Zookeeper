package com.example.datetime_composite_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DateClient {

    private final WebClient webClient;

    public DateClient(WebClient.Builder webClientBuilder, @Value("${date.service.url}") String dateServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(dateServiceUrl).build();
    }

    public Mono<String> getCurrentDate() {
        return webClient.get()
                .uri("/date")
                .retrieve()
                .bodyToMono(String.class);
    }
}
