package com.example.books_tracker.controller;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Genres;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Authors> parseAuthors(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<List<Authors>>() {});
    }

    public static List<Genres> parseGenres(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<List<Genres>>() {});
    }
}
