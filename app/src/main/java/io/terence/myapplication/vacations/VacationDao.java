package io.terence.myapplication.vacations;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface  VacationDao {
    @Insert
    void insert(Vacation entity);

    @Update
    void update(Vacation entity);

    @Delete
    void delete(Vacation entity);
    @Query("DELETE FROM vacations WHERE id = :id")
    void deleteVacationById(int id);
    @Query("SELECT * FROM vacations")
    List<Vacation> getAllEntities();
    @Query("SELECT * FROM vacations WHERE id=:id")
    Vacation loadSingle(int id);
    @Query("SELECT * FROM vacations LIMIT :limit OFFSET :offset")
    Vacation[] loadAllVacationsByPage(int limit,int offset);
    // Other queries and operations
}
