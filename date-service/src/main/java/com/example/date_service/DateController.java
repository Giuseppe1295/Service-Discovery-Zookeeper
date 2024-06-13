package com.example.date_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DateController {

    @GetMapping("/date")
    public String getCurrentDate() {
        return LocalDate.now().toString();
    }
}
