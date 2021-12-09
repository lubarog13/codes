package com.example.traininglog.ui.base.analysis.coach.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserButtonAdapter2 extends RecyclerView.Adapter<UsersButtonHolder> {

    private final List<User> users = new ArrayList<>();
    private final UserButtonAdapter.OnItemClickListener onItemClickListener;

    public UserButtonAdapter2(UserButtonAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UsersButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_button_layout, parent, false);
        return new UsersButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersButtonHolder holder, int position) {
        User user = users.get(position);
        holder.bind(null, user, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addData(List<User> users, boolean isRefreshed) {
        if(isRefreshed) this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }
}
