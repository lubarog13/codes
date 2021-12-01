package com.example.traininglog.ui.base.profile.halls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.hall.HallViewFragment;
import com.example.traininglog.ui.base.profile.buildings.BuildingsFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HallFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HallFragment extends PresenterFragment implements Refreshable, HallView, HallAdapter.onItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecycler;
    private RefreshOwner mRefreshOwner;
    private boolean saveData;
    private HallAdapter mHallAdapter;
    private View mErrorView;
    @InjectPresenter
    HallPresenter mPresenter;
    @ProvidePresenter
    HallPresenter providePresenter() {
        return new HallPresenter(mStorage);
    }

    // TODO: Rename and change types of parameters
    private String mBuildingName;
    private int mBuildingId;
    private Storage mStorage;
    private Button mBuildingButton;
    public static HallFragment newInstance(String param1, int param2) {
        HallFragment fragment = new HallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner)
            mRefreshOwner = ((RefreshOwner) context);
        if (context instanceof Storage.StorageOwner){
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    public HallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBuildingName = getArguments().getString(ARG_PARAM1);
            mBuildingId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.halls_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mBuildingButton = view.findViewById(R.id.item_button);
        mBuildingButton.setText(mBuildingName);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHallAdapter  = new HallAdapter(this);
        mRecycler.setAdapter(mHallAdapter);
        mBuildingButton.setOnClickListener(v -> ((HomeActivity) getActivity()).changeFragment(BuildingsFragment.newInstance()));
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
        mErrorView.setVisibility(View.VISIBLE);
        if(throwable!=null)
        Log.e("err", throwable.getMessage());
        mRecycler.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshData() {
        mPresenter.getHalls(mBuildingId, saveData);
    }

    @Override
    public void showHalls(List<Hall> halls) {
        mErrorView.setVisibility(View.GONE);
        mHallAdapter.addDate(halls, true);
        mRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    protected HallPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onClick(int hall_id) {
        ((HomeActivity) getActivity()).changeFragment(HallViewFragment.newInstance(hall_id));
    }
}