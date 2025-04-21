package com.example.demo.weatherForecast.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.weatherForecast.Model.Holiday;
import com.example.demo.weatherForecast.Model.Weather;
import com.example.demo.weatherForecast.Service.HolidayService;
import com.example.demo.weatherForecast.Service.WeatherService;

@RestController
public class WeatherController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather-on-next-holiday")
    public String getWeatherOnNextHoliday(@RequestParam String location) {
        Holiday holiday = holidayService.getNextPublicHolidays();
        if (holiday != null) {
            Weather weather = weatherService.getWeather(holiday.getDate(), location);
            if (weather != null && weather.getDescription() != null) {
                return String.format("The weather on the next public holiday (%s) in %s will be: %s, with a temperature of %.2f°C, pressure of %d hPa, and humidity of %d%%.",
                        holiday.getDate(), location, weather.getDescription(), weather.getTemperature(), weather.getPressure(), weather.getHumidity());
            } else {
                return "Weather data is not available for the next public holiday.";
            }
        }
        return "No upcoming holiday is found.";
    }
}
