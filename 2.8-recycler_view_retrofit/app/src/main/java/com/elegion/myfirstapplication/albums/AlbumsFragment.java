package com.elegion.myfirstapplication.albums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.myfirstapplication.ApiUtils;
import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.model.Albums;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Azret Magometov
 */

public class AlbumsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;

    @NonNull
    private final AlbumsAdapter mAlbumAdapter = new AlbumsAdapter();

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAlbumAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getAlbums();
        });
    }

    private void getAlbums() {

        ApiUtils.getApiService().getAlbums().enqueue(new Callback<Albums>() {
            @Override
            public void onResponse(Call<Albums> call, Response<Albums> response) {
                if (response.isSuccessful()) {
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAlbumAdapter.addData(response.body().getData(), true);
                } else {
                    mErrorView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    Log.e("code", Integer.toString(response.code()));
                }
                mRefresher.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Albums> call, Throwable t) {
                Log.e("throwable", t.getMessage());
                mErrorView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mRefresher.setRefreshing(false);
            }
        });
    }

}
