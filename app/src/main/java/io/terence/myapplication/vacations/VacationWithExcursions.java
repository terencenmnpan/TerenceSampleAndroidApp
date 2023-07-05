package io.terence.myapplication.vacations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VacationWithExcursions {
    @Embedded
    public Vacation vacation;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public List<Excursion> excursions;
}
