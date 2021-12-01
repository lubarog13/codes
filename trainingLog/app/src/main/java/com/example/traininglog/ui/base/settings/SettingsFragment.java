package com.example.traininglog.ui.base.settings;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends PresenterFragment implements Refreshable, SettingsView {
    private EditText mName;
    private EditText mLastName;
    private EditText mSecondName;
    private EditText mEmail;
    private EditText mUsername;
    private EditText mDay;
    private EditText mMonth;
    private EditText mYear;
    private ToggleButton mMale;
    private RefreshOwner mRefreshOwner;
    private SharedPreferences preferences;
    private Storage mStorage;
    private ToggleButton mFemale;
    private Button mSave;
    private SwitchCompat mShowAnalysis;
    private SwitchCompat mSaveData;
    @InjectPresenter
    SettingsPresenter mPresenter;
    @ProvidePresenter
    SettingsPresenter providePresenter() {return new SettingsPresenter();}

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) mRefreshOwner = (RefreshOwner) context;
        if (context instanceof Storage.StorageOwner) mStorage = ((Storage.StorageOwner) context).obtainStorage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName = view.findViewById(R.id.user_f_name);
        mLastName = view.findViewById(R.id.user_l_name);
        mSecondName = view.findViewById(R.id.user_s_name);
        mEmail = view.findViewById(R.id.email);
        mMale = view.findViewById(R.id.m_s);
        mFemale = view.findViewById(R.id.f_s);
        mMale.setOnClickListener(v -> {
            mFemale.setChecked(false);
            mMale.setChecked(true);
        });
        mFemale.setOnClickListener(v -> {
            mFemale.setChecked(true);
            mMale.setChecked(false);
        });
        mDay = view.findViewById(R.id.birth_day);
        mMonth = view.findViewById(R.id.birth_month);
        mYear = view.findViewById(R.id.year_birth);
        mUsername = view.findViewById(R.id.username);
        mSave = view.findViewById(R.id.save_button);
        mShowAnalysis = view.findViewById(R.id.showAnalysis);
        mSaveData = view.findViewById(R.id.save_data);
        mSave.setOnClickListener(v -> saveUser());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()==null) return;
        ImageView imageView = getActivity().findViewById(R.id.settings_image);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        mSaveData.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("save_data", !isChecked);
            editor.apply();
            Log.e("storage","storage");
            if(isChecked)
            mPresenter.clearTables(mStorage);
        }));
        mShowAnalysis.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("show_analysis", !isChecked);
            editor.apply();
        }));
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 24.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
            return;
        }
        onRefreshData();
    }


    private void initUser() {
        mName.setText(preferences.getString("first_name", ""));
        mLastName.setText(preferences.getString("last_name", ""));
        mSecondName.setText(preferences.getString("second_name", ""));
        mEmail.setText(preferences.getString("email", ""));
        mUsername.setText(preferences.getString("username", ""));
        String sex = preferences.getString("sex", "");
        if(sex.equals("М")){
            mMale.setChecked(true);
            mFemale.setChecked(false);
        } else {
            mMale.setChecked(false);
            mFemale.setChecked(true);
        }
        String date_b = preferences.getString("date_birth", new Date().toString());
        Date date = new Date(date_b);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", new Locale("ru"));
        mMonth.setText(simpleDateFormat.format(date));
        mYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        mSaveData.setChecked(!preferences.getBoolean("save_data", true));
        mShowAnalysis.setChecked(!preferences.getBoolean("show_analysis", true));
    }

    private void saveUser() {
        if(mName.getText().length()==0) {
            mName.setError("Имя должно быть введено");
            return;
        }
        if (mLastName.getText().length()==0) {
            mLastName.setError("Фамилия должна быть введена");
            return;
        }
        if(mEmail.getText().length()==0){
            mEmail.setError("email должен быть введен");
            return;
        }
        if(mUsername.getText().length()==0){
            mUsername.setError("Логин должен быть введен");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            mEmail.setError("Неверный email");
            return;
        }
        String date = mYear.getText().toString() + "-" + mMonth.getText().toString() + "-" + mDay.getText().toString();
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.ENGLISH);
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MMMM-dd", new Locale("ru"));
            try {
                date1 = simpleDateFormat1.parse(date);
            } catch (ParseException parseException) {
                mYear.setError("Неверно введена дата");
                return;
            }
        }
        String sex = mMale.isChecked()? "М":"Ж";
        String second_name = mSecondName.getText()!=null? mSecondName.getText().toString():null;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        AuthUser authUser = new AuthUser(mName.getText().toString(), mLastName.getText().toString(),
                second_name, mEmail.getText().toString(), simpleDateFormat1.format(date1), sex, preferences.getBoolean("is_coach", false));
        mPresenter.updateUser(authUser);
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
        if(throwable.getMessage()!=null) {
            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefreshData() {
        initUser();
        hideRefresh();
    }

    @Override
    public void showUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        if(user.getFirst_name()!=null)
        editor.putString("first_name", user.getFirst_name());
        if (user.getLast_name()!=null)
        editor.putString("last_name", user.getLast_name());
        if(user.getSecond_name()!=null)
        editor.putString("second_name", user.getSecond_name());
        if(user.getEmail()!=null)
        editor.putString("email", user.getEmail());
        if(user.getSex()!=null)
        editor.putString("sex", user.getSex());
        if(user.getDate_birth()!=null)
        editor.putString("date_birth", user.getDate_birth().toString());
        if(user.getUsername()!=null)
        editor.putString("username", user.getUsername());
        editor.apply();
        onRefreshData();
    }

    @Override
    protected SettingsPresenter getPresenter() {
        return mPresenter;
    }
}