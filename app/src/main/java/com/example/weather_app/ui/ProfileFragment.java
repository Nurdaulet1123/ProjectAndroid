package com.example.weather_app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weather_app.R;
import com.example.weather_app.ui.LoginActivity;

public class ProfileFragment extends Fragment {

    private TextView welcomeTextView;
    private Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        welcomeTextView = rootView.findViewById(R.id.profile_welcome);
        logoutButton = rootView.findViewById(R.id.logout_button);

        // Получаем данные пользователя
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "Гость");

        // Форматируем приветствие
        String welcomeText = String.format("Добро пожаловать,\n%s!", username);
        welcomeTextView.setText(welcomeText);

        logoutButton.setOnClickListener(v -> logoutUser());

        return rootView;
    }

    private void logoutUser() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // Переход на экран входа с очисткой стека активностей
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
