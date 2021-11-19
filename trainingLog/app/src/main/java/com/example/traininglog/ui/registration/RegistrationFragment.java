package com.example.traininglog.ui.registration;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.auth.AuthFragment;
import com.example.traininglog.ui.auth.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends PresenterFragment implements RegistrationView, Refreshable {

    private ImageView mMainImage;
    private View mFirstView;
    private View mSecondView;
    private EditText mFirstname;
    private EditText mLastname;
    private EditText mSecondName;
    private EditText mEmail;
    private ToggleButton mMaleButton;
    private ToggleButton mFemaleButton;
    private EditText mDay;
    private EditText mMonth;
    private EditText mYear;
    private Button mNext;
    private TextView mClear;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mRePassword;
    private Button mRegistration;
    private TextView mClear2;
    private TextView mErrorText1;
    private TextView mErrorText2;
    private RefreshOwner mRefreshOwner;
    private AuthUser mUser;
    @InjectPresenter
    RegistrationPresenter mPresenter;

    @ProvidePresenter
    RegistrationPresenter providePresenter() {
        return new RegistrationPresenter();
    }

    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainImage = view.findViewById(R.id.registrationImage);
        mFirstname = view.findViewById(R.id.firstname);
        mLastname = view.findViewById(R.id.lastname);
        mSecondName = view.findViewById(R.id.second_name);
        mEmail = view.findViewById(R.id.email);
        mMaleButton = view.findViewById(R.id.m_s);
        mFemaleButton = view.findViewById(R.id.f_s);
        mMaleButton.setOnClickListener(v -> {
            mFemaleButton.setChecked(false);
            mMaleButton.setChecked(true);
        });
        mFemaleButton.setOnClickListener(v -> {
            mFemaleButton.setChecked(true);
            mMaleButton.setChecked(false);
        });
        mDay = view.findViewById(R.id.birth_day);
        mMonth = view.findViewById(R.id.birth_month);
        mYear = view.findViewById(R.id.year_birth);
        mNext = view.findViewById(R.id.next_button);
        mClear = view.findViewById(R.id.cleanButton);
        mFirstView = view.findViewById(R.id.registration_1);
        mSecondView = view.findViewById(R.id.registration_2);
        mUsername = view.findViewById(R.id.login_reg);
        mPassword = view.findViewById(R.id.password_reg);
        mRePassword = view.findViewById(R.id.password_repeat);
        mRegistration = view.findViewById(R.id.registrationButton);
        mClear2 = view.findViewById(R.id.cleanButton2);
        mErrorText1 = view.findViewById(R.id.error_text1);
        mErrorText2 = view.findViewById(R.id.error_text2);
        mNext.setOnClickListener(v -> user1FragmentChange());
        mRegistration.setOnClickListener(v -> user2FragmentChange());
        mClear.setOnClickListener(v -> clear1());
        mClear2.setOnClickListener(v -> clear2());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 1.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mMainImage.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {

        }
    }

    private void user1FragmentChange() {
        String firstname = mFirstname.getText().toString();
        String secondname = mSecondName.getText().toString();
        String lastname = mLastname.getText().toString();
        String email = mEmail.getText().toString();
        String sex = mMaleButton.isChecked()? "М" : "Ж";
        int day =Integer.parseInt( mDay.getText().toString());
        String month = mMonth.getText().toString();
        int year = Integer.parseInt(mYear.getText().toString());
        if(firstname.length()==0 || secondname.length()==0 || lastname.length()==0 || day==0 || month.length()==0 || year==0) {
            mErrorText1.setText("Заполните все поля");
            mErrorText1.setVisibility(View.VISIBLE);
            return;
        }
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mErrorText1.setText("Ошибка: неверный email");
            mErrorText1.setVisibility(View.VISIBLE);
            return;
        }
        String date = year + "-" + month + "-" + day;
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.ENGLISH);
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MMMM-dd", new Locale("ru"));
            try {
                date1 = simpleDateFormat1.parse(date);
            } catch (ParseException parseException) {
                mErrorText1.setText("Неверно введена дата");
                mErrorText1.setVisibility(View.VISIBLE);
                return;
            }
        }
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        mUser = new AuthUser(firstname, lastname, secondname, email, simpleDateFormat1.format(date1), sex, false);
        mFirstView.setVisibility(View.GONE);
        mSecondView.setVisibility(View.VISIBLE);
    }


    private void user2FragmentChange() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String re_password = mRePassword.getText().toString();
        if(username.length()==0 || password.length()==0 || re_password.length()==0) {
            mErrorText2.setText("Заполните все поля");
            mErrorText2.setVisibility(View.VISIBLE);
            return;
        }
        if(!re_password.equals(password)) {
            mErrorText2.setText("Пароли не совпадают");
            mErrorText2.setVisibility(View.VISIBLE);
            return;
        }
        mUser.setUsername(username);
        mUser.setPassword(password);
        mUser.setRe_password(re_password);
        mPresenter.registration(mUser);
    }

    private void clear1() {
        mFirstname.setText("");
        mLastname.setText("");
        mSecondName.setText("");
        mEmail.setText("");
        mDay.setText("");
        mMonth.setText("");
        mYear.setText("");
    }

    private void clear2() {
        mUsername.setText("");
        mPassword.setText("");
        mRePassword.setText("");
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
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshData() {
        hideRefresh();
    }

    @Override
    public void showSuccess() {
        Fragment authFragment = AuthFragment.newInstance();
        ((MainActivity) getActivity()).changeFragment(authFragment);
    }

    @Override
    protected RegistrationPresenter getPresenter() {
        return mPresenter;
    }
}