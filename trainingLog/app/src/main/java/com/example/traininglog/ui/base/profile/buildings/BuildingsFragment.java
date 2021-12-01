package com.example.traininglog.ui.base.profile.buildings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.buildings.BuildingAdapter;
import com.example.traininglog.ui.base.profile.halls.HallFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildingsFragment extends PresenterFragment implements BuildingsView, Refreshable, BuildingAdapter.OnItemClickListener {

    private RecyclerView mRecycler;
    private BuildingAdapter mBuildingAdapter;
    private View mErrorView;
    private Storage mStorage;
    private boolean saveData;
    private RefreshOwner mRefreshOwner;
    @InjectPresenter
    BuildingsPresenter mPresenter;

    @ProvidePresenter
    BuildingsPresenter providePresenter() {
        return new BuildingsPresenter(mStorage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    public static BuildingsFragment newInstance() {
        return new BuildingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buildings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.buildings);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBuildingAdapter = new BuildingAdapter(this);
        mRecycler.setAdapter(mBuildingAdapter);
        ImageView imageView = getActivity().findViewById(R.id.buildings_image);
        saveData = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("save_data", true);
        try
        {
            InputStream ims = getActivity().getAssets().open("image 17.png");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
            return;
        }
        onRefreshData();
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
        mRecycler.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshData() {
        mPresenter.getBuildings(saveData);
    }

    @Override
    public void showBuildings(List<Building> buildings) {
        mErrorView.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mBuildingAdapter.addData(buildings, true);
    }

    @Override
    public void onBuildingClick(Building building, String name) {
        ((HomeActivity) getActivity()).changeFragment(HallFragment.newInstance(name, building.getId()));
    }

    @Override
    protected BuildingsPresenter getPresenter() {
        return mPresenter;
    }
}