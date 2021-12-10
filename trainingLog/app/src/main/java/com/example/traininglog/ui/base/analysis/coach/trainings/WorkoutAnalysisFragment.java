package com.example.traininglog.ui.base.analysis.coach.trainings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.data.model.TypesResponse;
import com.example.traininglog.utils.ApiUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.Comparator;
import java.util.List;


public class WorkoutAnalysisFragment extends PresenterFragment implements WorkoutAnalysisView, Refreshable {
    private TextView mTotalCount;
    private PieChart pieChart;
    private View mErrorView;
    private View mView;
    private View mHideView;
    private RefreshOwner mRefreshOwner;
    private TextView mCardioCount;
    private TextView mStrengthCount;
    private TextView mAnotherCount;
    private TextView mForAllCount;
    private TextView mForTechCount;
    private TextView mTotalCountText;
    private Button mAllTrainButton;
    private TextView mTypeText;
    private MaterialSpinner mSpinner;
    private BarChart mChart;
    private String month = "Всего";
    @InjectPresenter
    WorkoutAnalysisPresenter mPresenter;
    @ProvidePresenter
    WorkoutAnalysisPresenter providePresenter(){return new WorkoutAnalysisPresenter();}
    public static WorkoutAnalysisFragment newInstance() {
        return new WorkoutAnalysisFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mErrorView = view.findViewById(R.id.errorView);
        mView = view.findViewById(R.id.analysis_content);
        mTypeText = view.findViewById(R.id.typeText);
        mHideView = view.findViewById(R.id.hide_view);
        pieChart = view.findViewById(R.id.piechart);
        mAllTrainButton = view.findViewById(R.id.all_train_button);
        mAllTrainButton.setOnClickListener(v -> mPresenter.getWorkoutCount());
        mTotalCount = view.findViewById(R.id.total_count);
        Shader textShader = new LinearGradient(0, 0,0, mTotalCount.getTextSize(),
                new int[]{
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#ED6C30"),
                        Color.parseColor("#1A237E"),
                }, null, Shader.TileMode.CLAMP);
        mTotalCount.getPaint().setShader(textShader);
        mTotalCount.setTextColor(Color.parseColor("#F8000D"));
        mTotalCountText = view.findViewById(R.id.trainings);
        Shader textShader1 = new LinearGradient(0, 0,0, mTotalCountText.getTextSize(),
                new int[]{
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#ED6C30"),
                        Color.parseColor("#1A237E"),
                }, null, Shader.TileMode.CLAMP);
        mTotalCountText.getPaint().setShader(textShader1);
        mTotalCountText.setTextColor(Color.parseColor("#F8000D"));
        mCardioCount = view.findViewById(R.id.cardio_workout);
        mForAllCount = view.findViewById(R.id.all_workout);
        mForTechCount = view.findViewById(R.id.technic_workout);
        mStrengthCount = view.findViewById(R.id.strong_workout);
        mAnotherCount = view.findViewById(R.id.other_workout);
        mSpinner = view.findViewById(R.id.month_spinner);
        mChart = view.findViewById(R.id.barchart);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.months, R.layout.text_for_spinner);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                month = item.toString();
                onRefreshData();
            }
        });
        onRefreshData();
    }

    @Override
    public void showAnalysisForTypes(TypesResponse typesResponse, int month) {
        mErrorView.setVisibility(View.GONE);
        if(month==-1){
            mAllTrainButton.setVisibility(View.GONE);
            mTypeText.setText("Всего:");
        }
        else {
            mAllTrainButton.setVisibility(View.VISIBLE);
            mTypeText.setText(getMonth(month));
        }
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("общие", typesResponse.getFor_all(), getResources().getColor(R.color.colorRed)));
        pieChart.addPieSlice(
                new PieModel("кардио", typesResponse.getCardio(), getResources().getColor(R.color.colorOrange)));
        pieChart.addPieSlice(
                new PieModel("другое", typesResponse.getAnother(), getResources().getColor(R.color.colorYellow)));
        pieChart.addPieSlice(new PieModel("силовые", typesResponse.getStrength(), getResources().getColor(R.color.colorGreen)));
        pieChart.addPieSlice(new PieModel("на технику", typesResponse.getFor_tech(), getResources().getColor(R.color.colorPrimaryDark)));
        pieChart.startAnimation();
        int totalCount = typesResponse.getFor_all() + typesResponse.getFor_tech() + typesResponse.getAnother() + typesResponse.getCardio() + typesResponse.getStrength();
        mTotalCount.setText(String.valueOf(totalCount));
        mTotalCountText.setText(getTotalCountText(totalCount));
        mCardioCount.setText(String.format("%s кардио", String.valueOf(typesResponse.getCardio())));
        mStrengthCount.setText(String.format("%s силовых", String.valueOf(typesResponse.getStrength())));
        mForAllCount.setText(String.format("%s общих", String.valueOf(typesResponse.getFor_all())));
        mAnotherCount.setText(String.format("%s других", String.valueOf(typesResponse.getAnother())));
        mForTechCount.setText(String.format("%s на технику", String.valueOf(typesResponse.getFor_tech())));
        mPresenter.getWorkoutForTypes(setMonth(this.month));
    }

    @Override
    public void showWorkoutAnalysis(List<GroupAnalysis.GroupAnalysisItem> workouts, List<GroupAnalysis.GroupAnalysisItem> presences) {
        mChart.clearChart();
        Log.e("presences", presences.toString());
        Log.e("workouts", workouts.toString());
        workouts.sort(new Comparator<GroupAnalysis.GroupAnalysisItem>() {
            @Override
            public int compare(GroupAnalysis.GroupAnalysisItem o1, GroupAnalysis.GroupAnalysisItem o2) {
                return o1.getClubGroup().compareTo(o2.getClubGroup());
            }
        });
        presences.sort(new Comparator<GroupAnalysis.GroupAnalysisItem>() {
            @Override
            public int compare(GroupAnalysis.GroupAnalysisItem o1, GroupAnalysis.GroupAnalysisItem o2) {
                return o1.getGroup().compareTo(o2.getGroup());
            }
        });
        for (int i=0; i<workouts.size(); i++) {
            for(int j=0; j<presences.size(); j++) {
                if(presences.get(j).getGroup().equals(workouts.get(i).getClubGroup())) {
                    float pplCount = 0;
                    if (presences.get(j).getPresenceCount() != 0) {
                        pplCount = (float) presences.get(j).getPresenceCount() / workouts.get(i).getWcount();
                    }
                    String name = workouts.get(i).getClubGroup();
                    Log.e("name", name);
                    mChart.addBar(new BarModel(name, pplCount,  getResources().getColor(R.color.colorPrimaryDark)));
                    break;
                }
            }
        }
        mChart.startAnimation();
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
    private String getTotalCountText(int totalCount) {
        String totalCountText = "тренировки";
        if(totalCount==1) return "тренировка";
        if (totalCount<5) return totalCountText;
        if(totalCount<21) return "тренировок";
        if (totalCount%10==0) return "тренировок";
        if(totalCount%10==1) return "тренировка";
        if (totalCount%10<5) return totalCountText;
        return "тренировок";
    }
    private String getMonth(int month) {
        switch (month-1){
            case 0:
                return "Январь:";
            case 1:
                return "Февраль:";
            case 2:
                return "Март:";
            case 3:
                return "Апрель:";
            case 4:
                return "Май:";
            case 5:
                return "Июнь:";
            case 6:
                return "Июль:";
            case 7:
                return "Август:";
            case 8:
                return "Сентябрь:";
            case 9:
                return "Октябрь:";
            case 10:
                return "Ноябрь:";
            case 11:
                return "Декабрь:";
        }
        return "";
    }

    private String setMonth(String month){
        switch (month) {
            case "Всего":
                return "all";
            case "Январь":
                return "1";
            case "Февраль":
                return "2";
            case "Март":
                return "3";
            case "Апрель":
                return "4";
            case "Май":
                return "5";
            case "Июнь":
                return "6";
            case "Июль":
                return "7";
            case "Август":
                return "8";
            case "Сентябрь":
                return "9";
            case "Октябрь":
                return "10";
            case "Ноябрь":
                return "11";
            case "Декабрь":
                return "12";
        }
        return "all";
    }



    @Override
    public void onRefreshData() {
        if(month.equals("Всего"))
        mPresenter.getWorkoutCount();
        else mPresenter.getAnalysisForMonthsForTypes(ApiUtils.user_id, Integer.parseInt(setMonth(month)));
    }

    @Override
    protected WorkoutAnalysisPresenter getPresenter() {
        return mPresenter;
    }
}