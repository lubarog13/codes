package com.example.traininglog.ui.base.messages;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.traininglog.R;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.ui.base.messages.create.CreateMessageFragment;
import com.example.traininglog.ui.base.messages.create.CreateMessageFragment$$PresentersBinder;
import com.example.traininglog.ui.base.messages.incoming.IncomingMessagesFragment;
import com.example.traininglog.ui.base.messages.outcoming.OutcomingMessagesFragment;
import com.example.traininglog.ui.base.messages.update.UpdateMessageFragment;

import java.io.IOException;
import java.io.InputStream;

public class MessagesFragment extends Fragment implements Refreshable {
    private static final  String ARG_PARAM1 = "param1";
    private String mClubName;
    private Button mInButton;
    private Button mOutButton;
    private Fragment mChildFragment;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }
    public static MessagesFragment newInstance(String club_name) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, club_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            mClubName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInButton = view.findViewById(R.id.enter_messages);
        mOutButton = view.findViewById(R.id.out_messages);
        if(mClubName!=null) {
            changeFragment(CreateMessageFragment.newInstance(true, mClubName));
        }
        else changeFragment(IncomingMessagesFragment.newInstance());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = getActivity().findViewById(R.id.messages_image);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 23.png");
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
        mInButton.setOnClickListener(v -> {
            changeFragment(IncomingMessagesFragment.newInstance() );
            changeColors(true);
        });
        mOutButton.setOnClickListener(v -> {
            changeFragment(OutcomingMessagesFragment.newInstance());
            changeColors(false);
        });
    }

    public void changeFragment(Fragment fragment) {
        mChildFragment = fragment;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction()
                .replace(R.id.child_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
    public void changeColors(boolean is_in) {
        if (is_in) {
            mInButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
            mOutButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDivider));
        } else {
            mOutButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
            mInButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDivider));
        }
    }

    @Override
    public void onRefreshData() {
        if(mChildFragment!=null && mChildFragment instanceof Refreshable) {
            ((Refreshable) mChildFragment).onRefreshData();
        }
    }
}