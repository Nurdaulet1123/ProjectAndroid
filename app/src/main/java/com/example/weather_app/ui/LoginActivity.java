package com.example.weather_app.ui;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app.R;
import com.example.weather_app.api.MockApiService;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private MockApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = new MockApiService(this);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);  // Добавляем кнопку регистрации

        loginButton.setOnClickListener(v -> onLoginClicked());
        registerButton.setOnClickListener(v -> onRegisterClicked());  // Обработчик для кнопки регистрации
    }

    private void onLoginClicked() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (apiService.authenticateUser(username, password)) {
            Toast.makeText(this, "Успешный вход!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRegisterClicked() {
        // Переходим на экран регистрации
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
