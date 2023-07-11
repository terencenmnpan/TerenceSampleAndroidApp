package io.terence.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.time.LocalDate;
import java.util.List;

import io.terence.myapplication.config.AppDatabase;
import io.terence.myapplication.vacations.Vacation;
import io.terence.myapplication.vacations.VacationDao;
import io.terence.myapplication.vacations.VacationViewAdapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_database")
                .build();
        vacationDao = appDatabase.vacationDao();
        loadTableData();
    }


    private void loadTableData() {

        new Thread(() -> {
            loadInitialData(vacationDao);

            List<Vacation> entityList = vacationDao.getAllEntities();
            runOnUiThread(() -> {
                vacationViewAdapter = new VacationViewAdapter(entityList);
                recyclerView.setAdapter(vacationViewAdapter);
            });
        }).start();
    }

    private void loadInitialData(VacationDao vacationDao) {
        if(vacationDao.getAllEntities().isEmpty()){
            Vacation vacation = new Vacation();
            vacation.setTitle("ABC Vacay");
            vacation.setId(1);
            vacation.setStartDate(LocalDate.now().plusDays(5));
            vacation.setEndDate(LocalDate.now().plusDays(8));
            vacationDao.insert(vacation);
        }
    }
}