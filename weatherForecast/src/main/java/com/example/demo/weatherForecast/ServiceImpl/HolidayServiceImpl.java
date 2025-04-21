package com.example.demo.weatherForecast.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.weatherForecast.Model.Holiday;
import com.example.demo.weatherForecast.Service.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://date.nager.at/api/v3/NextPublicHolidaysWorldwide";

    @Override
    public Holiday getNextPublicHolidays() {
        Holiday[] holidays = restTemplate.getForObject(API_URL, Holiday[].class);
        return holidays != null && holidays.length > 0 ? holidays[0] : null;
    }
}
