package com.example.weatherapp;

public class CityWeather {
    String cityName;
    String temperature;
    String weatherIcon;
    String windSpeed;

    public CityWeather(String cityName, String temperature, String weatherIcon, String windSpeed) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherIcon = weatherIcon;
        this.windSpeed = windSpeed;
    }
}
