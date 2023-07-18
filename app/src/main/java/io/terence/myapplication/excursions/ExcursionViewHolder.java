package io.terence.myapplication.excursions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.terence.myapplication.R;
import io.terence.myapplication.vacations.OnVacationClickListener;
import io.terence.myapplication.vacations.Vacation;

public class ExcursionViewHolder extends RecyclerView.ViewHolder{
    public TextView excursionViewName;

    public ExcursionViewHolder(@NonNull View itemView) {
        super(itemView);
        excursionViewName = itemView.findViewById(R.id.excursionViewName);
    }

    public void bind(final Excursion excursion, final OnExcursionClickListener listener) {
        itemView.setOnClickListener(v -> listener.onExcursionClick(excursion));
    }
}
