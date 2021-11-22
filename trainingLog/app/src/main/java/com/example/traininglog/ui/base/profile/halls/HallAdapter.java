package com.example.traininglog.ui.base.profile.halls;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Hall;

import java.util.ArrayList;
import java.util.List;

public class HallAdapter extends RecyclerView.Adapter<HallHolder> {
    private final List<Hall> mHalls = new ArrayList<>();
    private final   onItemClickListener onItemClickListener;

    public HallAdapter(HallAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hall_item_layout, parent, false);
        return new HallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallHolder holder, int position) {
        Hall hall1 = mHalls.get(position * 2);
        Hall hall2 = null;
        if (!(position*2==(mHalls.size()-1))) {
            hall2 = mHalls.get(position * 2 + 1);
        }
        holder.bind(hall1, hall2, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        if(mHalls.size()%2==0) return mHalls.size() / 2;
        return mHalls.size() / 2 + 1;
    }

    public void addDate(List<Hall> halls, boolean isRefreshed) {
        if(isRefreshed) mHalls.clear();
        mHalls.addAll(halls);
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onClick(int hall_id);
    }
}
