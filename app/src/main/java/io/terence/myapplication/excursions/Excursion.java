package io.terence.myapplication.excursions;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;
    private LocalDate date;
    private boolean dateAlert;

    public boolean isDateAlert() {
        return dateAlert;
    }

    public void setDateAlert(boolean dateAlert) {
        this.dateAlert = dateAlert;
    }

    private int vacationId;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }
}
