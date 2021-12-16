package com.example.traininglog.ui.base.traininglog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Presence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogAdapter extends RecyclerView.Adapter<LogHolder> {
    private final Map<String, List<Presence>> mPresencesMap = new HashMap<>();
    private final OnItemClickListener onItemClickListener;

    public LogAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.log_item_layout, parent, false);
        return new LogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogHolder holder, int position) {
        Presence presence = null;
        boolean isNewGroup = false;
        int i=0;
        for(List<Presence> presences: mPresencesMap.values()){
            if(i+presences.size()<position) {
                i+=presences.size();
            } else {
                int k=0;
                for (Presence presence1 : presences) {
                    if(i==position && k==0) isNewGroup = true;
                    if (i==position) {
                        presence = presence1;
                    }
                    i++;
                    k++;
                }
            }
        }
        holder.bind(presence, isNewGroup, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        int i = 0;
        for (List<Presence> presences: mPresencesMap.values()) {
            i+=presences.size();
        }
        return i;
    }

    public void addData(List<Presence> presences, boolean isRefreshed) {
        if(isRefreshed) mPresencesMap.clear();
        for(Presence presence: presences) {
            mPresencesMap.put(presence.getWorkout().getClub().getGroup(), new ArrayList<>());
        }
        for(Presence presence: presences) {
            mPresencesMap.get(presence.getWorkout().getClub().getGroup()).add(presence);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void updatePresence(Presence presence);
    }
}
