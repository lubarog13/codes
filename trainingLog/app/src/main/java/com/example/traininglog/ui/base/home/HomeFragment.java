package com.example.traininglog.ui.base.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Workout;

import java.util.List;


public class HomeFragment extends PresenterFragment implements HomeView, Refreshable, RefreshOwner, SwipeRefreshLayout.OnRefreshListener {
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
        mWorkoutAdapter = new WorkoutAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mWorkoutAdapter);
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
}