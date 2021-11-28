package com.example.traininglog.ui.base.messages.create;

import android.content.Context;
import android.os.Bundle;
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
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.base.messages.MessagesFragment;
import com.example.traininglog.ui.base.messages.outcoming.OutcomingMessagesFragment;
import com.example.traininglog.utils.ApiUtils;

import java.util.Calendar;
import java.util.List;


public class CreateMessageFragment extends PresenterFragment implements Refreshable, CreateMessageView {


    private EditText mUser;
    private RefreshOwner mRefreshOwner;
    private EditText mHeading;
    private EditText mText;
    private Button mSaveButton;
    private Button mResetButton;
    @InjectPresenter
    CreateMessagePresenter mPresenter;
    @ProvidePresenter
    CreateMessagePresenter providePresenter(){return new CreateMessagePresenter();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
    }

    public CreateMessageFragment() {
        // Required empty public constructor
    }

    public static CreateMessageFragment newInstance() {
        return new CreateMessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUser = view.findViewById(R.id.sender);
        mHeading = view.findViewById(R.id.heading);
        mText = view.findViewById(R.id.message_text);
        mSaveButton = view.findViewById(R.id.create_message_button);
        mResetButton = view.findViewById(R.id.cleanButton);
        mResetButton.setOnClickListener(v -> {
            mUser.setText("");
            mHeading.setText("");
            mText.setText("");
        });
        mSaveButton.setOnClickListener(v -> {
            String name = mUser.getText().toString();
            if(name.length()!=0 && mHeading.getText().length()!=0) {
                mPresenter.getUsers(name);
            }
            else if (mHeading.getText().length()==0) {
                mHeading.setError("Ввыдите заголовок");
            }
            else {
                mUser.setError("Введите имя");
            }
        });
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
        if (throwable!=null && throwable.getMessage().contains("404")) {
            mUser.setError("Пользователь не найден");
            return;
        }
        Toast.makeText(getActivity(), "Ошибка сервера", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshData() {
    }

    @Override
    public void showUsers(List<User> users) {
        User user = new User(users.get(0).getId());
        User me = new User(ApiUtils.user_id);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 3);
        MessageCreate message = new MessageCreate(mHeading.getText().toString(), mText.getText().toString(), calendar.getTime(), me.getId(), user.getId());
        mPresenter.createMessage(message);
    }

    @Override
    public void saveMessage() {
        if(getParentFragment()!=null)
        ((MessagesFragment) getParentFragment()).changeFragment(OutcomingMessagesFragment.newInstance());
    }

    @Override
    protected CreateMessagePresenter getPresenter() {
        return mPresenter;
    }
}