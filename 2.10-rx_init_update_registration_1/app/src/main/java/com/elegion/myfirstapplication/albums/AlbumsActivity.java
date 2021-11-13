package com.elegion.myfirstapplication.albums;

import android.support.v4.app.Fragment;

import com.elegion.myfirstapplication.SingleFragmentActivity;

public class AlbumsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AlbumsFragment.newInstance();
    }
}
