package com.example.traininglog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.traininglog.AppDelegate;
import com.example.traininglog.R;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.ui.base.schedule.ScheduleFragment;
import com.example.traininglog.ui.base.home.HomeFragment;
import com.example.traininglog.ui.base.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeActivity extends AppCompatActivity implements Storage.StorageOwner, RefreshOwner, SwipeRefreshLayout.OnRefreshListener {
    private View mAnView;
    private Fragment currentFragment;
    public static boolean is_firstOpened = true;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_week:
                    changeFragment(HomeFragment.newInstance());
                    break;
                case R.id.navigation_shedule:
                    changeFragment(ScheduleFragment.newInstance());
                    break;
                case R.id.navigation_log:
                    changeFragment(ScheduleFragment.newInstance());
                    break;
                case R.id.navigation_analysis:
                    changeFragment(ScheduleFragment.newInstance());
                    break;
                case R.id.navigation_profile:
                    changeFragment(ProfileFragment.newInstance());
                    break;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAnView = findViewById(R.id.nav_host_fragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        Log.e("NotErr", "NotErr");
        mSwipeRefreshLayout = findViewById(R.id.home_refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        setTitle("");
        Intent intent = getIntent();
        if(is_firstOpened) {
            changeFragment(HomeFragment.newInstance());
            is_firstOpened=false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            Toast.makeText(this, "message", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }


    public void changeFragment(Fragment fragment) {
        Log.e("fragment", fragment.getClass().getSimpleName());
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment);
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }


    @Override
    public void onRefresh() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (fragment instanceof Refreshable) {
            ((Refreshable) fragment).onRefreshData();
        } else {
            setRefreshState(false);
        }
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }
}