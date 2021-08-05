package com.elegion.myfirstapplication.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.myfirstapplication.ApiUtils;
import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.albums.AlbumsActivity;
import com.elegion.myfirstapplication.help_class;
import com.elegion.myfirstapplication.model.Album;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.ApiUtils;
import com.elegion.myfirstapplication.App;
import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.db.MusicDao;
import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Song;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentsAlbumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ALBUM_KEY = "ALBUM_KEY";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private View mNoObjectsView;
    private Album mAlbum;
    private EditText mEditText;
    private Button mEnter;
    private boolean firstRefresh = true;
    @NonNull
    private final CommentsAdapter mCommentsAdapter = new CommentsAdapter();

    public static CommentsAlbumFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ALBUM_KEY, album);
        CommentsAlbumFragment fragment = new CommentsAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler_edit_text, container, false );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_comment);
        mRefresher = view.findViewById(R.id.refresher_comment);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
        mNoObjectsView = view.findViewById(R.id.noObjectsView);
        mEditText = view.findViewById(R.id.enter_comment);
        mEnter  = view.findViewById(R.id.buttonEnterComment);
        mEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mEditText.getText().length() != 0) {
                        postComments();
                        mEditText.setText("");
                        mEditText.clearFocus();
                    }
                    return true;
                } else {
                    Log.e("key", KeyEvent.keyCodeToString(keyCode));
                }
                return false;
            }
        });
        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().length() != 0){
                    postComments();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlbum = (Album) getArguments().getSerializable(ALBUM_KEY);
        getActivity().setTitle(mAlbum.getName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentsAdapter);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getComments();
        });

    }
    private void getComments() {
        ApiUtils.getApiService().getComments(mAlbum.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(
                        comments -> {
//                            Toast.makeText(getActivity(),  comments.get(0).toString(), Toast.LENGTH_LONG);
                            mErrorView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            if(comments.size()==0){
                                mNoObjectsView.setVisibility(View.VISIBLE);
                            } else {
                                mNoObjectsView.setVisibility(View.GONE);
                            }
                            if(mCommentsAdapter.getItemCount()==comments.size() && !firstRefresh) {
                                Toast.makeText(getActivity(), "Новых комментариев нет", Toast.LENGTH_SHORT).show();
                            } else if(mCommentsAdapter.getItemCount()!=0 && !firstRefresh) {
                                Toast.makeText(getActivity(), "Комментарии обновлены", Toast.LENGTH_SHORT).show();
                            }
                            firstRefresh=false;
                            mCommentsAdapter.addData(comments, true);
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mNoObjectsView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.e("error", throwable.getMessage());
                        }
                );
    }
    private void postComments() {
        mRefresher.setRefreshing(true);
        ApiUtils.getApiService().postComment(new help_class(mEditText.getText().toString(), mAlbum.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(
                        ()-> {
                            onRefresh();
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mNoObjectsView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.e("error", throwable.getMessage());
                        }
                );
    }
}
