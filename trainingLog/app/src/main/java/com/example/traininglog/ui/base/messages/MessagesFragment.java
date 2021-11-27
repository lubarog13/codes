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
import com.example.traininglog.ui.base.messages.incoming.IncomingMessagesFragment;
import com.example.traininglog.ui.base.messages.outcoming.OutcomingMessagesFragment;

import java.io.IOException;
import java.io.InputStream;

public class MessagesFragment extends Fragment {

    private Button mInButton;
    private Button mOutButton;

    public MessagesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        changeFragment(IncomingMessagesFragment.newInstance());
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
            changeFragment(IncomingMessagesFragment.newInstance());
            mInButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
            mOutButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDivider));
        });
        mOutButton.setOnClickListener(v -> {
            changeFragment(OutcomingMessagesFragment.newInstance());
            mOutButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
            mInButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDivider));
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction()
                .replace(R.id.child_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
}