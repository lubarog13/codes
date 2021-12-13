package com.example.traininglog.ui.base.profile.clubs;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.home.WorkoutAdapter;
import com.example.traininglog.ui.base.profile.ProfilePresenter;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.AllClubsAdapter;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.AllClubsFragment;
import com.example.traininglog.ui.base.profile.clubs.coach.ClubCreateFragment;
import com.example.traininglog.ui.base.profile.clubs.coach.WorkoutCreateFragment;
import com.example.traininglog.ui.base.profile.coaches.CoachAdapter;
import com.example.traininglog.utils.ApiUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubsFragment extends PresenterFragment implements  Refreshable,  ClubsView, ClubAdapter.onItemClickListener, com.example.traininglog.ui.base.profile.clubs.coach.ClubAdapter.onItemClickListener {

    private RecyclerView mRecyclerView;
    private View mErrorView;
    private View mAddView;
    private Button mMyCategory;
    private Button mAllCategory;
    private boolean m_checked = true;
    private String category = "all";
    private EditText mIdentifier;
    private Button mCreateButton;
    private View mFragmentView;
    private RefreshOwner mRefreshOwner;
    private Fragment mChildFragment;
    @InjectPresenter
    ClubsPresenter mPresenter;
    private ClubAdapter mAdapter;
    private com.example.traininglog.ui.base.profile.clubs.coach.ClubAdapter mCoachAdapter;


    @ProvidePresenter
    ClubsPresenter providePresenter() {
        return new ClubsPresenter();
    }

    public ClubsFragment() {
        // Required empty public constructor
    }

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
        mFragmentView = view.findViewById(R.id.club_container);
        mCreateButton = view.findViewById(R.id.create_club_button);
    }

    public void changeFragment(Fragment fragment) {
        mChildFragment = fragment;
        mRecyclerView.setVisibility(View.GONE);
        mCreateButton.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mFragmentView.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction()
                .replace(R.id.club_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void hideFragment(Fragment fragment) {
        mCreateButton.setVisibility(View.VISIBLE);
        getChildFragmentManager().popBackStack(fragment.getClass().getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(fragment).commit();
        mFragmentView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ClubAdapter(this);
        mCoachAdapter = new com.example.traininglog.ui.base.profile.clubs.coach.ClubAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(ApiUtils.coach_id!=-1? mCoachAdapter: mAdapter);
        mRecyclerView.setHasFixedSize(false);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        mMyCategory.setTypeface(typeFace);
        mAllCategory.setTypeface(typeFace);
        mAllCategory.setOnClickListener(view -> changeFragment(AllClubsFragment.class));
        mIdentifier.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_SEND) {
                            mPresenter.createSignup(mIdentifier.getText().toString());
                            mIdentifier.setText("");
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(mIdentifier.getWindowToken(), 0);
                            mIdentifier.clearFocus();
                            handled = true;
                        }
                        return handled;
                    }
                }
        );
        if(ApiUtils.coach_id!=-1){
            mAddView.setVisibility(View.GONE);
            mCreateButton.setVisibility(View.VISIBLE);
            mCreateButton.setOnClickListener(v -> changeFragment(ClubCreateFragment.newInstance()));
        }
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
        if(ApiUtils.coach_id==-1)
        mAddView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setClubs(List<Club> clubs) {
        mCoachAdapter.addData(clubs, true);
    }

    @Override
    public void setUsers(List<User> users, int signup_id) {
        if(ApiUtils.coach_id==-1)
        mAdapter.addUsers(signup_id, users);
        else mCoachAdapter.addUsers(signup_id, users);
    }

    @Override
    public void showCreatingError(Throwable throwable) {
        Toast.makeText(getActivity(), "Ошибка создания или удаления записи, возможно идентификатор неверен", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateSignUps(SignUp signUp) {
        mAdapter.updateData(signUp);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getActivity(), "Успешно удалено!", Toast.LENGTH_SHORT).show();
        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        if(ApiUtils.coach_id==-1)
        mPresenter.getSignUps();
        else mPresenter.getClubs();
    }

    @Override
    public void onUsersClick(int club_id, int signup_id) {
        mPresenter.getUsersForClub(club_id, signup_id);
    }

    @Override
    public void onUsersClick(int club_id) {
        mPresenter.getUsersForClub(club_id, club_id);
    }

    @Override
    public void hideUser(int signup_id) {
        if(ApiUtils.coach_id==-1)
        mAdapter.removeUsers(signup_id);
        else mCoachAdapter.removeUsers(signup_id);
    }

    @Override
    public void addWorkout(int club_id) {
        changeFragment(WorkoutCreateFragment.newInstance(club_id));
    }

    @Override
    public void addSignup(int club_id) {

    }

    @Override
    public void deleteSignUp(int signup_id) {
        mPresenter.deleteSignUp(signup_id);
    }

    private void changeFragment(Class fragmentClass){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((HomeActivity) getActivity()).changeFragment(fragment);
    }
}