package com.example.secodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {
    private SharedPreferencesHelper mSharedPreferencesHelper;
    public static SecondFragment newInstance() {
        Bundle args = new Bundle();

        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.check(mSharedPreferencesHelper.login());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        mSharedPreferencesHelper.addBrowser("Google");
                        break;
                    case R.id.google:
                        mSharedPreferencesHelper.addBrowser("Google");
                        break;
                    case R.id.yandex:
                        mSharedPreferencesHelper.addBrowser("Yandex");
                        break;
                    case R.id.bing:
                        mSharedPreferencesHelper.addBrowser("Bing");
                        break;

                    default:
                        break;
                }
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, SecondFragment.newInstance())
                        .commit();
                return true;
            case R.id.action_search:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, SearchFragment.newInstance())
                        .addToBackStack(SearchFragment.class.getName())
                        .commit();
                return true;
            case R.id.action_exit:
                getActivity().finish();
                System.exit(0);
            default:
                // Not one of ours. Perform default menu processing
                return super.onOptionsItemSelected(item);
        }
    }
}