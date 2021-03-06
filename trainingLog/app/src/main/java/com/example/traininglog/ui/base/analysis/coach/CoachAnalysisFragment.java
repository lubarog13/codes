package com.example.traininglog.ui.base.analysis.coach;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traininglog.R;
import com.example.traininglog.common.BaseView;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.ui.base.analysis.coach.base.ButtonsFragment;
import com.example.traininglog.ui.base.analysis.coach.group.GroupAnalysisFragment;
import com.example.traininglog.ui.base.analysis.coach.trainings.WorkoutAnalysisFragment;
import com.example.traininglog.ui.base.analysis.coach.users.UserAnalysisButtonFragment;

import java.io.IOException;
import java.io.InputStream;

public class CoachAnalysisFragment extends Fragment implements BaseView, Refreshable
{
    private TextView textView;
    private RefreshOwner mRefreshOwner;
    private Fragment mChildFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public CoachAnalysisFragment() {
        // Required empty public constructor
    }

    public static CoachAnalysisFragment newInstance() {
        return new CoachAnalysisFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach_analysis, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = getActivity().findViewById(R.id.analysis_title);
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
        changeFragment(ButtonsFragment.newInstance());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction()
                .replace(R.id.analysis_conteiner, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void onGroupButtonClick() {
        mChildFragment = GroupAnalysisFragment.newInstance();
        changeFragment(mChildFragment);
        textView.setText("?????? ??????????");
    }

    public void onUserButtonClick() {
        mChildFragment = UserAnalysisButtonFragment.newInstance();
        changeFragment(mChildFragment);
        textView.setText("?????? ????????????????????????");
    }

    public void onTrainingButtonClick() {
        mChildFragment = WorkoutAnalysisFragment.newInstance();
        changeFragment(mChildFragment);
        textView.setText("?????? ????????????????????");
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

    }

    @Override
    public void onRefreshData() {
        if(mChildFragment!=null && mChildFragment instanceof Refreshable) ((Refreshable) mChildFragment).onRefreshData();
    }
}