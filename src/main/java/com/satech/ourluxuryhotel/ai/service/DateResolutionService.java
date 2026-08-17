package com.satech.ourluxuryhotel.ai.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class DateResolutionService {

    public LocalDate resolve(String expression) {

        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException(
                    "Date is required."
            );
        }

        String value = expression
                .trim()
                .toLowerCase();

        LocalDate today = LocalDate.now();

        return switch (value) {

            case "today" ->
                    today;

            case "tomorrow" ->
                    today.plusDays(1);

            case "day after tomorrow" ->
                    today.plusDays(2);

            default ->
                    LocalDate.parse(value);
        };
    }

    public LocalDate resolveRelativeTo(
            String expression,
            LocalDate baseDate) {

        String value = expression
                .trim()
                .toLowerCase();

        if (value.matches("\\d+ days?")) {

            int days = Integer.parseInt(
                    value.replaceAll("[^0-9]", "")
            );

            return baseDate.plusDays(days);
        }

        return resolve(value);
    }
}
