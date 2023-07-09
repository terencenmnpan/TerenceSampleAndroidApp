package io.terence.myapplication.vacations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.terence.myapplication.R;

public class VacationViewHolder extends RecyclerView.ViewHolder{
    public TextView vacationViewId;
    public TextView vacationViewName;

    public VacationViewHolder(@NonNull View itemView) {
        super(itemView);
        vacationViewId = itemView.findViewById(R.id.vacationViewId);
        vacationViewName = itemView.findViewById(R.id.vacationViewName);
    }
}
