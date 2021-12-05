package com.example.traininglog.ui.base.traininglog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Workout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class LogFragment extends PresenterFragment implements LogView, Refreshable {

    private CalendarView mCalendar;
    private View mErrorView;
    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private TextView mHeading;
    private LogAdapter mAdapter;
    private final List<Workout> mWorkouts = new ArrayList<>();
    private Calendar calendar;
    @InjectPresenter
    LogPresenter mPresenter;
    @ProvidePresenter
    LogPresenter providePresenter(){return new LogPresenter();}

    public LogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendar = view.findViewById(R.id.calendarView);
        mErrorView = view.findViewById(R.id.errorView);
        mRecyclerView = view.findViewById(R.id.log_recycler);
        mHeading = view.findViewById(R.id.heading);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = getActivity().findViewById(R.id.schedule_image);
        try {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 6.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {

        }
        mAdapter = new LogAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);
        mCalendar.setCalendarDayLayout(R.layout.calendar);
        List<Calendar> calendars = new ArrayList<>();
        calendar = Calendar.getInstance();
        calendars.add(calendar);
        mCalendar.setSelectedDates(calendars);
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
        onRefreshData();
    }

    private void currentDataPresences() {
        mPresenter.getPresences(calendar);
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
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        if(throwable!=null) {
            Log.e("err", throwable.getMessage());
        }
    }

    @Override
    public void onRefreshData() {
        mPresenter.getWorkoutsForMonth(calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.YEAR));
    }

    @Override
    public void showWorkouts(List<Workout> workouts) {
        List<EventDay> events = new ArrayList<>();
        for (Workout workout : workouts) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(workout.getStart_time());
            events.add(new EventDay(calendar1, R.drawable.ic_ellipse_2));
        }
        mCalendar.setEvents(events);
        mWorkouts.clear();
        mWorkouts.addAll(workouts);
        currentDataPresences();
    }

    @Override
    public void showPresences(List<Presence> presences) {
        mAdapter.addData(presences, true);
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        if(presences.size()!=0)
        mHeading.setText(presences.get(0).getWorkout().getClub().getName());
    }

    @Override
    protected LogPresenter getPresenter() {
        return mPresenter;
    }
}