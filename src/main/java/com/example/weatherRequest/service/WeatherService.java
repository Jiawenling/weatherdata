package com.example.weatherRequest.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.example.weatherRequest.Weather;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import static com.example.weatherRequest.Constants.*;

@Service
public class WeatherService {

    // private final String appId ;

    // public WeatherService(){
    //     String id = System.getenv(ENV_OPENWEATHERMAP_KEY);
    //     if(id!=null & (id.trim().length()>0)){
    //         appId = id;
    //     } else {
    //         appId = "noID";
    //     }
    // }

    public List<Weather> getWeatherInfo(String city) throws IOException{
        RestTemplate template = new RestTemplate();
        String url = UriComponentsBuilder
            .fromUriString("http://api.openweathermap.org/data/2.5/weather")
            .queryParam("q", city)
            .queryParam("appid", "5b86734006e2bbe73f6fd073ae13d9ac")
            .queryParam("units","metric")
            .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<String> resp =  template.exchange(req, String.class);

        try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            JsonArray readings = data.getJsonArray("weather");
            final String cityName = data.getString("name");
            float temperature = (float)data.getJsonObject("main").getJsonNumber("temp").doubleValue();
            return readings.stream()
                .map(v -> (JsonObject)v)
                .map(Weather::create)
                .map(w -> {
                    w.setCityName(cityName);
                    w.setTemperature(temperature);
                    return w;
                })
                .collect(Collectors.toList());
        }
    }
}

   

    
    

