package io.terence.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.VacationViewAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VacationDao vacationDao;
    private VacationViewAdapter vacationViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTableData();
    }


    private void loadTableData() {
        new Thread(() -> {
            List<Vacation> entityList = vacationDao.getAllEntities();
            runOnUiThread(() -> {
                vacationViewAdapter = new VacationViewAdapter(entityList);
                recyclerView.setAdapter(vacationViewAdapter);
            });
        }).start();
    }
}