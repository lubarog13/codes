package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.PresenterFragment;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.ui.profile_projects.UserProjectsFragment;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.utils.DateUtils;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends PresenterFragment implements Refreshable, ProfileView {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private Button mButton;
    private Storage mStorage;
    @InjectPresenter
    ProfilePresenter mPresenter;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    @ProvidePresenter
    ProfilePresenter providePresenter() {
        Log.e("m", "mPresenter");
        return new ProfilePresenter(this, mStorage, mUsername);
    }


    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("context", context.getClass().getName());
        if (context instanceof Storage.StorageOwner) {
            Log.e("m", "mStorage");
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
            Log.e("refr", mRefreshOwner.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);

        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);
        mButton = view.findViewById(R.id.profile_projects);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }
        Log.e("m", "onCreated");
        mProfileView.setVisibility(View.VISIBLE);
        Log.e("username", mUsername);
        mPresenter.setUsername(mUsername);
        mPresenter.setView(this);
        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        Log.e("presenter", mPresenter.toString());
        mPresenter.getProfile();
    }

    @Override
    protected ProfilePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showRefresh() {
        Log.e("show", "show refresh");
        Log.e("refresh", mRefreshOwner.toString());
            mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
            mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mProfileView.setVisibility(View.GONE);
    }

    @Override
    public void showUser(User user) {
        Log.e("showUser", user.toString());
        Picasso.with(getContext())
                .load(user.getImage().getPhotoUrl())
                .fit()
                .into(mProfileImage);
        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
        mProfileView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }
    public void replaceFragment() {
        Log.e("rep", "replace");
        Bundle args = new Bundle();
        args.putString(PROFILE_KEY, mUsername);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UserProjectsFragment.newInstance(args), null)
                .addToBackStack(UserProjectsFragment.class.getName())
                .commit();
    }
    public String getUsername() {
        return mUsername;
    }
}
