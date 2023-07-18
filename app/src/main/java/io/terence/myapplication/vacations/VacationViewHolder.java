package io.terence.myapplication.vacations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.terence.myapplication.R;

public class VacationViewHolder extends RecyclerView.ViewHolder{
    public TextView vacationViewName;

    public VacationViewHolder(@NonNull View itemView) {
        super(itemView);
        vacationViewName = itemView.findViewById(R.id.vacationViewName);
    }

    public void bind(final Vacation vacation, final OnVacationClickListener listener) {
        itemView.setOnClickListener(v -> listener.onVacationClick(vacation));
    }
}
