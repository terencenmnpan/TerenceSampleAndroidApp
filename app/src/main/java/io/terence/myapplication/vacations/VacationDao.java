package io.terence.myapplication.vacations;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface  VacationDao {
    @Insert
    void insert(Vacation entity);

    @Update
    void update(Vacation entity);

    @Delete
    void delete(Vacation entity);

    @Query("SELECT * FROM vacations")
    List<Vacation> getAllEntities();

    // Other queries and operations
}
