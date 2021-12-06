package com.example.traininglog.ui.base.analysis.coach.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.traininglog.R;
import com.example.traininglog.ui.base.analysis.coach.CoachAnalysisFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonsFragment extends Fragment {
    private Button mGroupButton;
    private Button mUserButton;
    private Button mWorkoutsButton;

    public static ButtonsFragment newInstance() {
        return new ButtonsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGroupButton = view.findViewById(R.id.group_button);
        mUserButton = view.findViewById(R.id.users_button);
        mWorkoutsButton = view.findViewById(R.id.training_button);
        mGroupButton.setOnClickListener(v -> {
            if(getParentFragment()!=null)
                ((CoachAnalysisFragment) getParentFragment()).onGroupButtonClick();
        });
        mUserButton.setOnClickListener(v -> {
            if(getParentFragment()!=null)
                ((CoachAnalysisFragment) getParentFragment()).onUserButtonClick();
        });
        mWorkoutsButton.setOnClickListener(v -> {
            if(getParentFragment()!=null)
                ((CoachAnalysisFragment) getParentFragment()).onTrainingButtonClick();
        });
    }
}