package com.example.weather_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weather_app.model.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        // Проверка на существование пользователя с таким же именем
        Cursor cursor = db.query(TABLE_USERS, null, "username=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false; // Пользователь с таким логином уже существует
        }

        // Добавление нового пользователя
        db.insert(TABLE_USERS, null, values);
        db.close();
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, "username=? AND password=?", new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }
}
