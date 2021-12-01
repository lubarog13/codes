package com.example.traininglog.ui.base.profile.coaches;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Coach;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachFragment extends PresenterFragment implements Refreshable, CoachView {
    private RecyclerView mRecycler;
    private CoachAdapter mCoachAdapter;
    private View mErrorView;
    private RefreshOwner mRefreshOwner;
    private boolean saveData;
    private Storage mStorage;
    @InjectPresenter
    CoachPresenter mPresenter;

    @ProvidePresenter
    CoachPresenter providePresenter() {
        return new CoachPresenter(mStorage);
    }

    public CoachFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static CoachFragment newInstance() {
        return new CoachFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
        if(context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.coach_recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCoachAdapter = new CoachAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mCoachAdapter);
        if(getActivity()!=null)
        saveData = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("save_data", true);
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
        mErrorView.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
        if(throwable!=null)
        Log.e("error", throwable.getMessage());
    }

    @Override
    public void onRefreshData() {
        mPresenter.getCoaches(saveData);
    }

    @Override
    public void showCoaches(List<Coach> coaches) {
        Log.e("size", String.valueOf(coaches.size()));
        mErrorView.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mCoachAdapter.addData(coaches, true);
    }

    @Override
    protected CoachPresenter getPresenter() {
        return mPresenter;
    }
}