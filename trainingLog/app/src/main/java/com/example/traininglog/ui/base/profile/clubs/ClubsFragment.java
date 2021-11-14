package com.example.traininglog.ui.base.profile.clubs;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.base.home.WorkoutAdapter;
import com.example.traininglog.ui.base.profile.ProfilePresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubsFragment extends PresenterFragment implements  Refreshable,  ClubsView, ClubAdapter.onItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mErrorView;
    private View mAddView;
    private Button mMyCategory;
    private Button mAllCategory;
    private boolean m_checked = true;
    private String category = "all";
    private EditText mIdentifier;
    private RefreshOwner mRefreshOwner;
    @InjectPresenter
    ClubsPresenter mPresenter;
    private ClubAdapter mAdapter;


    @ProvidePresenter
    ClubsPresenter providePresenter() {
        return new ClubsPresenter();
    }

    public ClubsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClubsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClubsFragment newInstance(String param1, String param2) {
        return new ClubsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clubs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.signups_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mAddView = view.findViewById(R.id.add_layout);
        mIdentifier = view.findViewById(R.id.identifier);
        mMyCategory = view.findViewById(R.id.my);
        mAllCategory = view.findViewById(R.id.all);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ClubAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        mMyCategory.setTypeface(typeFace);
        mAllCategory.setTypeface(typeFace);
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
        mRecyclerView.setVisibility(View.GONE);
        mAddView.setVisibility(View.GONE);
        Log.e("err", throwable.getMessage());
    }

    @Override
    protected ClubsPresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void setSignUps(List<SignUp> signUps) {
        Log.e("signups", String.valueOf(signUps.size()));
        mAdapter.addData(signUps, true);
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAddView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUsers(List<User> users, int signup_id) {
        mAdapter.addUsers(signup_id, users);
    }

    @Override
    public void onRefreshData() {
        mPresenter.getSignUps();
    }

    @Override
    public void onUsersClick(int club_id, int signup_id) {
        mPresenter.getUsersForClub(club_id, signup_id);
    }

    @Override
    public void hideUser(int signup_id) {
        mAdapter.removeUsers(signup_id);
    }
}