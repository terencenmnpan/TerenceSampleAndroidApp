package io.terence.myapplication.excursions.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.excursions.Excursion;
import io.terence.myapplication.excursions.ExcursionDao;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.activities.VacationForm;

public class ExcursionForm extends AppCompatActivity {

    private ExcursionDao excursionDao;
    private VacationDao vacationDao;
    private AppDatabase appDatabase;
    private TextView header;
    private EditText excursionName;
    private EditText excursionDate;
    private Button saveBtn;
    private Button deleteBtn;
    private Excursion excursion;
    private Switch dateAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_form);


        appDatabase = AppDatabase.getInstance(getApplicationContext());
        vacationDao = appDatabase.vacationDao();
        excursionDao = appDatabase.excursionDao();

        header = findViewById(R.id.edit_excursion_header);
        excursionName = findViewById(R.id.excursion_name);
        excursionDate = findViewById(R.id.excursion_date);
        saveBtn = findViewById(R.id.save_excursion);
        deleteBtn = findViewById(R.id.delete_excursion);
        dateAlert = findViewById(R.id.date_alert);

        Intent intent = getIntent();

        int vacationId = intent.getIntExtra("vacationId", -1);

        int editExcursionId = intent.getIntExtra("editExcursionId", -1);
        if(editExcursionId != -1) {
            excursion = vacationDao.loadSingleExcursion(editExcursionId);
            header.setText(R.string.editing_excursion);
            excursionName.setText(excursion.getTitle());
            excursionDate.setText(excursion.getDate().toString());
            dateAlert.setChecked(excursion.isDateAlert());
            deleteBtn.setOnClickListener(v -> deleteExcursion());
        } else {
            header.setText(R.string.new_excursion);
            excursion = new Excursion();
            excursion.setVacationId(vacationId);
            deleteBtn.setVisibility(View.INVISIBLE);
        }
        saveBtn.setOnClickListener(v -> saveExcursion());

    }

    public void saveExcursion(){
        validateExcursion();
        if(hasErrors()){
            return;
        }
        excursion.setTitle(excursionName.getText().toString());
        excursion.setDate(LocalDate.parse(excursionDate.getText().toString()));
        excursion.setDateAlert(dateAlert.isChecked());
        appDatabase.runInTransaction(() -> excursionDao.upsert(excursion));
        Intent intentApp = new Intent(ExcursionForm.this,
                VacationForm.class);
        intentApp.putExtra("editVacationId", excursion.getVacationId());
        ExcursionForm.this.startActivity(intentApp);
    }

    public void deleteExcursion(){
        appDatabase.runInTransaction(() -> excursionDao.delete(excursion));
        Intent intentApp = new Intent(ExcursionForm.this,
                VacationForm.class);
        ExcursionForm.this.startActivity(intentApp);
    }

    private boolean hasErrors(){
        return excursionName.getError() != null ||
                excursionDate.getError() != null;
    }

    private void validateExcursion(){
        if(excursionName.getText().length() == 0){
            excursionName.setError("Excursion Title must have a value");
        }
        try{
            LocalDate.parse(excursionDate.getText().toString());
        } catch (DateTimeParseException dateTimeParseException){
            excursionDate.setError("Format must be yyyy-mm-dd");
        }

        LocalDate excursionLocalDate = LocalDate.parse(excursionDate.getText().toString());
        Vacation vacation = vacationDao.loadSingle(excursion.getVacationId());
        if (excursionLocalDate.isBefore(vacation.getStartDate()) ||
                excursionLocalDate.isAfter(vacation.getEndDate())) {
            excursionDate.setError("Date must be between Vacation Start and End Dates");
        }
    }
}