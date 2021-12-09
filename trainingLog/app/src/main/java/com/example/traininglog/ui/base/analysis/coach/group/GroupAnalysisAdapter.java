package com.example.traininglog.ui.base.analysis.coach.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.TypesResponse;
import com.example.traininglog.ui.base.home.WorkoutHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnalysisAdapter extends RecyclerView.Adapter<GroupAnalysisHolder> {
    private final List<String> mGroups = new ArrayList<>();
    private final List<TypesResponse> mResponses = new ArrayList<>();
    @NonNull
    @Override
    public GroupAnalysisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.analysis_item_layout, parent, false);
        return new GroupAnalysisHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAnalysisHolder holder, int position) {
        String name = mGroups.get(position);
        TypesResponse response = mResponses.get(position);
        holder.bind(response, name);
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public void addItem(String name, TypesResponse response) {
        this.mGroups.add(name);
        this.mResponses.add(response);
        notifyDataSetChanged();
    }

    public void clear() {
        mGroups.clear();
        mResponses.clear();
        notifyDataSetChanged();
    }
}
