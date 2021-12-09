package com.example.traininglog.ui.base.analysis.coach.group;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Spinner;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.data.model.TypesResponse;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GroupAnalysisFragment extends PresenterFragment implements GroupAnalysisView, Refreshable {

    private BarChart mChart;
    private Spinner mSpinner;
    private RecyclerView mRecycler;
    private GroupAnalysisAdapter mAdapter;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private final List<Club> clubs = new ArrayList<>();
    @InjectPresenter
    GroupAnalysisPresenter mPresenter;
    @ProvidePresenter
    GroupAnalysisPresenter providePresenter() {return new GroupAnalysisPresenter();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public static GroupAnalysisFragment newInstance() {
        return new GroupAnalysisFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChart = view.findViewById(R.id.barchart);
        mSpinner = view.findViewById(R.id.week_day_spinner);
        mRecycler = view.findViewById(R.id.analysis_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mChart.startAnimation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.week_days, R.layout.text_for_spinner);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(false);
        mAdapter = new GroupAnalysisAdapter();
        mRecycler.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onRefreshData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        if(throwable!=null) Log.e("err", throwable.getMessage());
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showClubs(List<Club> clubs) {
        this.clubs.addAll(clubs);
        mPresenter.getAnalysisForClubs(parseDate());
    }

    @Override
    public void showAnalysis(List<GroupAnalysis.GroupAnalysisItem> items) {
        mChart.clearChart();
        Random random = new Random();
        if(items==null) return;
        Log.e("items", items.toString());
        for (GroupAnalysis.GroupAnalysisItem item: items) {
            Log.e("1", item.toString());
            Club club = clubs.stream().filter(club1 -> club1.getId()==item.getClubId()).collect(Collectors.toList()).get(0);
            mChart.addBar(new BarModel(club.getGroup(), item.getPresenceCount(), getResources().getColor(R.color.colorOrange)));
        }
        Log.e("items", items.toString());
        mChart.startAnimation();
        for (Club club: clubs){
            mPresenter.getWorkoutCountForGroup(club);
        }
    }

    @Override
    public void showCountForTypes(String name, TypesResponse responses) {
        mAdapter.addItem(name, responses);
    }

    @Override
    public void onRefreshData() {
        mAdapter.clear();
        clubs.clear();
        mPresenter.getClubs();
    }

    private String parseDate() {
        switch (mSpinner.getSelectedItem().toString()){
            case "Всего":
                return "all";
            case "Понедельник":
                return "2";
            case "Вторник":
                return "3";
            case "Среда":
                return "4";
            case "Четверг":
                return "5";
            case "Пятница":
                return "6";
            case "Суббота":
                return "7";
            case "Воскресенье":
                return "1";
        }
        return "all";
    }

    @Override
    protected GroupAnalysisPresenter getPresenter() {
        return mPresenter;
    }
}