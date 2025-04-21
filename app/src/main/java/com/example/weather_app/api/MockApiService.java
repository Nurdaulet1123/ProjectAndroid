package com.example.weather_app.api;

import android.content.Context;

import com.example.weather_app.database.UserDatabaseHelper;

public class MockApiService {

    private UserDatabaseHelper dbHelper;

    public MockApiService(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public boolean authenticateUser(String username, String password) {
        return dbHelper.authenticateUser(username, password);
    }

    public boolean addUser(String username, String password) {
        return dbHelper.addUser(username, password);
    }
}
