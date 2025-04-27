package com.example.weather_app.api;

import java.util.List;

public class WeatherResponse {
    private Main main;
    private Wind wind;
    private String name;
    private List<Weather> weather;

    // Геттеры и сеттеры
    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static class Main {
        private double temp;
        private int humidity;
        private double pressure;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getPressure() {
            return pressure;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }
    }

    public static class Wind {
        private double speed;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    public static class Weather {
        private String description;
        private String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}