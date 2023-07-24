package io.terence.myapplication.vacations.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.excursions.Excursion;
import io.terence.myapplication.excursions.ExcursionViewAdapter;
import io.terence.myapplication.excursions.activities.ExcursionForm;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.VacationViewAdapter;
import io.terence.myapplication.vacations.VacationWithExcursions;

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

    private RecyclerView recyclerView;
    private ExcursionViewAdapter excursionViewAdapter;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch startAlert;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch endAlert;

    private Button shareBtn;
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

        startAlert = findViewById(R.id.start_alert);
        endAlert = findViewById(R.id.end_alert);

        shareBtn = findViewById(R.id.share_btn);

        Intent intent = getIntent();

        int vacationId = intent.getIntExtra("editVacationId", -1);
        if(vacationId != -1){
            header.setText(R.string.editing_vacation);
            vacation = vacationDao.loadSingle(vacationId);
            vacationName.setText(vacation.getTitle());
            vacationAccommodation.setText(vacation.getAccommodation());
            vacationStartDate.setText(vacation.getStartDate().toString());
            vacationEndDate.setText(vacation.getEndDate().toString());
            startAlert.setChecked(vacation.isStartAlert());
            endAlert.setChecked(vacation.isEndAlert());

            deleteButton.setOnClickListener(v -> deleteVacation());
        } else {
            header.setText(R.string.new_vacation_header);
            vacation = new Vacation();
            deleteButton.setVisibility(View.INVISIBLE);
            shareBtn.setVisibility(View.INVISIBLE);
        }
        submitButton.setOnClickListener(v -> saveVacation());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        loadTableData();
    }

    public void saveVacation(){
        validateVacation();
        if(hasErrors()){
            return;
        }

        vacation.setTitle(vacationName.getText().toString());
        vacation.setAccommodation(vacationAccommodation.getText().toString());
        vacation.setStartDate(LocalDate.parse(vacationStartDate.getText().toString()));
        vacation.setEndDate(LocalDate.parse(vacationEndDate.getText().toString()));
        vacation.setStartAlert(startAlert.isChecked());
        vacation.setEndAlert(endAlert.isChecked());
        appDatabase.runInTransaction(() -> vacationDao.upsert(vacation));
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




    private void loadTableData() {

        new Thread(() -> {
            VacationWithExcursions vacationWithExcursions = vacationDao.getVacationWithExcursions(vacation.getId());

            List<Excursion> excursions = vacationWithExcursions != null ? vacationWithExcursions.excursions :
                    new ArrayList<>();
            runOnUiThread(() -> {
                excursionViewAdapter = new ExcursionViewAdapter(excursions, excursion -> {
                    Intent intent =  new Intent(getApplicationContext(), ExcursionForm.class);
                    intent.putExtra("editExcursionId", excursion.getId());
                    startActivity(intent);
                });
                recyclerView.setAdapter(excursionViewAdapter);
            });
        }).start();
    }
    public void addExcursion(View view) {
        validateVacation();
        if(hasErrors()){
            return;
        }
        Intent intent = new Intent(VacationForm.this, ExcursionForm.class);
        intent.putExtra("vacationId", vacation.getId());
        startActivity(intent);
    }


    public void shareVacation(View view) {
        String message = "I am taking a vacation: ";
        message = message.concat(vacation.getTitle());
        message = message.concat(" at " + vacation.getAccommodation() + ".");
        message = message.concat(" from: " + vacation.getStartDate() + " ");
        message = message.concat(" to: " + vacation.getEndDate() + ".");

        VacationWithExcursions vacationWithExcursions =
                vacationDao.getVacationWithExcursions(vacation.getId());
        if (vacationWithExcursions != null && !vacationWithExcursions.excursions.isEmpty()) {
            message = message.concat(System.getProperty("line.separator") + "With Excursions: ");
            for (Excursion excursion : vacationWithExcursions.excursions) {
                message = message.concat(System.getProperty("line.separator") + excursion.getTitle() + ": " + excursion.getDate());
            }
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
