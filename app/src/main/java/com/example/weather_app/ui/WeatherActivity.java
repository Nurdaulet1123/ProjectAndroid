package com.example.weather_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app.R;
import com.example.weather_app.api.RetrofitClient;
import com.example.weather_app.api.WeatherApiService;
import com.example.weather_app.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherActivity extends AppCompatActivity {

    private TextView weatherInfo;
    private EditText cityInput;
    private Button checkWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherInfo = findViewById(R.id.weather_info);
        cityInput = findViewById(R.id.city_input); // Поле для ввода города
        checkWeatherButton = findViewById(R.id.check_weather_button);

        checkWeatherButton.setOnClickListener(v -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                getWeatherDataForCity(city);
            } else {
                Toast.makeText(WeatherActivity.this, "Введите название города", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeatherDataForCity(String city) {
        // Создаем Retrofit объект и API-сервис
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        WeatherApiService apiService = retrofit.create(WeatherApiService.class);
        String apiKey = "659a8357e1f169c0936d0f47c0d1c92f"; // Вставьте свой ключ от OpenWeatherMap
        String units = "metric"; // Цель: температура в Цельсиях
        String language = "ru"; // Язык: русский

        // Выполняем запрос для указанного города
        Call<WeatherResponse> call = apiService.getWeather(city, apiKey, units, language);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        String cityName = weatherResponse.getName();
                        String description = weatherResponse.getWeather().get(0).getDescription();
                        double temp = weatherResponse.getMain().getTemp();

                        // Отображаем информацию о погоде для выбранного города
                        String weatherDetails = "Город: " + cityName
                                + "\nТемпература: " + temp + "°C"
                                + "\nОписание: " + description;

                        weatherInfo.setText(weatherDetails);
                    }
                } else {
                    Toast.makeText(WeatherActivity.this, "Не удалось получить данные для города: " + city, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
