package com.example.weatherRequest.controller;

import com.example.weatherRequest.Weather;
import com.example.weatherRequest.service.WeatherCacheService;
import com.example.weatherRequest.service.WeatherService;
import jakarta.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = "application/json")
public class WeatherRestController {

    Logger logger = LoggerFactory.getLogger(WeatherRestController.class);

    @Autowired
    WeatherService weatherService;

    @Autowired
    WeatherCacheService weatherCacheService;


    @GetMapping("/{city}")
    public ResponseEntity<String> showWeather(@PathVariable String city) {


        JsonObject weatherResults;
        try{

            Optional<JsonObject> opt = weatherCacheService.get(city);
            if(opt.isPresent()){
                logger.info("City information retrieved from cache");
                weatherResults = opt.get();

            } else{

                weatherResults = weatherService.getWeatherObjectInJson(weatherService.getWeatherinJson(city));
                weatherCacheService.save(city, weatherResults);
                logger.info("City information has been cached");
            }

            return ResponseEntity.ok(weatherResults.toString());
        } catch(IOException e){
            logger.error("city not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No results found");
        }


    }
}
