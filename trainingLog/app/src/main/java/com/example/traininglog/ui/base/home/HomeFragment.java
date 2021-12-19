package com.example.traininglog.ui.base.home;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.hall.HallViewFragment;
import com.example.traininglog.ui.base.home.coach.CoachWorkoutAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class HomeFragment extends PresenterFragment implements HomeView, Refreshable, WorkoutAdapter.OnItemClickListener, CoachWorkoutAdapter.OnItemClickListener {
    @InjectPresenter
    HomePresenter mPresenter;
    private CoachWorkoutAdapter mCoachWorkoutAdapter;
    private RecyclerView mRecyclerView;
    private View mErrorView;
    private View mHomeView;
    private Storage mStorage;
    private WorkoutAdapter mWorkoutAdapter;
    private boolean showData = true;
    private RefreshOwner mRefreshOwner;
    private boolean is_coach;

    public static HomeFragment newInstance() {return new HomeFragment();}

    @ProvidePresenter
    HomePresenter providePresenter() {
        return new HomePresenter(mStorage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            Log.e("m", "mStorage");
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.week_rec);
        mErrorView = view.findViewById(R.id.errorView);
        mHomeView = view.findViewById(R.id.home_view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        is_coach = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("is_coach", false);
        mCoachWorkoutAdapter = new CoachWorkoutAdapter(this, this);
        mWorkoutAdapter = new WorkoutAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(is_coach? mCoachWorkoutAdapter: mWorkoutAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("token", token);
                    }
                });
        TextView textView = getActivity().findViewById(R.id.week_workouts);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        showData = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getBoolean("save_data", true);

        ImageView imageView = getActivity().findViewById(R.id.image_main);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 2.png");
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
        if(is_coach){
            mPresenter.getData();
        }
        else onRefreshData();
    }

    @Override
    protected HomePresenter getPresenter() {
        return mPresenter;
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
        mHomeView.setVisibility(View.GONE);
        this.hideRefresh();
        if(throwable!=null)
        Log.e("err", throwable.getMessage());
    }



    @Override
    public void onRefreshData() {
        mPresenter.getWorkouts(showData);
    }

    @Override
    public void saveWorkouts(List<Workout> workouts) {
        mErrorView.setVisibility(View.GONE);
        mHomeView.setVisibility(View.VISIBLE);
        if(is_coach)
            mCoachWorkoutAdapter.addData(workouts, true);
        else mWorkoutAdapter.addData(workouts, true);
    }

    @Override
    public void updatePresence(boolean is_attend) {

    }

    @Override
    public void showPresences(List<Presence_W_N> presences) {
        if(is_coach) mCoachWorkoutAdapter.addPresences(presences);
        else mWorkoutAdapter.addPresences(presences);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), "Ошибка интернет-соединения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValues(List<Hall> halls, List<Coach> coaches, List<Club> clubs) {
        mCoachWorkoutAdapter.addValues(coaches, halls, clubs);
        onRefreshData();
    }

    @Override
    public void updateComplete() {
        onRefreshData();
    }

    @Override
    public void onItemClick(int workout_id, boolean is_attend) {
        if (is_attend) {
            mPresenter.setPresence(workout_id);
        } else {
            mPresenter.resetPresence(workout_id);
        }
    }

    @Override
    public void onPresencesClick(int workout_id) {
        mPresenter.getPresences(workout_id);
    }

    @Override
    public void removePresences(int workout_id) {
        if(is_coach) mCoachWorkoutAdapter.removePresence(workout_id);
        else mWorkoutAdapter.removePresence(workout_id);
    }

    @Override
    public void onSendClick(String reason, int workout_id) {
        mPresenter.updateReason(reason, workout_id);
    }

    @Override
    public void onHallClick(int hall_id) {
        ((HomeActivity) getActivity()).changeFragment(HallViewFragment.newInstance(hall_id));
    }

    @Override
    public void onDetach() {
        mStorage = null;
        super.onDetach();
    }

    @Override
    public void editWorkout(WorkoutForEdit workout) {
        mPresenter.updateWorkout(workout);
    }
}