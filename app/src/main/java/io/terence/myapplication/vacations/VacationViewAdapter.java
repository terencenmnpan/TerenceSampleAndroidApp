package io.terence.myapplication.vacations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.terence.myapplication.R;

public class VacationViewAdapter extends RecyclerView.Adapter<VacationViewHolder> {

    private List<Vacation> vacations;

    private final AdapterView.OnItemClickListener listener;

    public VacationViewAdapter(List<Vacation> vacations, AdapterView.OnItemClickListener listener) {
        this.vacations = vacations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_view, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        Vacation vacation = vacations.get(position);
        holder.vacationViewName.setText(vacation.getTitle());

    }

    @Override
    public int getItemCount() {
        return vacations.size();
    }
}
