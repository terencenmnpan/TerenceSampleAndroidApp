package io.terence.myapplication.vacations.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;

public class EditVacation extends Activity {
    private VacationDao vacationDao;
    private AppDatabase appDatabase;

    private EditText vacationName;
    private EditText vacationAccommodation;
    private EditText vacationStartDate;
    private EditText vacationEndDate;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vacation);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        vacationDao = appDatabase.vacationDao();
        vacationName = findViewById(R.id.vacation_name);
        vacationAccommodation = findViewById(R.id.vacation_accommodation);
        vacationStartDate = findViewById(R.id.vacation_start_date);
        vacationEndDate = findViewById(R.id.vacation_end_date);
        submitButton = findViewById(R.id.save_vacation);
        submitButton.setOnClickListener(v -> saveNewVacation());

    }

    public void saveNewVacation(){
        Vacation vacation = new Vacation();
        vacation.setTitle(vacationName.getText().toString());
        vacation.setAccommodation(vacationAccommodation.getText().toString());
        vacation.setStartDate(LocalDate.parse(vacationStartDate.getText().toString()));
        vacation.setEndDate(LocalDate.parse(vacationStartDate.getText().toString()));
        appDatabase.runInTransaction(() -> {
            vacationDao.insert(vacation);});
        // doStuff
        Intent intentApp = new Intent(EditVacation.this,
                MainActivity.class);

        EditVacation.this.startActivity(intentApp);
    }
}
