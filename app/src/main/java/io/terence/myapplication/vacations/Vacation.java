package io.terence.myapplication.vacations;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(tableName = "vacations")
public class Vacation implements Serializable {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;
    private String accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean startAlert;
    private boolean endAlert;

    public boolean isStartAlert() {
        return startAlert;
    }

    public void setStartAlert(boolean startAlert) {
        this.startAlert = startAlert;
    }

    public boolean isEndAlert() {
        return endAlert;
    }

    public void setEndAlert(boolean endAlert) {
        this.endAlert = endAlert;
    }

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

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
