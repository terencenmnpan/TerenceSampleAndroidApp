package io.terence.myapplication.vacations;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

import io.reactivex.Completable;
import io.terence.myapplication.excursions.Excursion;

@Dao
public interface  VacationDao {
    @Upsert
    void upsert(Vacation entity);

    @Insert
    void insert(Vacation entity);

    @Update
    void update(Vacation entity);

    @Delete
    void delete(Vacation entity);

    @Query("SELECT * FROM vacations")
    List<Vacation> getAllEntities();

    @Query("SELECT * FROM vacations WHERE id=:id")
    Vacation loadSingle(int id);

    @Query("SELECT * FROM vacations LIMIT :limit OFFSET :offset")
    Vacation[] loadAllVacationsByPage(int limit, int offset);

    // Other queries and operations
    @Transaction
    @Query("SELECT * FROM vacations")
    List<VacationWithExcursions> getAllVacationsWithExcursions();

    @Transaction
    @Query("SELECT * FROM vacations WHERE id=:id")
    VacationWithExcursions getVacationWithExcursions(int id);

    @Query("SELECT count(*) FROM excursions WHERE vacationId=:id")
    int countExcursions(int id);

    @Query("SELECT * FROM excursions WHERE id=:id")
    Excursion loadSingleExcursion(int id);
}
