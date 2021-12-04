package com.example.traininglog.ui.base.schedule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.hall.HallViewFragment;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.AllClubsPresenter;
import com.example.traininglog.ui.base.schedule.coach.CoachWorkoutAdapter;
import com.example.traininglog.utils.ApiUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ScheduleFragment extends PresenterFragment implements Refreshable, ScheduleView, WorkoutAdapter.OnItemClickListener, CoachWorkoutAdapter.OnItemClickListener {

    private RefreshOwner mRefreshOwner;
    private CalendarView mCalendar;
    private View mErrorView;
    private View mFreeDay;
    private RecyclerView mRecycler;
    private boolean saveData;
    private Storage mStorage;
    private WorkoutAdapter mAdapter;
    private CoachWorkoutAdapter mCoachAdapter;
    private final List<Presence> mPresence = new ArrayList<>();
    private final List<Workout> mWorkouts =new ArrayList<>();
    private Calendar calendar;

    @InjectPresenter
    SchedulePresenter mPresenter;

    @ProvidePresenter
    SchedulePresenter providePresenter() {
        return new SchedulePresenter();
    }

    public static ScheduleFragment newInstance() {return new ScheduleFragment();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
        if (context instanceof Storage.StorageOwner) {
            Log.e("m", "mStorage");
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendar = (CalendarView) view.findViewById(R.id.calendarView);
        mRecycler = view.findViewById(R.id.schedule_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mFreeDay = view.findViewById(R.id.free_day);
        mErrorView.setPadding(0, 0, 0, 100);
        ImageView imageView = view.findViewById(R.id.schedule_image);
        ImageView imageView1 = view.findViewById(R.id.free_image);
        if(getActivity()!=null)
        saveData = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("save_data", true);
        try {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 3.png");
            InputStream inputStream = getActivity().getAssets().open("calendar.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            imageView1.setImageDrawable(drawable);
            ims .close();
            inputStream .close();
        }
        catch(IOException ex)
        {

        }
        List<Calendar> calendars = new ArrayList<>();
        mCalendar.setCalendarDayLayout(R.layout.calendar);
        calendar = Calendar.getInstance();
        calendars.add(calendar);
        mCalendar.setSelectedDates(calendars);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new WorkoutAdapter(this);
        mCoachAdapter = new CoachWorkoutAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setNestedScrollingEnabled(false);
        if(getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("is_coach", false))
            mRecycler.setAdapter(mCoachAdapter);
        else mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(false);
        mPresenter.setStorage(mStorage);
        mCalendar.setOnPreviousPageChangeListener(() -> {
            calendar.add(Calendar.MONTH, -1);
            onRefreshData();
        });
        mCalendar.setOnForwardPageChangeListener(() -> {
            calendar.add(Calendar.MONTH, 1);
            onRefreshData();
        });
        mCalendar.setOnDayClickListener(eventDay -> {
            calendar.setTime(eventDay.getCalendar().getTime());
            currentDataPresences();
        });
        if(getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("is_coach", false)){
            mPresenter.getData();
        }
        else onRefreshData();
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
       mRecycler.setVisibility(View.GONE);
       mErrorView.setVisibility(View.VISIBLE);
       mFreeDay.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshData() {
        mPresenter.getPresencesForMonth(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), saveData);
    }

    @Override
    public void showPresences(List<Presence> presences) {
        List<EventDay> events = new ArrayList<>();
        Log.e("presences", String.valueOf(presences.size()));
        for (Presence presence : presences) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(presence.getWorkout().getStart_time());
            if(presence.isIs_attend()==null){
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_2));
            } else if(presence.isIs_attend()) {
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_3));
            } else {
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_1));
            }
        }
        mCalendar.setEvents(events);
        mPresence.clear();
        mPresence.addAll(presences);
        currentDataPresences();
        Log.e("events", events.toString());
    }

    private void currentDataPresences(){
        if(ApiUtils.coach_id==-1) {
            List<Presence> presences = new ArrayList<>(mPresence);
            presences.removeIf(presence -> !presence.isInDay(calendar));
            mAdapter.addData(presences, true);
            if (presences.size() != 0) {
                mRecycler.setVisibility(View.VISIBLE);
                mFreeDay.setVisibility(View.GONE);
            } else {
                mFreeDay.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
            }
        }
        else {
            List<Workout> workouts = mWorkouts.stream().filter(workout -> workout.isInDay(calendar)).collect(Collectors.toList());
            mCoachAdapter.addData(workouts, true);
            if (workouts.size() != 0) {
                mRecycler.setVisibility(View.VISIBLE);
                mFreeDay.setVisibility(View.GONE);
            } else {
                mFreeDay.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
            }
        }
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void updatePresence(boolean is_attend) {

    }

    @Override
    public void showPresence(List<Presence_W_N> presences) {
        if(ApiUtils.coach_id==-1)
        mAdapter.addPresences(presences);
        else mCoachAdapter.addPresences(presences);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), "Ошибка интернет-соединения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWorkouts(List<Workout> workouts) {
        List<EventDay> events = new ArrayList<>();
        for (Workout workout : workouts) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(workout.getStart_time());
            if(calendar1.getTime().compareTo(new Date())>0){
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_2));
            } else if(!workout.isIs_carried_out()) {
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_3));
            } else {
                events.add(new EventDay(calendar1, R.drawable.ic_ellipse_1));
            }
        }
        mCalendar.setEvents(events);
        mWorkouts.clear();
        mWorkouts.addAll(workouts);
        currentDataPresences();
        Log.e("events", events.toString());
    }

    @Override
    public void updateComplete() {
        onRefreshData();
    }

    @Override
    public void showValues(List<Hall> halls, List<Coach> coaches, List<Club> clubs) {
        mCoachAdapter.addValues(coaches, halls, clubs);
        onRefreshData();
    }

    @Override
    protected SchedulePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onItemClick(int workout_id, boolean is_attend) {
        if (is_attend) {
            mPresenter.setPresence(workout_id);
        } else {
            mPresenter.resetPresence(workout_id);
        }
    }

    @Override
    public void onPresencesClick(int workout_id) {
        mPresenter.getPresences(workout_id);
    }

    @Override
    public void removePresences(int workout_id) {
        if(ApiUtils.coach_id==-1)
        mAdapter.removePresence(workout_id);
        else mCoachAdapter.removePresence(workout_id);
    }

    @Override
    public void setReason(String reason, int workout_id) {
        mPresenter.updateReason(reason, workout_id);
    }

    @Override
    public void HallClick(int hall_id) {
        ((HomeActivity) getActivity()).changeFragment(HallViewFragment.newInstance(hall_id));
    }

    @Override
    public void editWorkout(WorkoutForEdit workout) {
        mPresenter.updateWorkout(workout);
    }
}