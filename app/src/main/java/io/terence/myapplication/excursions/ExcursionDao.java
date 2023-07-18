package io.terence.myapplication.excursions;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Upsert;

import io.terence.myapplication.vacations.Vacation;

@Dao
public interface ExcursionDao {
    @Upsert
    void upsert(Excursion entity);

    @Insert
    void insert(Excursion entity);

    @Update
    void update(Excursion entity);

    @Delete
    void delete(Excursion entity);
}
