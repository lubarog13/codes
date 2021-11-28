package com.example.traininglog.ui.base.messages.update;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.Message;
import com.example.traininglog.data.model.MessageCreate;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.base.messages.MessagesFragment;
import com.example.traininglog.ui.base.messages.outcoming.OutcomingMessagesFragment;
import com.example.traininglog.utils.ApiUtils;

import java.util.Calendar;
import java.util.Date;


public class UpdateMessageFragment extends PresenterFragment implements Refreshable, UpdateMessageView {

    private static final String ARG_PARAM1 = "param1";
    private Message mMessage;
    private int mMessageId;
    private EditText mUser;
    private RefreshOwner mRefreshOwner;
    private EditText mHeading;
    private EditText mText;
    private Button mSaveButton;
    private Button mResetButton;
    private Button mDeleteButton;
    @InjectPresenter
    UpdateMessagePresenter mPresenter;
    @ProvidePresenter
    UpdateMessagePresenter providePresenter(){return new UpdateMessagePresenter();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public static UpdateMessageFragment newInstance(int message_id ) {
       UpdateMessageFragment fragment = new UpdateMessageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, message_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessageId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUser = view.findViewById(R.id.sender);
        mHeading = view.findViewById(R.id.heading);
        mText = view.findViewById(R.id.message_text);
        mSaveButton = view.findViewById(R.id.create_message_button);
        mResetButton = view.findViewById(R.id.cleanButton);
        mDeleteButton = view.findViewById(R.id.delete_button);
        mResetButton.setOnClickListener(v -> {
            mHeading.setText(mMessage.getHeding());
            mText.setText(mMessage.getMessage());
        });
        mSaveButton.setOnClickListener(v->{
            if (mHeading.getText().length()==0) {
                mHeading.setError("Введите заголовок");
            }
            else {
                mPresenter.updateMessage(mMessageId, new MessageCreate(mHeading.getText().toString(),
                        mText.getText().toString(),mMessage.getSend_time(), ApiUtils.user_id, mMessage.getRecipient().getId()));
            }
        });
            mDeleteButton.setOnClickListener(v -> {
                mPresenter.deleteMessage(mMessageId);
            });
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
        if (throwable!=null)
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshData() {
        mPresenter.getMessage(mMessageId);
    }

    @Override
    public void showMessage(Message message) {
        mMessage = message;
        mUser.setText(message.getRecipient().getUsername());
        mHeading.setText(message.getHeding());
        mUser.setEnabled(false);
        if(message.getMessage()!=null)
        mText.setText(message.getMessage());
    }

    @Override
    public void saveMessage() {
        if(getParentFragment()!=null)
        ((MessagesFragment) getParentFragment()).changeFragment(OutcomingMessagesFragment.newInstance());
    }



    @Override
    protected UpdateMessagePresenter getPresenter() {
        return mPresenter;
    }
}