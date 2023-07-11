package io.terence.myapplication.vacations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.terence.myapplication.R;

public class VacationViewAdapter extends RecyclerView.Adapter<VacationViewHolder> {

    private List<Vacation> vacations;

    public VacationViewAdapter(List<Vacation> vacations) {
        this.vacations = vacations;
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
        holder.vacationViewId.setText(Integer.toString(vacation.getId()));
        holder.vacationViewName.setText(vacation.getTitle());

    }

    @Override
    public int getItemCount() {
        return vacations.size();
    }
}
