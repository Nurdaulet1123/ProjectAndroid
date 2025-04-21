package com.example.weather_app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app.R;

import org.json.JSONObject;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    private EditText cityInput;
    private TextView weatherInfo;
    private Button checkWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityInput = findViewById(R.id.city_input);
        weatherInfo = findViewById(R.id.weather_info);
        checkWeatherButton = findViewById(R.id.check_weather_button);

        checkWeatherButton.setOnClickListener(v -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                new GetWeatherTask().execute(city);
            } else {
                weatherInfo.setText("Введите город.");
            }
        });
    }

    private class GetWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String apiKey = "659a8357e1f169c0936d0f47c0d1c92f"; // Вставь свой ключ от OpenWeatherMap
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + params[0] + "&appid=" + apiKey + "&units=metric&lang=ru";
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                return json.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                weatherInfo.setText("Ошибка при получении погоды.");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String cityName = jsonObject.getString("name");
                JSONObject main = jsonObject.getJSONObject("main");
                double temp = main.getDouble("temp");
                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                weatherInfo.setText("Город: " + cityName + "\nТемпература: " + temp + "°C\nОписание: " + description);
            } catch (Exception e) {
                weatherInfo.setText("Не удалось обработать данные.");
            }
        }
    }
}
