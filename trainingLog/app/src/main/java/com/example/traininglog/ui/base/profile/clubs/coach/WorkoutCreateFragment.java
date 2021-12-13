package com.example.traininglog.ui.base.profile.clubs.coach;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutCreateFragment extends PresenterFragment implements WorkoutCreateView{

    private static final String ARG_PARAM1 = "param1";
    private MaterialSpinner mGroup;
    private MaterialSpinner mCoach;
    private MaterialSpinner mHall;
    private MaterialSpinner mType;
    private EditText mOneDay;
    private EditText mOneMonth;
    private EditText mOneYear;
    private EditText mStartDay;
    private EditText mStartMonth;
    private EditText mStartYear;
    private Button mSaveButton;
    private Button mClearButton;
    private View mErrorView;
    private TextView mErrorText;
    private EditText mEndDay;
    private EditText mEndMonth;
    private EditText mEndYear;
    private CheckBox mPn;
    private CheckBox mVt;
    private CheckBox mSr;
    private CheckBox mCh;
    private CheckBox mPt;
    private CheckBox mSb;
    private CheckBox mVs;
    private RadioButton mOnce;
    private RadioButton mPeriodic;
    private EditText mStartHour;
    private EditText mStartMinutes;
    private EditText mEndMinutes;
    private EditText mEndHour;
    private LinearProgressIndicator mIndicator;
    private int createdProgress = 0;
    private int createdCount;
    private int createdSize;
//    private final List<Hall> mHalls = new ArrayList<>();
//    private final List<Coach> mCoaches = new ArrayList<>();
//    private final List<String> mTypes = new ArrayList<>();
//    private final List<Club> mClubs = new ArrayList<>();
    private Hall hall = new Hall();
    private Coach coach = new Coach();
    private Club club = new Club();
    private String type = "на технику";
    private int mClubId;
    private RefreshOwner mRefreshOwner;
    @InjectPresenter
    WorkoutCreatePresenter mPresenter;
    @ProvidePresenter
    WorkoutCreatePresenter providePresenter(){return new WorkoutCreatePresenter();}

    public WorkoutCreateFragment() {
        // Required empty public constructor
    }

    public static WorkoutCreateFragment newInstance(int param1) {
        WorkoutCreateFragment fragment = new WorkoutCreateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClubId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGroup = view.findViewById(R.id.club_group);
        mSaveButton = view.findViewById(R.id.save_button);
        mClearButton = view.findViewById(R.id.cleanButton);
        mErrorView = view.findViewById(R.id.error_view);
        mErrorText = view.findViewById(R.id.error_text);
        mCoach = view.findViewById(R.id.club_coach);
        mOneDay = view.findViewById(R.id.one_day);
        mOneMonth = view.findViewById(R.id.one_month);
        mOneYear = view.findViewById(R.id.one_year);
        mOnce = view.findViewById(R.id.workout_1);
        mType = view.findViewById(R.id.workout_type);
        mHall = view.findViewById(R.id.workout_hall);
        mPn = view.findViewById(R.id.pn_button);
        mVt = view.findViewById(R.id.vt_button);
        mSr = view.findViewById(R.id.sr_button);
        mCh = view.findViewById(R.id.ch_button);
        mPt = view.findViewById(R.id.pt_button);
        mSb = view.findViewById(R.id.sb_button);
        mVs = view.findViewById(R.id.vs_button);
        mPeriodic = view.findViewById(R.id.workout_many);
        mStartDay = view.findViewById(R.id.start_day);
        mEndDay = view.findViewById(R.id.end_day);
        mStartMonth = view.findViewById(R.id.start_month);
        mStartYear = view.findViewById(R.id.start_year);
        mEndYear = view.findViewById(R.id.end_year);
        mEndMonth = view.findViewById(R.id.end_month);
        mStartHour = view.findViewById(R.id.start_hour);
        mEndHour = view.findViewById(R.id.end_hour);
        mStartMinutes = view.findViewById(R.id.start_minutes);
        mEndMinutes = view.findViewById(R.id.end_minutes);
        mOnce.setOnCheckedChangeListener((buttonView, isChecked) -> mPeriodic.setChecked(!isChecked));
        mPeriodic.setOnCheckedChangeListener((buttonView, isChecked) -> mOnce.setChecked(!isChecked));
        mSaveButton.setOnClickListener(v ->save());
        mIndicator = view.findViewById(R.id.linear_progress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = getActivity().findViewById(R.id.create_title);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        mPresenter.getValues();
    }

    private void save() {
        if(mOnce.isChecked()) {
            Date date1 = getDate(mOneDay, mOneMonth, mOneYear);
            if(date1==null) return;
            try {
                int start_hour = Integer.parseInt(mStartHour.getText().toString());
                int end_hour = Integer.parseInt(mEndHour.getText().toString());
                int end_minutes = Integer.parseInt(mEndMinutes.getText().toString());
                int start_minutes = Integer.parseInt(mStartMinutes.getText().toString());
                if(start_hour<0 || end_hour<0 || end_hour>23 || start_hour>23 || start_minutes<0 || end_minutes<0|| start_minutes>59 || end_minutes>59) {
                    throw new Exception("Not valid time");
                }
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date1);
                calendar1.set(Calendar.HOUR, start_hour);
                calendar1.set(Calendar.MINUTE,start_minutes);
                calendar2.set(Calendar.HOUR, end_hour);
                calendar2.set(Calendar.MINUTE, end_minutes);
                if(calendar1.getTime().compareTo(calendar2.getTime())>0) throw new Exception("Not valid time 2");
                String pattern = "yyyy-MM-dd'T'HH:mm";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                WorkoutForEdit workout = new WorkoutForEdit(0, format.format(calendar1.getTime()),
                        format.format(calendar2.getTime()), type, null,
                        false, club.getCoach().getId()==coach.getId()? null: coach.getId(),
                        hall.getId(), club.getId());
                Log.d("workout", workout.toString());
                List<WorkoutForEdit> workouts = new ArrayList<>();
                workouts.add(workout);
                mPresenter.createWorkout(workouts, true);
            } catch (Exception e){
                Log.e("ex", e.getMessage());
                mErrorText.setText("Неверное время");
                mErrorView.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            Date startDate = getDate(mStartDay, mStartMonth, mStartYear);
            Date endDate = getDate(mEndDay, mEndMonth, mEndYear);
            if(startDate==null || endDate==null)return;
            try {
                int start_hour = Integer.parseInt(mStartHour.getText().toString());
                int end_hour = Integer.parseInt(mEndHour.getText().toString());
                int end_minutes = Integer.parseInt(mEndMinutes.getText().toString());
                int start_minutes = Integer.parseInt(mStartMinutes.getText().toString());
                if (start_hour < 0 || end_hour < 0 || end_hour > 23 || start_hour > 23 || start_minutes < 0 || end_minutes < 0 || start_minutes > 59 || end_minutes > 59) {
                    throw new Exception();
                }
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(startDate);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(startDate);
                calendar1.set(Calendar.HOUR, start_hour);
                calendar1.set(Calendar.MINUTE, start_minutes);
                calendar2.set(Calendar.HOUR, end_hour);
                calendar2.set(Calendar.MINUTE, end_minutes);
                if (calendar1.getTime().compareTo(calendar2.getTime()) > 0) throw new Exception();
                calendar2.setTime(endDate);
                calendar2.set(Calendar.HOUR, end_hour);
                calendar2.set(Calendar.MINUTE, end_minutes);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(calendar1.getTime());
                calendar3.set(Calendar.HOUR, end_hour);
                calendar3.set(Calendar.MINUTE, end_minutes);
                List<WorkoutForEdit> workouts = new ArrayList<>();
                if(calendar1.getTime().compareTo(Calendar.getInstance().getTime())>0) {
                    calendar1.add(Calendar.DATE, 1);
                    calendar3.add(Calendar.DATE, 1);
                }
                while (calendar1.getTime().compareTo(calendar2.getTime())<=0) {
                    if(isNeed(calendar1)) {
                        String pattern = "yyyy-MM-dd'T'HH:mm";
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        workouts.add(new WorkoutForEdit(0, format.format(calendar1.getTime()),
                                format.format(calendar3.getTime()), type, null,
                                false, club.getCoach().getId()==coach.getId()?null: coach.getId(),
                                hall.getId(), club.getId()));
                    }
                    calendar1.add(Calendar.DATE, 1);
                    calendar3.add(Calendar.DATE, 1);
                }
                workouts.removeIf(workout -> workout.getStart_time().compareTo(calendar2.toString())>0);
                Log.d("workouts", workouts.toString());
                mPresenter.createWorkout(workouts, false);
                createdSize = workouts.size();
                mIndicator.setVisibility(View.VISIBLE);
            } catch (Exception e){
                Log.e("ex", e.getMessage());
                mErrorText.setText("Неверное время");
                mErrorView.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    private Date getDate(EditText e_day, EditText e_month, EditText e_year){
        int day = Integer.parseInt(e_day.getText().toString());
        String month = e_month.getText().toString();
        int year = Integer.parseInt(e_year.getText().toString());
        String date = year + "-" + month + "-" + day;
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.ENGLISH);
        try {
            date1 = simpleDateFormat.parse(date);
            return date1;
        } catch (ParseException e) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MMMM-dd", new Locale("ru"));
            try {
                date1 = simpleDateFormat1.parse(date);
                return date1;
            } catch (ParseException parseException) {
                mErrorText.setText("Неверно введена дата");
                mErrorView.setVisibility(View.VISIBLE);
                return null;
            }
        }
    }


    private boolean isNeed(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return mVs.isChecked();
            case 2:
                return mPn.isChecked();
            case 3:
                return mVt.isChecked();
            case 4: return mSr.isChecked();
            case 5: return mCh.isChecked();
            case 6: return mPt.isChecked();
            case 7: return mSb.isChecked();
            default: return false;
        }
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError(Throwable throwable) {
        if(throwable!=null) Log.e("err", throwable.getMessage());
        mErrorView.setVisibility(View.VISIBLE);
        mErrorText.setText("Ошибка подключения");
    }

    @Override
    public void showValues(List<Club> clubs, List<Coach> coaches, List<Hall> halls) {
        List<String> groups = new ArrayList<>();
        for (Club club: clubs){
            groups.add(club.getGroup());
        }
        mGroup.setItems(groups);
        club = clubs.stream().filter(club1 -> club1.getId()==mClubId).collect(Collectors.toList()).get(0);
        mGroup.setSelectedIndex(clubs.indexOf(club));
        mGroup.setOnItemSelectedListener((view, position, id, item) -> club = clubs.get(position));
        List<String> names = new ArrayList<>();
        for (Coach coach : coaches) {
            names.add(String.format("%s %s. %s.", coach.getUser().getLast_name(), coach.getUser().getFirst_name().charAt(0), coach.getUser().getSecond_name().charAt(0)));
        }
        coach = coaches.stream().filter(coach1 -> coach1.getId()==club.getCoach().getId()).collect(Collectors.toList()).get(0);
        mCoach.setItems(names);
        mCoach.setSelectedIndex(coaches.indexOf(coach));
        mCoach.setOnItemSelectedListener((view, position, id, item) -> coach = coaches.get(position));
        List<String> addresses = new ArrayList<>();
        for (Hall hall: halls) {
            addresses.add(hall.getName());
        }
        hall = halls.get(0);
        mHall.setItems(addresses);
        mHall.setOnItemSelectedListener((view, position, id, item) -> hall = halls.get(position));
        List<String> types = new ArrayList<>();
        types.add("на технику");
        types.add("кардио");
        types.add("силовая");
        types.add("общая");
        types.add("другое");
        type = types.get(0);
        mType.setItems(types);
        mType.setOnItemSelectedListener((view, position, id, item) -> type=item.toString());
    }

    @Override
    public void createdOne() {
        Toast.makeText(getActivity(), "Успешно создано!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createdOneOf() {
        createdProgress +=100/createdSize;
        createdCount++;
        mIndicator.setProgress(createdProgress);
        if(createdCount==createdSize){
            mIndicator.setProgress(100);
            Toast.makeText(getActivity(), "Успешно создано!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected WorkoutCreatePresenter getPresenter() {
        return mPresenter;
    }
}