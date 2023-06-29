package io.terence.myapplication;

import android.app.Application;

import androidx.room.Room;

import io.terence.myapplication.config.AppDatabase;

public class VacationApp extends Application {
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .build();
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }
}
