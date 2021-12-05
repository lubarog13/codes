package com.example.traininglog.ui.base.traininglog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Presence;

public class LogHolder extends RecyclerView.ViewHolder
{

    private TextView mGroup;
    private TextView mName;
    private ImageView mAttend;
    private ImageView mNotAttend;
    private ImageView mDelay;
    private ImageView mEarlyRet;
    private View mHeading1;
    private View mHeading2;
    public LogHolder(@NonNull View itemView) {
        super(itemView);
        mGroup = itemView.findViewById(R.id.group_name);
        mName = itemView.findViewById(R.id.name);
        mAttend = itemView.findViewById(R.id.is_attend);
        mNotAttend = itemView.findViewById(R.id.is_not_attend);
        mDelay = itemView.findViewById(R.id.delay);
        mEarlyRet = itemView.findViewById(R.id.early_ret);
        mHeading1 = itemView.findViewById(R.id.group);
        mHeading2 = itemView.findViewById(R.id.head);
    }

    public void bind(Presence item, boolean isNewGroup) {
        if (item==null) return;
        if(isNewGroup) {
            mHeading1.setVisibility(View.VISIBLE);
            mHeading2.setVisibility(View.VISIBLE);
            mGroup.setText(item.getWorkout().getClub().getGroup());
        } else {
            mHeading1.setVisibility(View.GONE);
            mHeading2.setVisibility(View.GONE);
        }
        if (item.getIs_attend()){
            mAttend.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorok));
            mNotAttend.setImageDrawable(null);
        }
        else {
            mNotAttend.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
            mAttend.setImageDrawable(null);
        }
        if(item.isEarly_ret()) {
            mEarlyRet.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
        } else {
            mEarlyRet.setImageDrawable(null);
        }
        if(item.isDelay()) {
            mDelay.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
        } else {
            mDelay.setImageDrawable(null);
        }
        mName.setText(String.format("%s %s %s", item.getUser().getLast_name(), item.getUser().getFirst_name(), item.getUser().getSecond_name()));
    }
}
