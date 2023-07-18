package io.terence.myapplication.vacations.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;

public class VacationForm extends Activity {
    private VacationDao vacationDao;
    private AppDatabase appDatabase;
    private TextView header;
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
        setContentView(R.layout.activity_vacation_form);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        vacationDao = appDatabase.vacationDao();

        header = findViewById(R.id.edit_vacation_header);

        vacationName = findViewById(R.id.vacation_name);
        vacationAccommodation = findViewById(R.id.vacation_accommodation);
        vacationStartDate = findViewById(R.id.vacation_start_date);
        vacationEndDate = findViewById(R.id.vacation_end_date);

        submitButton = findViewById(R.id.save_vacation);
        deleteButton = findViewById(R.id.delete_vacation);

        Intent intent = getIntent();

        int vacationId = intent.getIntExtra("editVacationId", -1);
        if(vacationId != -1){
            header.setText(R.string.editing_vacation);
            vacation = vacationDao.loadSingle(vacationId);
            vacationName.setText(vacation.getTitle());
            vacationAccommodation.setText(vacation.getAccommodation());
            vacationStartDate.setText(vacation.getStartDate().toString());
            vacationEndDate.setText(vacation.getEndDate().toString());

            deleteButton.setOnClickListener(v -> deleteVacation());
        } else {
            header.setText(R.string.new_vacation_header);
            vacation = new Vacation();
            deleteButton.setVisibility(View.INVISIBLE);
        }
        submitButton.setOnClickListener(v -> saveVacation());
    }

    public void saveVacation(){
        validateVacation();
        if(hasErrors()){
            return;
        }
        vacationName = findViewById(R.id.vacation_name);
        vacationAccommodation = findViewById(R.id.vacation_accommodation);
        vacationStartDate = findViewById(R.id.vacation_start_date);
        vacationEndDate = findViewById(R.id.vacation_end_date);

        vacation.setTitle(vacationName.getText().toString());
        vacation.setAccommodation(vacationAccommodation.getText().toString());
        vacation.setStartDate(LocalDate.parse(vacationStartDate.getText().toString()));
        vacation.setEndDate(LocalDate.parse(vacationStartDate.getText().toString()));
        appDatabase.runInTransaction(() -> vacationDao.upsert(vacation));
        // doStuff
        Intent intentApp = new Intent(VacationForm.this,
                MainActivity.class);

        VacationForm.this.startActivity(intentApp);
    }

    public void deleteVacation(){
        appDatabase.runInTransaction(() -> vacationDao.delete(vacation));
        Intent intentApp = new Intent(VacationForm.this,
                MainActivity.class);

        VacationForm.this.startActivity(intentApp);
    }

    private void validateVacation(){
        if(vacationName.getText().length() == 0){
            vacationName.setError("Vacation Title must have a value");
        }
        if(vacationAccommodation.getText().length() == 0){
            vacationAccommodation.setError("Vacation Accommodation must have a value");
        }
        try{
            LocalDate.parse(vacationStartDate.getText().toString());
        } catch (DateTimeParseException dateTimeParseException){
            vacationStartDate.setError("Format must be yyyy-mm-dd");
        }
        try{
            LocalDate.parse(vacationEndDate.getText().toString());
        } catch (DateTimeParseException dateTimeParseException){
            vacationEndDate.setError("Format must be yyyy-mm-dd");
        }
        try{
            LocalDate startDate = LocalDate.parse(vacationStartDate.getText().toString());
            LocalDate endDate = LocalDate.parse(vacationEndDate.getText().toString());
            if(startDate.isAfter(endDate)){
                vacationStartDate.setError("Start Date must be before End Date");
            }
        } catch (DateTimeParseException ignored){
            //parsing errors handled before this block
        }
    }
    private boolean hasErrors(){
        return vacationName.getError() != null ||
                vacationAccommodation.getError() != null ||
                vacationStartDate.getError() != null ||
                vacationEndDate.getError() != null;
    }
}
