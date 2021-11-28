package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.messages.MessagesFragment;
import com.example.traininglog.ui.base.profile.clubs.ClubsFragment;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.buildings.BuildingAdapter;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.coaches.CoachesAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllClubsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllClubsFragment extends PresenterFragment implements Refreshable, AllClubsView, CoachesAdapter.OnItemClickListener, BuildingAdapter.OnItemClickListener, AllClubsAdapter.onItemClickListener {
    private RecyclerView mRecyclerView;
    private View mErrorView;
    private Button mMyCategory;
    private Button mAllCategory;
    private boolean m_checked = true;
    private String category = "all";
    private RefreshOwner mRefreshOwner;
    private RecyclerView mCoachRecycler;
    private CoachesAdapter mCoachAdapter;
    private Spinner mSpinner;
    private int mCoachId;
    private View mCoachInfo;
    private TextView mCoachName;
    private RecyclerView mBuildingsRecycler;
    private BuildingAdapter mBuildingAdapter;
    private Button mSendMessageButton;
    private AllClubsAdapter mAdapter;
    private int mBuildingId;
    @InjectPresenter
    AllClubsPresenter mPresenter;

    @ProvidePresenter
    AllClubsPresenter providePresenter() {
        return new AllClubsPresenter();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllClubsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllClubsFragment newInstance(String param1, String param2) {
        return new AllClubsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_clubs, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.clubs_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mMyCategory = view.findViewById(R.id.my_all);
        mAllCategory = view.findViewById(R.id.all_all);
        mSpinner = view.findViewById(R.id.club_category_spinner);
        mCoachRecycler = view.findViewById(R.id.coaches_category);
        mMyCategory.setOnClickListener(view1 -> changeFragment(ClubsFragment.class));
        mCoachInfo = view.findViewById(R.id.coach_info);
        mCoachName = view.findViewById(R.id.coach_selected_name);
        mSendMessageButton = view.findViewById(R.id.message_coach_create);
        mBuildingsRecycler = view.findViewById(R.id.buildings_category);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, R.layout.text_for_spinner);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] choose = getResources().getStringArray(R.array.categories);
                        switch (choose[i]){
                            case "Все":
                                category = "all";
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mCoachRecycler.setVisibility(View.GONE);
                                onRefreshData();
                                break;
                            case "По тренерам":
                                category = "coaches";
                                mRecyclerView.setVisibility(View.GONE);
                                mCoachRecycler.setVisibility(View.VISIBLE);
                                onRefreshData();
                                break;
                            case "По зданиям":
                                category = "buildings";
                                mRecyclerView.setVisibility(View.GONE);
                                mCoachRecycler.setVisibility(View.GONE);
                                onRefreshData();
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        mAdapter = new AllClubsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mCoachAdapter = new CoachesAdapter(this);
        mCoachRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCoachRecycler.setNestedScrollingEnabled(false);
        mCoachRecycler.setAdapter(mCoachAdapter);
        mCoachRecycler.setHasFixedSize(false);
        mBuildingAdapter = new BuildingAdapter(this);
        mBuildingsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBuildingsRecycler.setNestedScrollingEnabled(false);
        mBuildingsRecycler.setAdapter(mBuildingAdapter);
        mBuildingsRecycler.setHasFixedSize(false);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        mMyCategory.setTypeface(typeFace);
        mAllCategory.setTypeface(typeFace);
        mCoachName.setTypeface(typeFace);
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
        mCoachRecycler.setVisibility(View.GONE);
        Log.e("err", throwable.getMessage());
        mRecyclerView.setVisibility(View.GONE);
        mBuildingsRecycler.setVisibility(View.GONE);
    }

    @Override
    protected AllClubsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showClubs(List<Club> clubs) {
        mErrorView.setVisibility(View.GONE);
        mAdapter.addData(clubs, true);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCoaches(List<Coach> coaches) {
        mErrorView.setVisibility(View.GONE);
        mCoachRecycler.setVisibility(View.VISIBLE);
        mCoachAdapter.addData(coaches, true);
    }

    @Override
    public void showBuildings(List<Building> buildings) {
        mErrorView.setVisibility(View.GONE);
        mBuildingAdapter.addData(buildings, true);
        mBuildingsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshData() {
        switch (category){
            case "all":
                mPresenter.getClubs();
                mCoachInfo.setVisibility(View.GONE);
                break;
            case "coaches":
                mCoachInfo.setVisibility(View.GONE);
                mPresenter.getCoaches();
                break;
            case "coach_clubs":
                mCoachRecycler.setVisibility(View.GONE);
                mPresenter.getClubsForCoach(mCoachId);
                break;
            case "buildings":
                mCoachInfo.setVisibility(View.GONE);
                mPresenter.getBuildings();
                break;
            case "building_clubs":
                mBuildingsRecycler.setVisibility(View.GONE);
                mPresenter.getClubsForBuilding(mBuildingId);
                break;
            default:
                break;
        }
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

    @Override
    public void onCoachClick(Coach coach) {
        category = "coach_clubs";
        mCoachId = coach.getId();
        mCoachName.setText(String.format("%s %s. %s.", coach.getUser().getLast_name(), coach.getUser().getFirst_name().charAt(0), coach.getUser().getSecond_name().charAt(0)));
        mCoachInfo.setVisibility(View.VISIBLE);
        mSendMessageButton.setVisibility(View.VISIBLE);
        onRefreshData();
    }

    @Override
    public void onBuildingClick(Building building, String name) {
        category = "building_clubs";
        mBuildingId = building.getId();
        mCoachName.setText(name);
        mCoachInfo.setVisibility(View.VISIBLE);
        mSendMessageButton.setVisibility(View.GONE);
        onRefreshData();
    }

    @Override
    public void createSignup(String club_name) {
        ((HomeActivity) getActivity()).changeFragment(MessagesFragment.newInstance(club_name));
    }
}