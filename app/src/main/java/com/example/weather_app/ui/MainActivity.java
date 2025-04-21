package com.example.weather_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weather_app.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goWeatherButton = findViewById(R.id.button_go_weather);
        goWeatherButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
        });
    }
}
