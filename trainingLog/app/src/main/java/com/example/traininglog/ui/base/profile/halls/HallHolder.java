package com.example.traininglog.ui.base.profile.halls;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.BuildConfig;
import com.example.traininglog.R;
import com.example.traininglog.data.model.Hall;
import com.squareup.picasso.Picasso;

public class HallHolder extends RecyclerView.ViewHolder {
    private CardView mCard1;
    private CardView mCard2;
    private ImageView mImage1;
    private ImageView mImage2;
    private TextView mName1;
    private TextView mName2;
    public HallHolder(@NonNull View itemView) {
        super(itemView);
        mCard1 = itemView.findViewById(R.id.card_1);
        mCard2 = itemView.findViewById(R.id.card_2);
        mImage1 = itemView.findViewById(R.id.hall_image_1);
        mImage2 = itemView.findViewById(R.id.hall_image_2);
        mName1 = itemView.findViewById(R.id.hall_name_1);
        mName2 = itemView.findViewById(R.id.hall_name_2);
    }


    public void bind(Hall item1, Hall item2, HallAdapter.onItemClickListener onItemClickListener) {
        if(item1!=null) {
            Picasso.with(mImage1.getContext()).load(BuildConfig.API_URL + "media/" + item1.getId() + "_hall.jpg").fit().into(mImage1);
            mName1.setText(item1.getName());
            mCard1.setOnClickListener(v -> onItemClickListener.onClick(item1.getId()));
            mCard1.setVisibility(View.VISIBLE);
        } else mCard1.setVisibility(View.GONE);
        if(item2!=null) {
            Picasso.with(mImage2.getContext()).load(BuildConfig.API_URL + "media/" + item2.getId() + "_hall.jpg").fit().into(mImage2);
            mName2.setText(item2.getName());
            mCard2.setOnClickListener(v -> onItemClickListener.onClick(item2.getId()));
            mCard2.setVisibility(View.VISIBLE);
        } else mCard2.setVisibility(View.GONE);
    }
}
