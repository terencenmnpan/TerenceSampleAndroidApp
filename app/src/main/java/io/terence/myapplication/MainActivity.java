package io.terence.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.excursions.Excursion;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.VacationViewAdapter;
import io.terence.myapplication.vacations.VacationWithExcursions;
import io.terence.myapplication.vacations.activities.VacationForm;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VacationDao vacationDao;
    private AppDatabase appDatabase;
    private VacationViewAdapter vacationViewAdapter;
    List<Vacation> entityList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        vacationDao = appDatabase.vacationDao();
        loadTableData();

    }


    private void loadTableData() {

        new Thread(() -> {
            entityList = vacationDao.getAllEntities();
            runOnUiThread(() -> {
                vacationViewAdapter = new VacationViewAdapter(entityList, vacation -> {
                    Intent intent =  new Intent(getApplicationContext(), VacationForm.class);
                    intent.putExtra("editVacationId", vacation.getId());
                    startActivity(intent);
                });
                recyclerView.setAdapter(vacationViewAdapter);
                alerts();
            });
        }).start();
    }
    public void addVacation(View view) {
        Intent intent = new Intent(MainActivity.this, VacationForm.class);
        startActivity(intent);
    }

    private void alerts() {
        String message = "";

        for (Vacation vacation : entityList) {
            LocalDate today = LocalDate.now();
            if (vacation.isStartAlert() && vacation.getStartDate().equals(today)) {
                message = message.concat(vacation.getTitle() + "is starting." + System.getProperty("line.separator"));
            }
            if (vacation.isEndAlert() && vacation.getEndDate().equals(today)) {
                message = message.concat(vacation.getTitle() + "is ending." + System.getProperty("line.separator"));
            }
            VacationWithExcursions vacationWithExcursions =
                    vacationDao.getVacationWithExcursions(vacation.getId());
            if (vacationWithExcursions != null && !vacationWithExcursions.excursions.isEmpty()) {
                for (Excursion excursion : vacationWithExcursions.excursions) {
                    message = message.concat(excursion.getTitle() + "is today." + System.getProperty("line.separator"));
                }
            }
        }
        if(message.length() > 0){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Vacation Alerts")
                    .setMessage(message).setNeutralButton("OK", null).show();
        }
    }
}