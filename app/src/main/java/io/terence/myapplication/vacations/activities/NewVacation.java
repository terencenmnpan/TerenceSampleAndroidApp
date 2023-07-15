package io.terence.myapplication.vacations.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;

public class NewVacation extends AppCompatActivity {
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
        setContentView(R.layout.activity_new_vacation);

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
        Intent intentApp = new Intent(NewVacation.this,
                MainActivity.class);

        NewVacation.this.startActivity(intentApp);
    }
}