package com.example.weather_app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.weather_app.R;
import com.example.weather_app.api.RetrofitClient;
import com.example.weather_app.api.WeatherApiService;
import com.example.weather_app.api.WeatherResponse;
import com.example.weather_app.model.WeatherViewModel;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView temperatureText, weatherDescription, humidityText, windText, locationText;
    private ImageView weatherIcon;
    private Button refreshButton;
    private WeatherViewModel weatherViewModel;
    private String[] cities = {"Moscow", "New York", "London", "Tokyo", "Paris"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Инициализация View элементов
        temperatureText = rootView.findViewById(R.id.temperature_text);
        weatherDescription = rootView.findViewById(R.id.weather_description);
        humidityText = rootView.findViewById(R.id.humidity_text);
        windText = rootView.findViewById(R.id.wind_text);
        locationText = rootView.findViewById(R.id.location_text);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        refreshButton = rootView.findViewById(R.id.refresh_button);

        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        refreshButton.setOnClickListener(v -> getWeatherForRandomCity());

        if (weatherViewModel.hasData()) {
            updateUIFromViewModel();
        } else {
            getWeatherForRandomCity();
        }

        return rootView;
    }

    private void getWeatherForRandomCity() {
        WeatherApiService apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService.class);
        Random random = new Random();
        String city = cities[random.nextInt(cities.length)];

        if (weatherViewModel.isCityUsed(city)) {
            getWeatherForRandomCity(); // Пробуем другой город
            return;
        }

        weatherViewModel.addUsedCity(city);

        Call<WeatherResponse> call = apiService.getWeather(
                city,
                "659a8357e1f169c0936d0f47c0d1c92f",
                "metric",
                "ru");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    updateUI(weather);
                    weatherViewModel.addWeatherInfo(formatWeatherInfo(weather));
                } else {
                    Toast.makeText(getContext(), "Ошибка: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(WeatherResponse weather) {
        if (weather == null || weather.getWeather() == null || weather.getWeather().isEmpty()) {
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
        if (weather.getWeather().get(0).getIcon() != null) {
            String iconUrl = "https://openweathermap.org/img/wn/" + weather.getWeather().get(0).getIcon() + "@2x.png";
            Glide.with(this)
                    .load(iconUrl)
                    .into(weatherIcon);
        }
    }

    private void updateUIFromViewModel() {
        String weatherData = weatherViewModel.getWeatherData();
        if (weatherData.isEmpty()) {
            return;
        }

        // Используем регулярное выражение для парсинга
        Pattern pattern = Pattern.compile(
                "Город: (.*?)\nТемпература: (.*?)°C\nОписание: (.*?)\nВлажность: (.*?)%\nВетер: (.*?) м/с",
                Pattern.DOTALL);
        Matcher matcher = pattern.matcher(weatherData);

        String city = "--", temp = "--", description = "--", humidity = "--", wind = "--";

        // Ищем последнее совпадение (последний город в истории)
        while (matcher.find()) {
            city = matcher.group(1).trim();
            temp = matcher.group(2).trim();
            description = matcher.group(3).trim();
            humidity = matcher.group(4).trim();
            wind = matcher.group(5).trim();
        }

        // Обновляем UI
        locationText.setText(city);
        temperatureText.setText(temp + "°C");
        weatherDescription.setText(description);
        humidityText.setText("Влажность: " + humidity + "%");
        windText.setText("Ветер: " + wind + " м/с");
        weatherIcon.setImageResource(R.drawable.ic_weather_default);
    }

    private String formatWeatherInfo(WeatherResponse weather) {
        return String.format(
                "Город: %s\nТемпература: %.1f°C\nОписание: %s\nВлажность: %d%%\nВетер: %.1f м/с",
                weather.getName(),
                weather.getMain().getTemp(),
                weather.getWeather().get(0).getDescription(),
                weather.getMain().getHumidity(),
                weather.getWind() != null ? weather.getWind().getSpeed() : 0.0
        );
    }

    private String extractValue(String source, String prefix) {
        try {
            int startIndex = source.indexOf(prefix) + prefix.length();
            int endIndex = source.indexOf("\n", startIndex);
            if (endIndex == -1) endIndex = source.length();
            return source.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "--";
        }
    }
}