package com.example.demo.weatherForecast.Service;

import com.example.demo.weatherForecast.Model.Weather;

public interface WeatherService {
    Weather getWeather(String date, String location);
}
