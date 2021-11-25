package com.example.traininglog.ui.base.analysis;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.traininglog.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment {

    private TextView mTotalCount;
    private PieChart pieChart;
    private ValueLineChart mCubicValueLineChart;

    public AnalysisFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
         pieChart = view.findViewById(R.id.piechart);
        pieChart.addPieSlice(
                new PieModel("кардио", 10f, getResources().getColor(R.color.colorRed)));
         pieChart.addPieSlice(new PieModel("силовые", 10f, getResources().getColor(R.color.colorGreen)));
         pieChart.addPieSlice(new PieModel("на технику", 10f, getResources().getColor(R.color.colorOrange)));
         pieChart.startAnimation();
         mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(getResources().getColor(R.color.lightBlue));

        series.addPoint(new ValueLinePoint("янв", 10));
        series.addPoint(new ValueLinePoint("фев", 8));
        series.addPoint(new ValueLinePoint("мар", 9));
        series.addPoint(new ValueLinePoint("апр", 16));
        series.addPoint(new ValueLinePoint("май", 12));
        series.addPoint(new ValueLinePoint("июн", 10));
        series.addPoint(new ValueLinePoint("июл", 0));
        series.addPoint(new ValueLinePoint("авг", 6));
        series.addPoint(new ValueLinePoint("сен", 12));
        series.addPoint(new ValueLinePoint("окт", 14));
        series.addPoint(new ValueLinePoint("ноя", 12));
        series.addPoint(new ValueLinePoint("дек", 10));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.setOnPointFocusedListener(_PointPos -> Toast.makeText(getActivity(), String.valueOf(_PointPos), Toast.LENGTH_SHORT).show());
        mCubicValueLineChart.startAnimation();
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
        TextView textView = view.findViewById(R.id.trainings);
        Shader textShader1 = new LinearGradient(0, 0,0, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#ED6C30"),
                        Color.parseColor("#1A237E"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader1);
        textView.setTextColor(Color.parseColor("#F8000D"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }
}