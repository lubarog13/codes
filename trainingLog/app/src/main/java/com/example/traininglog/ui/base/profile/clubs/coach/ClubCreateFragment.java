package com.example.traininglog.ui.base.profile.clubs.coach;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.ClubCreate;
import com.example.traininglog.ui.base.profile.clubs.ClubsFragment;
import com.example.traininglog.utils.ApiUtils;
import com.example.traininglog.utils.ParseStringUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClubCreateFragment extends PresenterFragment implements ClubCreateView{

    private EditText mClubName;
    private MaterialSpinner mSpinner;
    private EditText mGroup;
    private Button mSaveButton;
    private Button mClearButton;
    private View mErrorView;
    private TextView mErrorText;
    private int building_id = 0;
    @InjectPresenter
    ClubCreatePresenter mPresenter;
    private RefreshOwner mRefreshOwner;

    @ProvidePresenter
    ClubCreatePresenter providePresenter() {return new ClubCreatePresenter();}

    public ClubCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    public static ClubCreateFragment newInstance() {
        return new ClubCreateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mClubName = view.findViewById(R.id.club_name);
        mSpinner = view.findViewById(R.id.club_building_address);
        mGroup = view.findViewById(R.id.club_group);
        mSaveButton = view.findViewById(R.id.save_button);
        mClearButton = view.findViewById(R.id.cleanButton);
        mErrorView = view.findViewById(R.id.error_view);
        mErrorText = view.findViewById(R.id.error_text);
        mSaveButton.setOnClickListener(v -> saveClub());
        mClearButton.setOnClickListener(v -> clear());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = getActivity().findViewById(R.id.create_title);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        mPresenter.getBuildings();
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
        mErrorText.setText("Ошибка подключения");
    }

    @Override
    public void showBuildings(List<Building> buildings) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Building building: buildings) {
            arrayList.add(ParseStringUtils.buildingAddress(building));
        }
        mSpinner.setItems(arrayList);
        mSpinner.setOnItemSelectedListener((view, position, id, item) -> building_id =
                buildings.stream().filter(building -> ParseStringUtils.buildingAddress(building)
                        .equals(item.toString())).collect(Collectors.toList()).get(0).getId());
        mSpinner.setSelectedIndex(0);
        building_id = buildings.get(0).getId();
    }
    private void saveClub() {
        String name = mClubName.getText().toString();
        String group = mGroup.getText().toString();
        if(name.length()==0 || group.length()==0) {
            mErrorView.setVisibility(View.VISIBLE);
            mErrorText.setText("Заполните все поля");
            return;
        }
        mErrorView.setVisibility(View.GONE);
        mPresenter.createClub(new ClubCreate(name, group, building_id, ApiUtils.coach_id, "111"));
    }

    private void clear() {
        mClubName.setText("");
        mGroup.setText("");
        mSpinner.setSelectedIndex(0);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void setOk() {
        ((ClubsFragment) getParentFragment()).hideFragment(this);
    }

    @Override
    protected ClubCreatePresenter getPresenter() {
        return mPresenter;
    }
}