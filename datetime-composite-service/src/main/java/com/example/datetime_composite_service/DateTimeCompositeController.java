package com.example.datetime_composite_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DateTimeCompositeController {

    private final DateTimeCompositeService dateTimeCompositeService;

    @Autowired
    public DateTimeCompositeController(DateTimeCompositeService dateTimeCompositeService) {
        this.dateTimeCompositeService = dateTimeCompositeService;
    }

    @GetMapping("/datetime")
    public Mono<String> getDateTime() {
        return dateTimeCompositeService.getDateTime();
    }
}
