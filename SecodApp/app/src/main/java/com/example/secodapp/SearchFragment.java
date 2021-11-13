package com.example.secodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    private SharedPreferencesHelper mSharedPreferencesHelper;
    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
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

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText text = view.findViewById(R.id.question);
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = text.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSharedPreferencesHelper.search(question)));
                startActivity(intent);
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
                        .addToBackStack(SecondFragment.class.getName())
                        .commit();
                return true;
            case R.id.action_search:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, SearchFragment.newInstance())
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
