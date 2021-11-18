package com.example.traininglog.ui.base.home;

import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkoutHolder extends RecyclerView.ViewHolder {
    private TextView mType;
    private TextView mName;
    private TextView mCountOn;
    private TextView mCountDN;
    private TextView mCountNotOn;
    private TextView mWeekDay;
    private TextView mTime;
    private View mInfoView;
    private TextView mGroupName;
    private View mMainView;
    private TextView mCoachName;
    private TextView mHallName;
    private TextView mTypeName;
    private View mButtonView;
    private Button mOkButton;
    private Button mNoButton;
    private Button mWhoButton;
    private Resources mHallString;
    private View mOkView;
    private View mNoView;
    private View mWhoGoView;
    private TextView mIsAttend;
    private TextView mNotAttend;
    private TextView mDatetime;
    private View mReasonView;
    private EditText mReasonEditText;
    private Button mSendReason;
    private String clubName;
    private View mTypeView;
    private boolean is_opened = false;
    private Boolean is_attend;
    private boolean isWhoOpened = false;

    public WorkoutHolder(View itemView) {
        super(itemView);
        long startTime = System.currentTimeMillis();
        mType = itemView.findViewById(R.id.workout_week_type);
        mName = itemView.findViewById(R.id.workout_week_name);
        mCountOn = itemView.findViewById(R.id.workout_week_on_train);
        mCountDN = itemView.findViewById(R.id.workout_week_dont_know);
        mCountNotOn = itemView.findViewById(R.id.workout_week_not_on_train);
        mWeekDay = itemView.findViewById(R.id.week_day);
        mTime = itemView.findViewById(R.id.train_time);
        mInfoView = itemView.findViewById(R.id.workout_info);
        mGroupName = itemView.findViewById(R.id.group_name);
        mCoachName = itemView.findViewById(R.id.coach_name);
        mMainView = itemView.findViewById(R.id.home_main_view);
        mHallName = itemView.findViewById(R.id.hall_name);
        mTypeName  = itemView.findViewById(R.id.type_name);
        mButtonView = itemView.findViewById(R.id.go_buttons);
        mOkButton = itemView.findViewById(R.id.i_am_go);
        mNoButton = itemView.findViewById(R.id.i_am_not);
        mWhoButton = itemView.findViewById(R.id.who_go);
        mHallString = itemView.getResources();
        mOkView = itemView.findViewById(R.id.is_attend_ok);
        mNoView = itemView.findViewById(R.id.is_attend_not);
        mOkView.setVisibility(View.GONE);
        mNoView.setVisibility(View.GONE);
        mWhoGoView = itemView.findViewById(R.id.who_go_view);
        mIsAttend = itemView.findViewById(R.id.is_attend_names);
        mDatetime = itemView.findViewById(R.id.train_datetime);
        mNotAttend = itemView.findViewById(R.id.is_not_attend_names);
        mTypeView = itemView.findViewById(R.id.workout_info_type);
        mReasonView = itemView.findViewById(R.id.reason);
        mReasonEditText = itemView.findViewById(R.id.reason_text);
        mSendReason = itemView.findViewById(R.id.send_reason);
        mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemClick();
            }
        });
        Log.e("time1", String.valueOf(System.currentTimeMillis() - startTime));
    }

    public void bind(Workout item, boolean newDay, WorkoutAdapter.OnItemClickListener onItemClickListener, List<Presence_W_N> presences){
        long startTime =  System.currentTimeMillis();
        mType.setBackgroundColor(mHallString.getColor(bindColor(item.getType())));
        if(item.getClub().getName().length()>15) {
            mName.setText(String.format("%s...", item.getClub().getName().substring(0, 15)));
        } else {
            mName.setText(item.getClub().getName());
        }
        clubName = item.getClub().getName();
        mCountNotOn.setText(String.format("-%s", item.getNot_on_train()));
        mCountOn.setText(String.format("+%s", item.getOn_train()));
        mCountDN.setText(String.format("%s", item.getDont_know()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        mTime.setText(simpleDateFormat.format(item.getStart_time()));
        if(newDay) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM", Locale.getDefault());
            String week_day = simpleDateFormat1.format(item.getStart_time());
            mWeekDay.setText(String.format("%s  %s%s", simpleDateFormat2.format(item.getStart_time()), week_day.substring(0, 1).toUpperCase(), week_day.substring(1)));
            mWeekDay.setVisibility(View.VISIBLE);
        }
        is_attend = item.Is_on();
        mGroupName.setText(item.getClub().getGroup());
        String c_n = "", g_n="";
        if(item.getCoach()!=null) c_n = item.getCoach().getUser().getLast_name() + " "
                + item.getCoach().getUser().getFirst_name().substring(0,1) + "."
                + item.getCoach().getUser().getSecond_name().substring(0,1) + ".";
        else c_n = item.getClub().getCoach().getUser().getLast_name() + " "
                + item.getClub().getCoach().getUser().getFirst_name().substring(0,1) + "."
                + item.getClub().getCoach().getUser().getSecond_name().substring(0,1) + ".";
        mCoachName.setText(c_n);
        if(item.getHall().getNumber()!=0)  g_n = item.getHall().getName() + ", " + item.getHall().getNumber();
        else g_n = item.getHall().getName();
        mHallName.setText(Html.fromHtml(mHallString.getString(R.string.hall_name, g_n), Html.FROM_HTML_MODE_LEGACY));
        Log.e("date", item.getStart_time().toString());
        mTypeName.setText(item.getType());
        if(presences!=null) {
            List<Presence_W_N> presences1 = new ArrayList<>(presences);
            presences1.removeIf(presence -> presence.isIs_attend()==null || !presence.isIs_attend());
            StringBuilder stringBuilder = new StringBuilder();
            for (Presence_W_N presence: presences1){
                stringBuilder.append(presence.getUser().getLast_name()).append(" ").append(presence.getUser().getFirst_name().charAt(0)).append(".\n");
            }
            mIsAttend.setText(stringBuilder.toString());
            List<Presence_W_N> presences2 = new ArrayList<>(presences);
            presences2.removeIf(presence_w_n -> ( presence_w_n.isIs_attend()==null || presence_w_n.isIs_attend()==true));
            StringBuilder stringBuilder1 = new StringBuilder();
            for (Presence_W_N presence: presences2){
                stringBuilder1.append(presence.getUser().getLast_name()).append(" ").append(presence.getUser().getFirst_name().charAt(0)).append(".\n");
            }
            mNotAttend.setText(stringBuilder1.toString());
            mWhoGoView.setVisibility(View.VISIBLE);
            isWhoOpened = true;
        }
        SimpleDateFormat simpleDateFormat1  = new SimpleDateFormat("dd.MM.yy hh:mm");
        mDatetime.setText(simpleDateFormat1.format(item.getStart_time()));
        mTypeView.setBackgroundColor(mHallString.getColor(bindColor(item.getType())));
        if (onItemClickListener != null) {
            mOkButton.setOnClickListener(view -> {
                onItemClickListener.onItemClick(item.getId(), true);
                mOkView.setVisibility(View.VISIBLE);
                mNoView.setVisibility(View.GONE);
                mReasonView.setVisibility(View.GONE);
                is_attend=true;
                setPresence();
            });
            mNoButton.setOnClickListener(view -> {
                onItemClickListener.onItemClick(item.getId(), false);
                mNoView.setVisibility(View.VISIBLE);
                mOkView.setVisibility(View.GONE);
                mReasonView.setVisibility(View.VISIBLE);
                is_attend=false;
                resetPresence();
            });
            mWhoButton.setOnClickListener(view -> {
                if (!isWhoOpened) {
                    onItemClickListener.onPresencesClick(item.getId());
                } else {
                    mWhoGoView.setVisibility(View.GONE);
                    onItemClickListener.removePresences(item.getId());
                    isWhoOpened=false;
                }
            });
            mSendReason.setOnClickListener(v -> {
                onItemClickListener.onSendClick(mReasonEditText.getText().toString(), item.getId());
                mReasonView.setVisibility(View.GONE);
            });
        }
        Log.e("time2", String.valueOf(System.currentTimeMillis() - startTime));
    }

    private int bindColor(String type) {
        switch (type){
            case "общая": return R.color.colorRed;
            case "кардио": return R.color.colorOrange;
            case "другое": return R.color.colorYellow;
            case "силовая": return R.color.colorGreen;
            case "на технику": return R.color.colorPrimaryDark;
        }
        return R.color.colorRed;
    }

    private void setPresence() {
        if(is_attend==null ){
            mCountOn.setText("+" + String.valueOf(Integer.parseInt(mCountOn.getText().toString()) + 1) );
        } else if(!is_attend){
            mCountOn.setText("+" + String.valueOf(Integer.parseInt(mCountOn.getText().toString()) + 1) );
            mCountNotOn.setText("-" + String.valueOf(Integer.parseInt(mCountNotOn.getText().toString()) - 1) );
        }
    }

    private void resetPresence() {
        if(is_attend==null ){
            mCountNotOn.setText("-" + String.valueOf(Integer.parseInt(mCountOn.getText().toString()) + 1) );
        } else if(is_attend){
            mCountNotOn.setText("-" + String.valueOf(Integer.parseInt(mCountOn.getText().toString()) + 1) );
            mCountOn.setText("+" + String.valueOf(Integer.parseInt(mCountNotOn.getText().toString()) - 1) );
        }
    }

    private void OnItemClick() {
        if(!is_opened) {
            mInfoView.setVisibility(View.VISIBLE);
            mButtonView.setVisibility(View.VISIBLE);
            if(is_attend!=null && is_attend==true) mOkView.setVisibility(View.VISIBLE);
            if(is_attend!=null && is_attend==false) mNoView.setVisibility(View.VISIBLE);
            mDatetime.setVisibility(View.VISIBLE);
            mCountOn.setVisibility(View.GONE);
            mCountNotOn.setVisibility(View.GONE);
            mCountDN.setVisibility(View.GONE);
            mTime.setVisibility(View.GONE);
            mType.setVisibility(View.GONE);
            mTypeView.setVisibility(View.VISIBLE);
            mName.setText(clubName);
        } else {
            mInfoView.setVisibility(View.GONE);
            mButtonView.setVisibility(View.GONE);
            mNoView.setVisibility(View.GONE);
            mOkView.setVisibility(View.GONE);
            mWhoGoView.setVisibility(View.GONE);
            mDatetime.setVisibility(View.GONE);
            mCountOn.setVisibility(View.VISIBLE);
            mCountNotOn.setVisibility(View.VISIBLE);
            mReasonView.setVisibility(View.GONE);
            mCountDN.setVisibility(View.VISIBLE);
            mTime.setVisibility(View.VISIBLE);
            mType.setVisibility(View.VISIBLE);
            mTypeView.setVisibility(View.GONE);
            isWhoOpened = false;
        }
        is_opened = !is_opened;
    }

}
