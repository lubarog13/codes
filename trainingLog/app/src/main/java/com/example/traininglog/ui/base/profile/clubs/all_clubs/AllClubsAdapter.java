package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;

import java.util.ArrayList;
import java.util.List;

public class AllClubsAdapter extends RecyclerView.Adapter<AllClubsHolder> {
    private final List<Club> mClubs = new ArrayList<>();
    private final onItemClickListener onItemClickListener;

    public AllClubsAdapter(AllClubsAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AllClubsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.club_view, parent, false);
        return new AllClubsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllClubsHolder holder, int position) {
        Club club = mClubs.get(position);
        holder.bind(club, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mClubs.size();
    }

    public void addData(List<Club> clubs, boolean isRefreshed){
        if(isRefreshed) mClubs.clear();
        mClubs.addAll(clubs);
        notifyDataSetChanged();
    }
    public interface onItemClickListener{
        void createSignup(String club_name);
    }

}
