package com.example.traininglog.ui.base.messages.incoming;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Message;
import com.example.traininglog.ui.base.home.WorkoutAdapter;
import com.example.traininglog.ui.base.messages.MessageAdapter;
import com.example.traininglog.ui.base.messages.MessagesFragment;
import com.example.traininglog.ui.base.messages.update.UpdateMessageFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomingMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomingMessagesFragment extends PresenterFragment implements IncomingMessagesView, Refreshable, MessageAdapter.onItemClickListener {
    private RecyclerView mRecycler;
    private View mErrorView;
    private MessageAdapter mAdapter;
    private RefreshOwner mRefreshOwner;
    @InjectPresenter
    IncomingMessagesPresenter mPresenter;

    @ProvidePresenter
    IncomingMessagesPresenter providePresenter() {return new IncomingMessagesPresenter();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public static IncomingMessagesFragment newInstance() {
        return new IncomingMessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incoming_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.input_messagesRecycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(false);
        mAdapter = new MessageAdapter(true, this);
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
        mPresenter.getMessages();
    }

    @Override
    public void showMessages(List<Message> messages) {
        mRecycler.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mAdapter.addData(messages, false);
    }

    @Override
    protected IncomingMessagesPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onUpdateClick(int message_id) {
    }
}