package com.example.traininglog.ui.base.analysis;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.MonthsResponse;
import com.example.traininglog.data.model.TypesResponse;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.analysis.coach.CoachAnalysisFragment;
import com.example.traininglog.ui.base.profile.halls.HallFragment;
import com.example.traininglog.utils.ApiUtils;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AnalysisFragment extends PresenterFragment implements Refreshable, AnalysisView {

    private static final String  ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mTotalCount;
    private PieChart pieChart;
    private View mErrorView;
    private View mView;
    private View mHideView;
    private ValueLineChart mCubicValueLineChart;
    private RefreshOwner mRefreshOwner;
    private TextView mCardioCount;
    private TextView mStrengthCount;
    private TextView mAnotherCount;
    private TextView mForAllCount;
    private TextView mForTechCount;
    private TextView mTotalCountText;
    private Button mAllTrainButton;
    private TextView mTypeText;
    private int mUserId;
    private String mUserName;
    @InjectPresenter
    AnalysisPresenter mPresenter;
    @ProvidePresenter
    AnalysisPresenter providePresenter() {return new AnalysisPresenter();}

    public AnalysisFragment() {
        // Required empty public constructor
    }

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    public static AnalysisFragment newInstance(int param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_PARAM1);
            mUserName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner)
            mRefreshOwner = (RefreshOwner) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
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
         mAllTrainButton.setOnClickListener(v -> mPresenter.getAnalysisForTypes(mUserId));
         mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
        mCubicValueLineChart.setOnPointFocusedListener(_PointPos -> mPresenter.getAnalysisForMonthsForTypes(mUserId, _PointPos));
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(ApiUtils.coach_id!=-1 && mUserName==null){
            ((HomeActivity) getActivity()).changeFragment(CoachAnalysisFragment.newInstance());
            return;
        }
        TextView textView = getActivity().findViewById(R.id.analysis_title);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        ImageView imageView = getActivity().findViewById(R.id.analysis_image);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("Group 60.png");
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
        if(mUserName!=null){
            textView.setText(mUserName);
        } else {
            mUserId = ApiUtils.user_id;
        }
        if(getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("show_analysis", true))
        onRefreshData();
        else {
            mHideView.setVisibility(View.VISIBLE);
            mView.setVisibility(View.GONE);
        }
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
        mView.setVisibility(View.GONE);
        if(throwable!= null) Log.e("err", throwable.getMessage());
    }

    @Override
    public void onRefreshData() {
        if(!getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("show_analysis", true)) {
            hideRefresh();
            return;
        }
        mPresenter.getAnalysisForTypes(mUserId);
    }

    @Override
    public void showAnalysisForTypes(TypesResponse typesResponse, int month) {
        if(month==-1){
            mAllTrainButton.setVisibility(View.GONE);
            mTypeText.setText("??????????:");
        }
        else {
            mAllTrainButton.setVisibility(View.VISIBLE);
            mTypeText.setText(getMonth(month));
        }
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("??????????", typesResponse.getFor_all(), getResources().getColor(R.color.colorRed)));
        pieChart.addPieSlice(
                new PieModel("????????????", typesResponse.getCardio(), getResources().getColor(R.color.colorOrange)));
        pieChart.addPieSlice(
                new PieModel("????????????", typesResponse.getAnother(), getResources().getColor(R.color.colorYellow)));
        pieChart.addPieSlice(new PieModel("??????????????", typesResponse.getStrength(), getResources().getColor(R.color.colorGreen)));
        pieChart.addPieSlice(new PieModel("???? ??????????????", typesResponse.getFor_tech(), getResources().getColor(R.color.colorPrimaryDark)));
        pieChart.startAnimation();
        int totalCount = typesResponse.getFor_all() + typesResponse.getFor_tech() + typesResponse.getAnother() + typesResponse.getCardio() + typesResponse.getStrength();
        mTotalCount.setText(String.valueOf(totalCount));
        mTotalCountText.setText(getTotalCountText(totalCount));
        mCardioCount.setText(String.format("%s ????????????", String.valueOf(typesResponse.getCardio())));
        mStrengthCount.setText(String.format("%s ??????????????", String.valueOf(typesResponse.getStrength())));
        mForAllCount.setText(String.format("%s ??????????", String.valueOf(typesResponse.getFor_all())));
        mAnotherCount.setText(String.format("%s ????????????", String.valueOf(typesResponse.getAnother())));
        mForTechCount.setText(String.format("%s ???? ??????????????", String.valueOf(typesResponse.getFor_tech())));
    }

    @Override
    public void showAnalysisForMonths(MonthsResponse monthsResponse) {
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(getResources().getColor(R.color.lightBlue));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getJan()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getFeb()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getMar()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getApr()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getMay()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getJun()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getJul()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getAug()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getSep()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getOct()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getNov()));
        series.addPoint(new ValueLinePoint("??????", monthsResponse.getDec()));
        mCubicValueLineChart.clearChart();
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    @Override
    protected AnalysisPresenter getPresenter() {
        return mPresenter;
    }

    private String getTotalCountText(int totalCount) {
        String totalCountText = "????????????????????";
        if(totalCount==1) return "????????????????????";
        if (totalCount<5) return totalCountText;
        if(totalCount<21) return "????????????????????";
        if (totalCount%10==0) return "????????????????????";
        if(totalCount%10==1) return "????????????????????";
        if (totalCount%10<5) return totalCountText;
        return "????????????????????";
    }
    private String getMonth(int month) {
        switch (month){
            case 0:
                return "????????????:";
            case 1:
                return "??????????????:";
            case 2:
                return "????????:";
            case 3:
                return "????????????:";
            case 4:
                return "??????:";
            case 5:
                return "????????:";
            case 6:
                return "????????:";
            case 7:
                return "????????????:";
            case 8:
                return "????????????????:";
            case 9:
                return "??????????????:";
            case 10:
                return "????????????:";
            case 11:
                return "??????????????:";
        }
        return "";
    }
}