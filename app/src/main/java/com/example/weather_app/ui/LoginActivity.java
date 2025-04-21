package com.example.weather_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app.api.MockApiService;
import com.example.weather_app.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private MockApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация apiService с контекстом текущей Activity
        apiService = new MockApiService(this);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
    }

    // Метод для обработки нажатия кнопки логина
    public void onLoginClicked(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (apiService.authenticateUser(username, password)) {
            // Переход на MainActivity после успешного логина
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для перехода на экран регистрации
    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
