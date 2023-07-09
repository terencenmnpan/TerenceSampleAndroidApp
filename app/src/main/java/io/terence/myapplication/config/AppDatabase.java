package io.terence.myapplication.config;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;

@Database(entities = {Vacation.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VacationDao vacationDao();
}
