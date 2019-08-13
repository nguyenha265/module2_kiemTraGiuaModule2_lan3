package com.codegym.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateConverter implements Converter< String, LocalDate> {
    @Override
    public LocalDate convert(String Date) {
        try {
            return LocalDate.parse(Date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date format. Please use this pattern\""
                    + DateTimeFormatter.ISO_LOCAL_DATE + "\"");
        }
    }
}
