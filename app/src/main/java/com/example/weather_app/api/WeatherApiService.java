package com.example.weather_app.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("q") String city,
                                     @Query("appid") String apiKey,
                                     @Query("units") String units, // Добавляем параметр для единиц измерения
                                     @Query("lang") String language); // Добавляем параметр для языка
}
