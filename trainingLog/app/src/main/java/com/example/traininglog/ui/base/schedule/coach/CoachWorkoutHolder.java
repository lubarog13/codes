package com.example.traininglog.ui.base.schedule.coach;

import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.ui.base.schedule.WorkoutAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoachWorkoutHolder extends RecyclerView.ViewHolder {
    private Spinner mGroupSpinner;
    private Spinner mCoachSpinner;
    private Spinner mHallSpinner;
    private Spinner mTypeSpinner;
    private EditText mTypeText;
    private EditText mStartTime;
    private EditText mEndTime;
    private Button mSaveButton;
    private Button mResetButton;
    private View mEditView;
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
    private Button mEditButton;
    private Button mNoButton;
    private Button mWhoButton;
    private TextView mDatetime;
    private View mTypeView;
    private Resources mHallString;
    private View mWhoGoView;
    private TextView mIsAttend;
    private TextView mNotAttend;
    private List<Hall> halls;
    private List<Coach> coaches;
    private List<Club> clubs;
    private ArrayList<String> groups;
    private ArrayList<String> types;
    private boolean is_opened = false;
    private String clubName;
    private boolean isEditViewOpened = false;
    private boolean isWhoOpened = false;
    private boolean whoGoesClick = false;
    public CoachWorkoutHolder(@NonNull View itemView) {
        super(itemView);
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
        mEditButton = itemView.findViewById(R.id.edit_workout);
        mNoButton = itemView.findViewById(R.id.delete_carried_out);
        mWhoButton = itemView.findViewById(R.id.who_go);
        mHallString = itemView.getResources();
        mEditView = itemView.findViewById(R.id.edit_workout_view);
        mTypeSpinner = itemView.findViewById(R.id.type_spinner);
        mGroupSpinner = itemView.findViewById(R.id.groups_spinner);
        mHallSpinner = itemView.findViewById(R.id.hall_spinner);
        mCoachSpinner = itemView.findViewById(R.id.coach_spinner);
        mTypeText = itemView.findViewById(R.id.type_text);
        mStartTime = itemView.findViewById(R.id.start_time);
        mEndTime = itemView.findViewById(R.id.end_time);
        mSaveButton = itemView.findViewById(R.id.save_button);
        mResetButton = itemView.findViewById(R.id.reset_button);
        mDatetime = itemView.findViewById(R.id.train_datetime);
        mTypeView = itemView.findViewById(R.id.workout_info_type);
        mWhoGoView = itemView.findViewById(R.id.who_go_view);
        mIsAttend = itemView.findViewById(R.id.is_attend_names);
        mNotAttend = itemView.findViewById(R.id.is_not_attend_names);
    }

    public void bind(Workout item, boolean newDay, List<Coach> coaches, List<Hall> halls, List<Club> clubs, CoachWorkoutAdapter.OnItemClickListener onItemClickListener, WorkoutAdapter.OnItemClickListener onClick, List<Presence_W_N> presences) {
        this.coaches = coaches;
        this.halls = halls;
        this.clubs =clubs;
        mType.setBackgroundColor(mHallString.getColor(bindColor(item.getType())));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getStart_time());
        calendar.add(Calendar.HOUR, -3);
        if (item.isIs_carried_out()) {
            mMainView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.lightRed));
        }
        if(presences==null&&!whoGoesClick)
        item.setStart_time(calendar.getTime());
        if(item.getClub().getName().length()>15) {
            mName.setText(String.format("%s...", item.getClub().getName().substring(0, 15)));
        } else {
            mName.setText(item.getClub().getName());
        }
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
        whoGoesClick = false;
        clubName = item.getClub().getName();
        mCountNotOn.setText(String.format("-%s", item.getNot_on_train()));
        mCountOn.setText(String.format("+%s", item.getOn_train()));
        mCountDN.setText(String.format("%s", item.getDont_know()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mTime.setText(simpleDateFormat.format(item.getStart_time().getTime()));
        if(newDay) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM", Locale.getDefault());
            String week_day = simpleDateFormat1.format(item.getStart_time().getTime());
            mWeekDay.setText(String.format("%s  %s%s", simpleDateFormat2.format(item.getStart_time()), week_day.substring(0, 1).toUpperCase(), week_day.substring(1)));
            mWeekDay.setVisibility(View.VISIBLE);
        }
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
        if (item.getType().equals("????????????") && item.getOther_type()!=null) {
            mTypeName.setText(item.getOther_type());
        }
        Log.e("date", item.getStart_time().toString());
        mTypeName.setText(item.getType());
        SimpleDateFormat simpleDateFormat1  = new SimpleDateFormat("dd.MM.yy HH:mm");
        mDatetime.setText(simpleDateFormat1.format(item.getStart_time().getTime()));
        mTypeView.setBackgroundColor(mHallString.getColor(bindColor(item.getType())));
        groups = new ArrayList<>();
        for (Club club: clubs) {
            groups.add(club.getGroup());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mEditView.getContext(), R.layout.text_for_edit_spinner, groups);
        mGroupSpinner.setAdapter(adapter);
        ArrayList<String> hall = new ArrayList<>();
        for(Hall hall1 : halls) {
            hall.add(hall1.getName());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mEditView.getContext(), R.layout.text_for_edit_spinner, hall);
        mHallSpinner.setAdapter(adapter1);
        ArrayList<String> coach = new ArrayList<>();
        for(Coach coach1: coaches){
            coach.add(String.format("%s %s.%s.", coach1.getUser().getLast_name(), coach1.getUser().getFirst_name().charAt(0), coach1.getUser().getSecond_name().charAt(0)));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mEditView.getContext(), R.layout.text_for_edit_spinner, coach);
        mCoachSpinner.setAdapter(adapter2);
        types = new ArrayList<>();
        types.add("???? ??????????????");
        types.add("????????????");
        types.add("??????????????");
        types.add("??????????");
        types.add("????????????");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(mEditView.getContext(), R.layout.text_for_edit_spinner, types);
        mTypeSpinner.setAdapter(adapter3);
        if(onItemClickListener!=null){
            mSaveButton.setOnClickListener(v -> {
                onItemClickListener.editWorkout(editWorkout(item));
            });
        }
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(types.get(position).equals("????????????")) {
                    mTypeText.setVisibility(View.VISIBLE);
                } else {
                    mTypeText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(clubs.size()!=0 && coaches.size()!=0 && halls.size()!=0){
            initEdit(item);
            mResetButton.setOnClickListener(v -> initEdit(item));
            mEditButton.setOnClickListener(v -> {
                if(item.getStart_time().compareTo(new Date())>0) {
                    if (isEditViewOpened) {
                        mEditView.setVisibility(View.GONE);
                    } else {
                        mEditView.setVisibility(View.VISIBLE);
                    }
                    isEditViewOpened = !isEditViewOpened;
                }
            });
            if(item.getStart_time().compareTo(new Date())>0 && onItemClickListener!=null){
                mNoButton.setOnClickListener(view -> onItemClickListener.cancelWorkout(item));
            }
        }
        if(onClick!=null) {
            mWhoButton.setOnClickListener(v -> {
                if (!isWhoOpened) {
                    onClick.onPresencesClick(item.getId());
                } else {
                    mWhoGoView.setVisibility(View.GONE);
                    onClick.removePresences(item.getId());
                    isWhoOpened=false;
                    whoGoesClick = true;
                }
            });
            mHallName.setOnClickListener(v -> onClick.HallClick(item.getHall().getId()));
        }
        mInfoView.setVisibility(View.VISIBLE);
        mButtonView.setVisibility(View.VISIBLE);
        mDatetime.setVisibility(View.VISIBLE);
        mCountOn.setVisibility(View.GONE);
        mCountNotOn.setVisibility(View.GONE);
        mCountDN.setVisibility(View.GONE);
        mTime.setVisibility(View.GONE);
        mType.setVisibility(View.GONE);
        mTypeView.setVisibility(View.VISIBLE);
        mName.setText(clubName);
    }
    private int bindColor(String type) {
        switch (type){
            case "??????????": return R.color.colorRed;
            case "????????????": return R.color.colorOrange;
            case "????????????": return R.color.colorYellow;
            case "??????????????": return R.color.colorGreen;
            case "???? ??????????????": return R.color.colorPrimaryDark;
        }
        return R.color.colorRed;
    }

    private void initEdit(Workout item) {
        Coach coach;
        if(item.getCoach()!=null) {
            coach = coaches.stream().filter(coach1 -> coach1.getId() == item.getCoach().getId()).collect(Collectors.toList()).get(0);
        } else {
            coach = coaches.stream().filter(coach1 -> coach1.getId() == item.getClub().getCoach().getId()).collect(Collectors.toList()).get(0);
        }
        mCoachSpinner.setSelection(coaches.indexOf(coach));
        Club club =clubs.stream().filter(club1 -> club1.getId()==item.getClub().getId()).collect(Collectors.toList()).get(0);
        mGroupSpinner.setSelection(clubs.indexOf(club));
        mTypeSpinner.setSelection(types.indexOf(item.getType()));
        Hall hall = halls.stream().filter(hall1 -> hall1.getId()==item.getHall().getId()).collect(Collectors.toList()).get(0);
        mHallSpinner.setSelection(halls.indexOf(hall));
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        mStartTime.setText(format.format(item.getStart_time()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getEnd_time());
        calendar.add(Calendar.HOUR, -3);
        mEndTime.setText(format.format(calendar.getTime()));
        if(item.getOther_type()!=null){
            mTypeText.setText(item.getOther_type());
        }
    }

    private WorkoutForEdit editWorkout(Workout item) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getStart_time());
        Pattern pattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
        if(!pattern.matcher(mStartTime.getText().toString()).matches()) {
            mStartTime.setError("???? ?????????? ???????????????? ??????????, ???????????? HH:mm");
            return null;
        }
        if(!pattern.matcher(mEndTime.getText().toString()).matches()) {
            mEndTime.setError("???? ?????????? ???????????????? ??????????, ???????????? HH:mm");
            return null;
        }
        if(mTypeSpinner.getSelectedItem().equals("????????????") && mTypeText.getText().length()==0) {
            mTypeText.setError("?????????????? ??????????");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outher_type = mTypeText.getText().length()!=0? mTypeText.getText().toString() : null;
        Integer coach;
        if(coaches.get(mCoachSpinner.getSelectedItemPosition()).getId()==item.getClub().getCoach().getId())
            coach = null;
        else coach = coaches.get(mCoachSpinner.getSelectedItemPosition()).getId();
        WorkoutForEdit workout = new WorkoutForEdit(item.getId(), simpleDateFormat.format(calendar.getTime()) + "T" + mStartTime.getText().toString(),
                simpleDateFormat.format(calendar.getTime()) + "T" + mEndTime.getText().toString(), mTypeSpinner.getSelectedItem().toString(), outher_type, false,
                coach,
                halls.get(mHallSpinner.getSelectedItemPosition()).getId(),
                clubs.get(mGroupSpinner.getSelectedItemPosition()).getId()
        );
        Log.e("workout", workout.toString());
        mEditView.setVisibility(View.GONE);
        isEditViewOpened = false;
        return workout;
    }

}
