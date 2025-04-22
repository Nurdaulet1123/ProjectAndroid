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

public class ProfileFragment extends Fragment {

    private TextView welcomeTextView;
    private TextView usernameTextView;
    private Button logoutButton;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        welcomeTextView = root.findViewById(R.id.profile_welcome);
        usernameTextView = root.findViewById(R.id.profile_username);
        logoutButton = root.findViewById(R.id.logout_button);

        // Получаем имя пользователя из SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "неизвестен");

        // Отображаем приветствие и логин
        welcomeTextView.setText("Добро пожаловать, " + username + "!");
        usernameTextView.setText("Логин: " + username);

        // Обработчик кнопки выхода
        logoutButton.setOnClickListener(v -> {
            // Очищаем SharedPreferences, чтобы удалить логин
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("username");
            editor.apply();

            // Переход к экрану логина
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();  // Закрываем текущую активность
        });

        return root;
    }
}
