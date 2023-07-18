package io.terence.myapplication.vacations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import io.terence.myapplication.excursions.Excursion;

public class VacationWithExcursions {
    @Embedded
    public Vacation vacation;
    @Relation(
            parentColumn = "id",
            entityColumn = "vacationId"
    )
    public List<Excursion> excursions;
}
