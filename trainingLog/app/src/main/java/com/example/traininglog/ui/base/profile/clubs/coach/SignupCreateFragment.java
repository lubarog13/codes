package com.example.traininglog.ui.base.profile.clubs.coach;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUpForCoachCreate;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupCreateFragment extends PresenterFragment implements SignupCreateView{

    private static final String ARG_PARAM1 = "param1";
    private MaterialSpinner mClubs;
    private EditText mUsername;
    private EditText mStartDay;
    private MaterialSpinner mStartMonth;
    private EditText mStartYear;
    private Button mSaveButton;
    private Button mClearButton;
    private View mErrorView;
    private TextView mErrorText;
    private EditText mEndDay;
    private MaterialSpinner mEndMonth;
    private EditText mEndYear;
    private RefreshOwner mRefreshOwner;
    private int mClubId;
    private Club club;
    private String startMonth = "января";
    private String endMonth = "января";
    private int defClub;
    @InjectPresenter
    SignupCreatePresenter mPresenter;
    @ProvidePresenter
    SignupCreatePresenter providePresenter(){return new SignupCreatePresenter();}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    public static SignupCreateFragment newInstance(int param1) {
        SignupCreateFragment fragment = new SignupCreateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClubId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStartDay = view.findViewById(R.id.start_day);
        mEndDay = view.findViewById(R.id.end_day);
        mStartMonth = view.findViewById(R.id.start_month);
        mStartYear = view.findViewById(R.id.start_year);
        mEndYear = view.findViewById(R.id.end_year);
        mEndMonth = view.findViewById(R.id.end_month);
        mSaveButton = view.findViewById(R.id.save_button);
        mClearButton = view.findViewById(R.id.cleanButton);
        mErrorView = view.findViewById(R.id.error_view);
        mErrorText = view.findViewById(R.id.error_text);
        mClubs = view.findViewById(R.id.group);
        mUsername = view.findViewById(R.id.username);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = getActivity().findViewById(R.id.create_title);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        textView.setTypeface(typeFace);
        List<String> months = new ArrayList<>();
        months.add("января");
        months.add("февраля");
        months.add("марта");
        months.add("апреля");
        months.add("мая");
        months.add("июня");
        months.add("июля");
        months.add("августа");
        months.add("сентября");
        months.add("октября");
        months.add("ноября");
        months.add("декабря");
        mEndMonth.setItems(months);
        mStartMonth.setItems(months);
        mStartMonth.setOnItemSelectedListener((view, position, id, item) -> startMonth= (String) item);
        mEndMonth.setOnItemSelectedListener((view, position, id, item) -> endMonth= (String) item);
        mSaveButton.setOnClickListener(v -> save());
        mClearButton.setOnClickListener(v -> clear());
        mPresenter.getClubs();
    }


    private void save() {
        Date startDate=getDate(mStartDay, startMonth, mStartYear);
        Date endDate = getDate(mEndDay, endMonth, mEndYear);
        if(startDate==null||endDate==null) return;
        if(startDate.compareTo(endDate)>=0){
            mErrorView.setVisibility(View.VISIBLE);
            mErrorText.setText("Неверно введены даты");
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if(mUsername.getText().length()==0) {
            mErrorText.setText("Заполните все поля");
            mErrorView.setVisibility(View.VISIBLE);
            return;
        }
        SignUpForCoachCreate signUpForCoachCreate = new SignUpForCoachCreate(club.getId(), 0, format.format(startDate), format.format(endDate));
        mPresenter.create(signUpForCoachCreate, mUsername.getText().toString());

    }

    private Date getDate(EditText e_day, String e_month, EditText e_year){
        int day = Integer.parseInt(e_day.getText().toString());
        int year = Integer.parseInt(e_year.getText().toString());
        String date = year + "-" + e_month + "-" + day;
        Date date1 = null;
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MMMM-dd", new Locale("ru"));
            try {
                date1 = simpleDateFormat1.parse(date);
                return date1;
            } catch (ParseException parseException) {
                mErrorText.setText("Неверно введена дата");
                mErrorView.setVisibility(View.VISIBLE);
                return null;
            }
    }

    private void clear() {
        mClubs.setSelectedIndex(defClub);
        mUsername.setText("");
        mStartDay.setText("");
        mStartMonth.setSelectedIndex(0);
        mStartYear.setText("");
        mEndDay.setText("");
        mEndMonth.setSelectedIndex(0);
        mEndYear.setText("");
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
        if (throwable!=null) Log.e("err", throwable.getMessage());
        hideRefresh();
        mErrorView.setVisibility(View.VISIBLE);
        mErrorText.setText("Ошибка подключения");
    }

    @Override
    public void showClubs(List<Club> clubs) {
        List<String> groups = new ArrayList<>();
        for(Club club: clubs){
            groups.add(club.getGroup());
        }
        this.club = clubs.stream().filter(club -> club.getId()==mClubId).collect(Collectors.toList()).get(0);
        defClub = clubs.indexOf(this.club);
        mClubs.setItems(groups);
        mClubs.setSelectedIndex(defClub);
        mClubs.setOnItemSelectedListener((view, position, id, item) -> club = clubs.get(position));
    }

    @Override
    public void showUserNotFound() {
        mUsername.setError("Пользователь не найден");
    }

    @Override
    public void setOk() {
        hideRefresh();
        mErrorView.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Запись успешно создана", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected SignupCreatePresenter getPresenter() {
        return mPresenter;
    }
}