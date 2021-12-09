package com.example.traininglog.ui.base.analysis.coach.users;

import android.content.Context;
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
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;

import java.util.List;


public class UserAnalysisButtonFragment extends PresenterFragment implements Refreshable, UserButtonView, UserButtonAdapter.OnItemClickListener{

    private RecyclerView mRecycler;
    private RecyclerView mUsersRecycler;
    private UserButtonAdapter mGroupAdapter;
    private RefreshOwner mRefreshOwner;
    private UserButtonAdapter2 mUsersAdapter;
    private View mErrorView;
    private Club mClub;
    private Button mGroup;
    @InjectPresenter
    UserButtonPresenter mPresenter;
    @ProvidePresenter
    UserButtonPresenter providePresenter() {return new UserButtonPresenter();}

    public UserAnalysisButtonFragment() {
        // Required empty public constructor
    }


    public static UserAnalysisButtonFragment newInstance() {
        return new UserAnalysisButtonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_analysis_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.analysis_recycler_clubs);
        mUsersRecycler = view.findViewById(R.id.analysis_recycler_users);
        mGroup = view.findViewById(R.id.group_button);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGroupAdapter = new UserButtonAdapter(this);
        mUsersAdapter = new UserButtonAdapter2(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mGroupAdapter);
        mUsersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUsersRecycler.setAdapter(mUsersAdapter);
        mGroup.setOnClickListener(v -> hideUsers());
        if(getActivity() instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) getActivity();
        onRefreshData();
    }

    @Override
    public void getUsersForClub(Club club) {
        mGroup.setText(club.getGroup());
        mGroup.setVisibility(View.VISIBLE);
        mClub = club;
        mRecycler.setVisibility(View.GONE);
        mUsersRecycler.setVisibility(View.VISIBLE);
        mPresenter.getUsersForClub(club.getId());
    }

    @Override
    public void selectUser(User user) {

    }

    public void hideUsers() {
        mClub=null;
        mUsersRecycler.setVisibility(View.GONE);
        mRecycler.setVisibility(View.GONE);
        mGroup.setVisibility(View.GONE);
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
        if(throwable.getMessage()!=null) Log.e("err", throwable.getMessage());
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshData() {
        if(mClub!=null) mPresenter.getUsersForClub(mClub.getId());
        else mPresenter.getClubs();
    }

    @Override
    public void showClubs(List<Club> clubs) {
        mGroupAdapter.addData(clubs, true);
        mRecycler.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showUsers(List<User> users) {
        mUsersAdapter.addData(users, true);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    protected UserButtonPresenter getPresenter() {
        return mPresenter;
    }
}