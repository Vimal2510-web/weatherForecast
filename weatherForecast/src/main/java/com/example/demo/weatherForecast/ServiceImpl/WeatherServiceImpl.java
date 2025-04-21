package com.example.demo.weatherForecast.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.weatherForecast.Model.Weather;
import com.example.demo.weatherForecast.Model.WeatherResponse;
import com.example.demo.weatherForecast.Service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweather.api.key}")
    private String apiKey;

    @Override
    public Weather getWeather(String date, String location) {
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", location, apiKey);
        try {
            WeatherResponse response = restTemplate.getForObject(apiUrl, WeatherResponse.class);
            System.out.println("API Response: " + response); // Debugging line
            if (response != null && response.getMain() != null && !response.getWeather().isEmpty()) {
                Weather weather = new Weather();
                weather.setTemperature(response.getMain().getTemp());
                weather.setPressure(response.getMain().getPressure());
                weather.setHumidity(response.getMain().getHumidity());
                weather.setDescription(response.getWeather().get(0).getDescription());
                return weather;
            } else {
                System.err.println("Invalid response structure or empty response.");
                return new Weather(); // Return an empty Weather object if the response is invalid
            }
        } catch (HttpClientErrorException e) {
            // Handle the error gracefully
            System.err.println("Error fetching weather data: " + e.getMessage());
            return new Weather(); // Return an empty Weather object if there's an error
        }
    }
}
