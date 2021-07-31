package com.elegion.myfirstapplication;

import android.support.v4.app.Fragment;

/**
 * Created by tanchuev on 08.11.2017.
 */

public class AuthActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AuthFragment.newInstance();
    }
}
