package com.example.traininglog.ui.base.home;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class HomeFragment extends PresenterFragment implements HomeView, Refreshable, RefreshOwner, SwipeRefreshLayout.OnRefreshListener, WorkoutAdapter.OnItemClickListener {
    @InjectPresenter
    HomePresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mErrorView;
    private View mHomeView;
    private WorkoutAdapter mWorkoutAdapter;

    @ProvidePresenter
    HomePresenter providePresenter() {
        return new HomePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.week_rec);
        mErrorView = view.findViewById(R.id.errorView);
        mHomeView = view.findViewById(R.id.home_view);
        mSwipeRefreshLayout = view.findViewById(R.id.home_refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWorkoutAdapter = new WorkoutAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mWorkoutAdapter);
        TextView textView = getActivity().findViewById(R.id.week_workouts);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        ImageView imageView = getActivity().findViewById(R.id.image_main);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 2.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
            return;
        }
        onRefreshData();
    }

    @Override
    protected HomePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showRefresh() {
        this.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        this.setRefreshState(false);
    }

    @Override
    public void showError(Throwable throwable) {
        mErrorView.setVisibility(View.VISIBLE);
        mHomeView.setVisibility(View.GONE);
        Log.e("err", throwable.getMessage());
    }

    @Override
    public void onRefresh() {
        onRefreshData();
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public void onRefreshData() {
        mPresenter.getWorkouts();
    }

    @Override
    public void saveWorkouts(List<Workout> workouts) {
        mErrorView.setVisibility(View.GONE);
        mHomeView.setVisibility(View.VISIBLE);
        mWorkoutAdapter.addData(workouts, true);
    }

    @Override
    public void updatePresence(boolean is_attend) {

    }

    @Override
    public void showPresences(List<Presence_W_N> presences) {
        mWorkoutAdapter.addPresences(presences);
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
}