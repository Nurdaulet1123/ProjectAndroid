package com.example.weather_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weather_app.R;
import com.example.weather_app.api.RetrofitClient;
import com.example.weather_app.api.WeatherApiService;
import com.example.weather_app.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView temperatureText, weatherDescription, humidityText, windText, locationText;
    private ImageView weatherIcon;
    private EditText cityInput;
    private Button checkWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Инициализация View элементов
        cityInput = findViewById(R.id.city_input);
        checkWeatherButton = findViewById(R.id.check_weather_button);
        temperatureText = findViewById(R.id.temperature_text);
        weatherDescription = findViewById(R.id.weather_description);
        humidityText = findViewById(R.id.humidity_text);
        windText = findViewById(R.id.wind_text);
        locationText = findViewById(R.id.location_text);
        weatherIcon = findViewById(R.id.weather_icon);

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
        WeatherApiService apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService.class);
        String apiKey = "659a8357e1f169c0936d0f47c0d1c92f";
        String units = "metric";
        String language = "ru";

        Call<WeatherResponse> call = apiService.getWeather(city, apiKey, units, language);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    updateUI(weather);
                } else {
                    Toast.makeText(WeatherActivity.this, "Не удалось получить данные для города: " + city, Toast.LENGTH_SHORT).show();
                    resetUI();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                resetUI();
            }
        });
    }

    private void updateUI(WeatherResponse weather) {
        if (weather.getWeather() == null || weather.getWeather().isEmpty()) {
            resetUI();
            return;
        }

        locationText.setText(weather.getName());
        temperatureText.setText(String.format("%.1f°C", weather.getMain().getTemp()));
        weatherDescription.setText(weather.getWeather().get(0).getDescription());
        humidityText.setText(String.format("Влажность: %d%%", weather.getMain().getHumidity()));

        if (weather.getWind() != null) {
            windText.setText(String.format("Ветер: %.1f м/с", weather.getWind().getSpeed()));
        }

        // Загрузка иконки погоды
        String iconUrl = "https://openweathermap.org/img/wn/" + weather.getWeather().get(0).getIcon() + "@2x.png";
        Glide.with(this)
                .load(iconUrl)
                .into(weatherIcon);
    }

    private void resetUI() {
        locationText.setText("--");
        temperatureText.setText("--°C");
        weatherDescription.setText("--");
        humidityText.setText("Влажность: --%");
        windText.setText("Ветер: -- м/с");
        weatherIcon.setImageResource(R.drawable.ic_weather_default);
    }
}