package com.example.weatherRequest.WeatherRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import static com.example.weatherRequest.Constants.BEAN_WEATHER_CACHE;

@Repository
public class WeatherRepo {

    @Autowired
    @Qualifier(BEAN_WEATHER_CACHE)
    RedisTemplate<String,String> redisTemplate;

    public void save(String city, String jsonString){
        redisTemplate.opsForValue().set(city,jsonString);
    }

    public String get(String city){
        return redisTemplate.opsForValue().get(city);
    }
}
