package com.example.traininglog.ui.base.messages.outcoming;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Message;
import com.example.traininglog.ui.base.messages.MessageAdapter;
import com.example.traininglog.ui.base.messages.incoming.IncomingMessagesPresenter;
import com.example.traininglog.ui.base.messages.incoming.IncomingMessagesView;

import java.util.List;

public class OutcomingMessagesFragment extends PresenterFragment implements Refreshable, IncomingMessagesView {

    private RecyclerView mRecycler;
    private View mErrorView;
    private MessageAdapter mAdapter;
    private RefreshOwner mRefreshOwner;
    private Button mCreateButton;
    @InjectPresenter
    IncomingMessagesPresenter mPresenter;

    @ProvidePresenter
    IncomingMessagesPresenter providePresenter() {return new IncomingMessagesPresenter();}

    public OutcomingMessagesFragment() {
        // Required empty public constructor
    }

    public static OutcomingMessagesFragment newInstance() {
        return new OutcomingMessagesFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outcoming_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.outcoming_messages_recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mCreateButton = view.findViewById(R.id.create_message_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(false);
        mAdapter = new MessageAdapter(false);
        mRecycler.setAdapter(mAdapter);
        onRefreshData();
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
        mRecycler.setVisibility(View.GONE);
        if (throwable!=null) Log.e("err", throwable.getMessage());
    }

    @Override
    public void onRefreshData() {
        mPresenter.getMessagesFromUser();
    }

    @Override
    public void showMessages(List<Message> messages) {
        mErrorView.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mAdapter.addData(messages, true);
    }

    @Override
    protected IncomingMessagesPresenter getPresenter() {
        return mPresenter;
    }
}