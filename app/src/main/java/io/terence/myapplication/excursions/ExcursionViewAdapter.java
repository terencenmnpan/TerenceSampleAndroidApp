package io.terence.myapplication.excursions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.terence.myapplication.R;

public class ExcursionViewAdapter extends RecyclerView.Adapter<ExcursionViewHolder> {

    private final List<Excursion> excursions;

    private final OnExcursionClickListener listener;

    public ExcursionViewAdapter(List<Excursion> excursions, OnExcursionClickListener listener) {
        this.excursions = excursions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.excursion_view, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        Excursion excursion = excursions.get(position);
        holder.excursionViewName.setText(excursion.getTitle());
        holder.bind(excursion, listener);
    }

    @Override
    public int getItemCount() {
        return excursions.size();
    }
}
