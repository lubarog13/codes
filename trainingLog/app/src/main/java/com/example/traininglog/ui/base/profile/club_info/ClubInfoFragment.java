package com.example.traininglog.ui.base.profile.club_info;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.traininglog.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubInfoFragment extends Fragment {
    private TextView mText;
    private TextView mHeader;
    private ImageView mImage;

    public ClubInfoFragment() {
        // Required empty public constructor
    }
    public static ClubInfoFragment newInstance() {
        return new ClubInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.club_info_text);
        mHeader = view.findViewById(R.id.main_club_name);
        mImage = view.findViewById(R.id.club_image);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BalsamiqSans-Bold.ttf");
        mHeader.setTypeface(typeFace);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("image 22.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImage.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
            return;
        }
        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        mText.setTypeface(typeface1);
        mText.setText("В настоящее время СОК «Ижорец» занимает озеленённую территорию в Колппинском районе площадью 22 гектара, включая:\n" +
                "\n" +
                "1. здание водно-спортивного корпуса с 3 чашами:\n" +
                "- большая чаша с дорожками длиной 50 м;\n" +
                "- средняя чаша с дорожками длиной 12,5 м; \n" +
                "- малая чаша «Малышок»;\n" +
                "2. крытый манеж с беговыми дорожками по 110 м;\n" +
                "3. три футбольных поля;\n" +
                "4. городошный корт с 4 площадками;\n" +
                "5. спортивный корпус с залами для борьбы, художественной гимнастики, игровых видов спорта. \n" +
                " \n" +
                "\n" +
                "В состав СОК «Ижорец» также входит картодром  (г. Колпино, Саперный пер., 13, лит.А) , спортивный корпус «Металлострой» (пос. Металлострой, ул. Пушкинская, д.3), стадион «Парус» (пос. Саперный, Лагерное ш., д. 11), Стадион «Искра» (пос. Металлострой, ул. Садовая, д.4).\n" +
                "\n" +
                " \n" +
                "Основной базой для проведения занятий и спортивных мероприятий являются спортивные сооружения СПб ГБУ СОК «Ижорец», а также спортивные сооружения Колпинского района - это дворовые площадки и пришкольные стадионы.\n" +
                "\n" +
                " \n" +
                "СОК «Ижорец» предоставляет возможность для занятий физической культурой и спортом населению всех возрастов Колпинского и близлежащих районов.");
    }
}