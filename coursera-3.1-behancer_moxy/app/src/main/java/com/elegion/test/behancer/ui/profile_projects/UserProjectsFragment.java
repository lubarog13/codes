package com.elegion.test.behancer.ui.profile_projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.PresenterFragment;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.ui.profile.ProfileActivity;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.projects.ProjectsAdapter;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;
import com.elegion.test.behancer.ui.projects.ProjectsView;

import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class UserProjectsFragment extends PresenterFragment implements Refreshable, ProjectsView, ProjectsAdapter.OnItemClickListener  {

    public static final String PROFILE_KEY = "PROFILE_KEY";
    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Storage mStorage;
    private String mUsername;
    private ProjectsAdapter mProjectsAdapter;

    @InjectPresenter
    ProjectsPresenter mPresenter;

    @ProvidePresenter
    ProjectsPresenter providePresenter() {
        Log.e("m", "mPresenter");
        return new ProjectsPresenter(mStorage, true, mUsername);
    }

    protected ProjectsPresenter getPresenter() {
        return mPresenter;
    }

    public static UserProjectsFragment newInstance(Bundle arguments) {
        UserProjectsFragment userProjectsFragment =  new UserProjectsFragment();
        userProjectsFragment.setArguments(arguments);
        return userProjectsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            Log.e("m", "mStorage");
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("ok", "ok");
        if (getArguments() != null) {
            Log.e("ok", "ok");
            mUsername = getArguments().getString(PROFILE_KEY);
            Log.e("user", mUsername);
            mPresenter.setUsername(mUsername);
        }
        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }
        Log.e("m", "onCreated");
        mProjectsAdapter = new ProjectsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

        onRefreshData();
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        mPresenter.getProjects();
    }

    @Override
    public void showRefresh() {
        Log.e("refresh", mRefreshOwner.toString());
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void openProfileFragment(@NonNull String username) {
    }

    @Override
    public void showProjects(@NonNull List<Project> projects) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProjectsAdapter.addData(projects, true);
    }

    @Override
    public void onItemClick(String username) {
        mPresenter.openProfileFragment(username);
    }
}
