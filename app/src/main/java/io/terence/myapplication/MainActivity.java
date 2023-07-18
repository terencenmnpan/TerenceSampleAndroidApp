package io.terence.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.VacationViewAdapter;
import io.terence.myapplication.vacations.activities.VacationForm;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VacationDao vacationDao;
    private AppDatabase appDatabase;
    private VacationViewAdapter vacationViewAdapter;
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
            List<Vacation> entityList = vacationDao.getAllEntities();
            runOnUiThread(() -> {
                vacationViewAdapter = new VacationViewAdapter(entityList, vacation -> {
                    Intent intent =  new Intent(getApplicationContext(), VacationForm.class);
                    intent.putExtra("editVacationId", vacation.getId());
                    startActivity(intent);
                });
                recyclerView.setAdapter(vacationViewAdapter);
            });
        }).start();
    }
    public void addVacation(View view) {
        Intent intent = new Intent(MainActivity.this, VacationForm.class);
        startActivity(intent);
    }

}