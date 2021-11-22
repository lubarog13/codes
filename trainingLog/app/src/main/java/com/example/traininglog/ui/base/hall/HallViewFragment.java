
package com.example.traininglog.ui.base.hall;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.BuildConfig;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Hall;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HallViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HallViewFragment extends PresenterFragment implements SingleHallView, Refreshable {

    private static final String ARG_PARAM1 = "param1";
    private Storage mStorage;
    private RefreshOwner mRefreshOwner;
    private TextView mName;
    private TextView mNumber;
    private TextView mOccupancy;
    private TextView mAddress;
    private ImageView mPicture;
    @InjectPresenter
    SingleHallPresenter mPresenter;

    @ProvidePresenter
    SingleHallPresenter providePresenter() {return new SingleHallPresenter(mStorage);}

    private int mHallId;
    public HallViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HallViewFragment newInstance(int param1) {
        HallViewFragment fragment = new HallViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHallId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hall_view, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) {
            mRefreshOwner = (RefreshOwner) context;
        }
        if(context instanceof Storage.StorageOwner){
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        mName.setTypeface(typeFace);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddress = view.findViewById(R.id.hall_address);
        mName = view.findViewById(R.id.hall_main_name);
        mNumber = view.findViewById(R.id.hall_number);
        mOccupancy = view.findViewById(R.id.hall_occupancy);
        mPicture = view.findViewById(R.id.hall_image);
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
        if(throwable!=null){
            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshData() {
        mPresenter.getHall(mHallId);
    }

    @Override
    public void showHall(Hall hall) {
        Log.e("hall", hall.toString());
        mName.setText(hall.getName());
        if(hall.getNumber()!=0)
        mNumber.setText(String.valueOf(hall.getNumber()));
        else
            mNumber.setText("-");
        mOccupancy.setText(String.valueOf(hall.getOccupancy()));
        if(hall.getBuilding().getLiter()!=null)
        mAddress.setText(String.format("%s, %s, д.%s%s", hall.getBuilding().getCity(), hall.getBuilding().getAddress(), hall.getBuilding().getNumber(), hall.getBuilding().getLiter()));
        else
            mAddress.setText(String.format("%s, %s, д.%s", hall.getBuilding().getCity(), hall.getBuilding().getAddress(), hall.getBuilding().getNumber()));

        Picasso.with(mPicture.getContext()).load(BuildConfig.API_URL + "media/" + hall.getId() + "_hall.jpg")
        .fit()
        .into(mPicture);
    }

    @Override
    protected SingleHallPresenter getPresenter() {
        return mPresenter;
    }
}