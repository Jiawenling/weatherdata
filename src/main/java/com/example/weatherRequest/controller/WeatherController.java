package com.example.weatherRequest.controller;

import java.io.IOException;

import com.example.weatherRequest.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/search", produces = MediaType.TEXT_HTML_VALUE)
public class WeatherController {

    @Autowired
    WeatherService weatherSvc;
    
    @PostMapping("result")
    public String getWeatherInfo(@RequestBody MultiValueMap<String, String> form, Model model) throws IOException{
        String city = form.getFirst("searchField");
        model.addAttribute("result", weatherSvc.getWeatherInfo(weatherSvc.getWeatherinJson(city)));
        return "index";
    }

    // @PostMapping("result")
    // public ResponseEntity<String> showWeatherInfo(@RequestParam String city){
    //     return ResponseEntity.ok().body(weatherSvc.getWeatherInfo(city).toString());
        
    // }


}
