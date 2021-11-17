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
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.AllClubsPresenter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleFragment extends PresenterFragment implements Refreshable, ScheduleView, WorkoutAdapter.OnItemClickListener {

    private RefreshOwner mRefreshOwner;
    private CalendarView mCalendar;
    private View mErrorView;
    private View mFreeDay;
    private RecyclerView mRecycler;
    private WorkoutAdapter mAdapter;
    private final List<Presence> mPresence = new ArrayList<>();
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
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(false);
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
        mPresenter.getPresencesForMonth(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
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
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void updatePresence(boolean is_attend) {

    }

    @Override
    public void showPresence(List<Presence_W_N> presences) {
        mAdapter.addPresences(presences);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), "Ошибка интернет-соединения", Toast.LENGTH_SHORT).show();
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
        mAdapter.removePresence(workout_id);
    }
}