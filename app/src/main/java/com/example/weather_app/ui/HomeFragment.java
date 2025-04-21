package com.example.weather_app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather_app.R;
import com.example.weather_app.api.RetrofitClient;
import com.example.weather_app.api.WeatherApiService;
import com.example.weather_app.api.WeatherResponse;
import com.example.weather_app.model.WeatherViewModel;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView weatherInfo;
    private WeatherViewModel weatherViewModel;

    private String[] cities = {"Moscow", "New York", "London", "Tokyo", "Paris"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        weatherInfo = rootView.findViewById(R.id.weather_info);

        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        // Если уже есть данные — просто отобразим их
        if (weatherViewModel.hasData()) {
            weatherInfo.setText(weatherViewModel.getWeatherData());
        } else {
            getWeatherForRandomCities();
        }

        return rootView;
    }

    private void getWeatherForRandomCities() {
        WeatherApiService apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService.class);
        int count = 5;
        Random random = new Random();

        while (weatherViewModel.getUsedCities().size() < count) {
            String city = cities[random.nextInt(cities.length)];
            if (!weatherViewModel.isCityUsed(city)) {
                weatherViewModel.addUsedCity(city);

                Call<WeatherResponse> call = apiService.getWeather(city,
                        "659a8357e1f169c0936d0f47c0d1c92f",
                        "metric", "ru");

                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            WeatherResponse weather = response.body();
                            String info = "Город: " + weather.getName() +
                                    "\nТемпература: " + weather.getMain().getTemp() + "°C" +
                                    "\nОписание: " + weather.getWeather().get(0).getDescription();
                            weatherViewModel.addWeatherInfo(info);
                            weatherInfo.setText(weatherViewModel.getWeatherData());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        weatherInfo.setText("Ошибка при получении данных.");
                    }
                });
            }
        }
    }
}
