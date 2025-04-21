package com.example.weather_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app.api.MockApiService;
import com.example.weather_app.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private MockApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Передаем контекст для создания экземпляра MockApiService
        apiService = new MockApiService(this);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
    }

    // Метод для обработки нажатия кнопки регистрации
    public void onRegisterClicked(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (apiService.addUser(username, password)) {
            Toast.makeText(this, "Регистрация прошла успешно! Переход в логин.", Toast.LENGTH_SHORT).show();
            // Переход на экран логина после успешной регистрации
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
        }
    }
}
