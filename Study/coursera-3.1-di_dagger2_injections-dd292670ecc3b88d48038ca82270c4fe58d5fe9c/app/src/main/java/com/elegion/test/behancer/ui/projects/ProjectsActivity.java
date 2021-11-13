package com.elegion.test.behancer.ui.projects;

import android.support.v4.app.Fragment;

import com.elegion.test.behancer.common.SingleFragmentActivity;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }
}
