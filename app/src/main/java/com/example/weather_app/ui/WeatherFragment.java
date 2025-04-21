package com.example.weather_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.weather_app.R;

public class WeatherFragment extends Fragment {

    public WeatherFragment() {
        super(R.layout.fragment_weather);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        Button goWeatherButton = rootView.findViewById(R.id.button_go_weather);
        goWeatherButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}