package com.example.traininglog.ui.base.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.auth.MainActivity;
import com.example.traininglog.ui.base.home.HomePresenter;
import com.example.traininglog.ui.base.profile.buildings.BuildingsFragment;
import com.example.traininglog.ui.base.profile.clubs.ClubsFragment;
import com.example.traininglog.ui.base.profile.coaches.CoachFragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends PresenterFragment implements ProfileView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView mProfileImage;
    private TextView mUsername;
    private View mClubs;
    private View mCoaches;
    private View mHalls;
    private View mAbout;
    private View mLogout;
    private SharedPreferences sp;
    @InjectPresenter
     ProfilePresenter mPresenter;

    @ProvidePresenter
    ProfilePresenter providePresenter() {
        return new ProfilePresenter();
    }

    public ProfileFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAbout = view.findViewById(R.id.about);
        mClubs = view.findViewById(R.id.clubs);
        mHalls = view.findViewById(R.id.halls);
        mCoaches = view.findViewById(R.id.coaches);
        mLogout = view.findViewById(R.id.logout);
        mClubs.setOnClickListener(view1 -> changeFragment(ClubsFragment.class));
        mCoaches.setOnClickListener(view1 -> changeFragment(CoachFragment.class));
        mProfileImage = view.findViewById(R.id.profile_image);
        mHalls.setOnClickListener(v -> changeFragment(BuildingsFragment.class));
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 13.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mProfileImage.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {

        }
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sp.getString("last_name", "") + "\n" + sp.getString("first_name", "") + "\n" + sp.getString("second_name", "");
        mUsername = view.findViewById(R.id.username);
        mUsername.setText(name);
        mPresenter.setSp(sp);
        mLogout.setOnClickListener(view1 -> mPresenter.logout());
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void hideRefresh() {

    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(getActivity(), "Ошибка интернет-соединения", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ProfilePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goToAuth() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void changeFragment(Class fragmentClass){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((HomeActivity) getActivity()).changeFragment(fragment);
    }

}