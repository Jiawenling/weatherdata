package com.example.weatherRequest.service;

import com.example.weatherRequest.WeatherRepo.WeatherRepo;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherCacheService {

    @Autowired
    WeatherRepo weatherRepo;

    public void save(String city, JsonObject jsonObject){
        weatherRepo.save(city, jsonObject.toString());

    }

    public JsonObject get(String city){
        weatherRepo.get(city);
    }
}
