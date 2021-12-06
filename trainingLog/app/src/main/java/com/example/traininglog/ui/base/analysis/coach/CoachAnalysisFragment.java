package com.example.traininglog.ui.base.analysis.coach;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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
import com.example.traininglog.ui.base.analysis.coach.base.ButtonsFragment;
import com.example.traininglog.ui.base.analysis.coach.group.GroupAnalysisFragment;

import java.io.IOException;
import java.io.InputStream;

public class CoachAnalysisFragment extends Fragment {
    private TextView textView;

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
        changeFragment(GroupAnalysisFragment.newInstance());
        textView.setText("Для групп");
    }

    public void onUserButtonClick() {
        Toast.makeText(getActivity(), "Users buttons", Toast.LENGTH_SHORT).show();
    }

    public void onTrainingButtonClick() {
        Toast.makeText(getActivity(), "Training buttons", Toast.LENGTH_SHORT).show();
    }

}