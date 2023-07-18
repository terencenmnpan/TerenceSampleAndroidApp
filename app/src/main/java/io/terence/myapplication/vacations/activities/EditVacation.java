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
    private Button deleteButton;

    private Vacation vacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vacation);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        vacationDao = appDatabase.vacationDao();
        Intent intent = getIntent();

        vacation = vacationDao.loadSingle(intent.getIntExtra("editVacationId", -1));
        vacationName = findViewById(R.id.vacation_name);
        vacationName.setText(vacation.getTitle());
        vacationAccommodation = findViewById(R.id.vacation_accommodation);
        vacationAccommodation.setText(vacation.getAccommodation());
        vacationStartDate = findViewById(R.id.vacation_start_date);
        vacationStartDate.setText(vacation.getStartDate().toString());
        vacationEndDate = findViewById(R.id.vacation_end_date);
        vacationEndDate.setText(vacation.getEndDate().toString());

        submitButton = findViewById(R.id.save_vacation);
        submitButton.setOnClickListener(v -> saveVacation());
        deleteButton = findViewById(R.id.delete_vacation);
        deleteButton.setOnClickListener(v -> deleteVacation());

    }

    public void saveVacation(){
        vacationName = findViewById(R.id.vacation_name);
        vacationAccommodation = findViewById(R.id.vacation_accommodation);
        vacationStartDate = findViewById(R.id.vacation_start_date);
        vacationEndDate = findViewById(R.id.vacation_end_date);

        vacation.setTitle(vacationName.getText().toString());
        vacation.setAccommodation(vacationAccommodation.getText().toString());
        vacation.setStartDate(LocalDate.parse(vacationStartDate.getText().toString()));
        vacation.setEndDate(LocalDate.parse(vacationStartDate.getText().toString()));
        appDatabase.runInTransaction(() -> vacationDao.update(vacation));
        // doStuff
        Intent intentApp = new Intent(EditVacation.this,
                MainActivity.class);

        EditVacation.this.startActivity(intentApp);
    }

    public void deleteVacation(){
        appDatabase.runInTransaction(() -> vacationDao.deleteVacationById(vacation.getId()));
        // doStuff
        Intent intentApp = new Intent(EditVacation.this,
                MainActivity.class);

        EditVacation.this.startActivity(intentApp);
    }
}
