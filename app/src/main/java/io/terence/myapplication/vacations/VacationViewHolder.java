package io.terence.myapplication.vacations;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.terence.myapplication.MainActivity;
import io.terence.myapplication.R;
import io.terence.myapplication.vacations.activities.NewVacation;

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
