package com.example.traininglog.ui.base.profile.coaches;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.BuildConfig;
import com.example.traininglog.R;
import com.example.traininglog.data.model.Coach;
import com.squareup.picasso.Picasso;

public class CoachHolder extends RecyclerView.ViewHolder {
    private ImageView mImage;
    private TextView mName;
    private TextView mPost;
    public CoachHolder(@NonNull View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.coach_image);
        mName = itemView.findViewById(R.id.coach_fio);
        mPost = itemView.findViewById(R.id.coach_post);
    }

    public void bind(Coach item) {
        Picasso.with(mImage.getContext()).load(BuildConfig.API_URL + "media/" + item.getId() + "_coach.jpg").fit().into(mImage);
        mName.setText(String.format("%s %s %s", item.getUser().getLast_name(), item.getUser().getFirst_name(), item.getUser().getSecond_name()));
        mPost.setText(item.getPost());
    }



}
