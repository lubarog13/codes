package com.example.traininglog.ui.base.analysis.coach.group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.traininglog.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class GroupAnalysisFragment extends Fragment {

    private BarChart mChart;
    private Spinner mSpinner;

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
        mChart.addBar(new BarModel("девочки 1", 2.3f, 0xFF123456));
        mChart.addBar(new BarModel("девочки 2",2.f,  0xFF343456));
        mChart.addBar(new BarModel("девочки 3",3.3f, 0xFF563456));
        mChart.addBar(new BarModel("девочки 4",1.1f, 0xFF873F56));

        mChart.startAnimation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.week_days, R.layout.text_for_spinner);
        mSpinner.setAdapter(adapter);
    }
}