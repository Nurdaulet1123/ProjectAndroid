package com.example.weather_app.model;

import androidx.lifecycle.ViewModel;

import java.util.HashSet;
import java.util.Set;

public class WeatherViewModel extends ViewModel {
    private StringBuilder weatherData = new StringBuilder();
    private Set<String> usedCities = new HashSet<>();

    public void addWeatherInfo(String info) {
        weatherData.append(info).append("\n\n");
    }

    public String getWeatherData() {
        return weatherData.toString();
    }

    public Set<String> getUsedCities() {
        return usedCities;
    }

    public void addUsedCity(String city) {
        usedCities.add(city);
    }

    public boolean isCityUsed(String city) {
        return usedCities.contains(city);
    }

    public boolean hasData() {
        return weatherData.length() > 0;
    }
}
