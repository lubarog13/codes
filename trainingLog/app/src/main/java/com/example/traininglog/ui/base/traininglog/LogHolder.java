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
    private TextView mReason;
    private TextView mReasonText;
    private ImageView mEarlyRet;
    private View mHeading1;
    private int clickCount = 0;
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
        mReason = itemView.findViewById(R.id.reason);
        mReasonText = itemView.findViewById(R.id.reason_text);
    }

    public void bind(Presence item, boolean isNewGroup, LogAdapter.OnItemClickListener onItemClickListener) {
        if (item==null) return;
        mReasonText.setVisibility(View.GONE);
        mReason.setVisibility(View.GONE);
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
        else if(item.getIs_attend()==false){
            mNotAttend.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
            mAttend.setImageDrawable(null);
        } else {
            mNotAttend.setImageDrawable(null);
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
        if(onItemClickListener!=null) {
            mAttend.setOnClickListener(v -> {
                if(item.getIs_attend()==null || item.getIs_attend()==false ) {
                    item.setIs_attend(true);
                    mAttend.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorok));
                    mNotAttend.setImageDrawable(null);
                    onItemClickListener.updatePresence(item);
                }
            });
            mNotAttend.setOnClickListener(v -> {
                if(item.getIs_attend()==null || item.getIs_attend()==true ) {
                    item.setIs_attend(false);
                    mNotAttend.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
                    mAttend.setImageDrawable(null);
                    onItemClickListener.updatePresence(item);
                } else {
                    if(clickCount%2==0) {
                        mReason.setVisibility(View.VISIBLE);
                        if (item.getReason() != null)
                            mReasonText.setText(item.getReason());
                        else mReasonText.setText("отсутствует");
                        mReasonText.setVisibility(View.VISIBLE);
                    } else {
                        mReason.setVisibility(View.GONE);
                        mReasonText.setVisibility(View.GONE);
                    }
                    clickCount++;
                }
            });
            mDelay.setOnClickListener(v -> {
                if(!item.isDelay()){
                    item.setDelay(true);
                    mDelay.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
                }
                else {
                    item.setDelay(false);
                    mDelay.setImageDrawable(null);
                }
                onItemClickListener.updatePresence(item);
            });
            mEarlyRet.setOnClickListener(v -> {
                if(!item.isEarly_ret()){
                    item.setEarly_ret(true);
                    mEarlyRet.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_vectorno));
                }
                else {
                    item.setEarly_ret(false);
                    mEarlyRet.setImageDrawable(null);
                }
                onItemClickListener.updatePresence(item);
            });
        }

    }
}
