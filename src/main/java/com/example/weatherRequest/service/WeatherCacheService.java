package com.example.weatherRequest.service;

import com.example.weatherRequest.WeatherRepo.WeatherRepo;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class WeatherCacheService {

    @Autowired
    WeatherRepo weatherRepo;

    public void save(String city, JsonObject jsonObject) {
        weatherRepo.save(city, jsonObject.toString());
    }

    public Optional<JsonObject> get(String city) {
        Optional<String> jsonString = Optional.ofNullable(weatherRepo.get(city));
        if(jsonString.isEmpty()){
            return Optional.empty();
        }

        try (InputStream is = new ByteArrayInputStream(jsonString.get().getBytes())) {
            JsonReader reader = Json.createReader(is);
            return Optional.of(reader.readObject());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }
}
