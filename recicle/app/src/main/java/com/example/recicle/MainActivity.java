package com.example.recicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new AndroidVersionsAdapter(generateAndroid());
        recyclerView.setAdapter(adapter);
    }
    private static List<AndroidVersions> generateAndroid(){
        List<AndroidVersions> androidVersions = new ArrayList<>();
        androidVersions.add(new AndroidVersions("Android 1.5 Cupcake", "https://i.pinimg.com/originals/92/ae/f1/92aef1b15de3c2ec95cc3bae440c91f7.jpg"));
        androidVersions.add(new AndroidVersions("Android 1.6 Donut", "http://devlup.com/wp-content/uploads/2010/05/android_donut-gxz.png"));
        androidVersions.add(new AndroidVersions("Android 2.0/2.1 Eclair", "http://devlup.com/wp-content/uploads/2010/05/android-ecliar.jpg"));
        androidVersions.add(new AndroidVersions("Android 2.2 Froyo", "http://devlup.com/wp-content/uploads/2010/05/Google-Android-2.2-Froyo.jpg"));
        androidVersions.add(new AndroidVersions("Android 2.3 ginger bread", "http://devlup.com/wp-content/uploads/2010/05/android-gingerbread.png"));
        androidVersions.add(new AndroidVersions("Android 3.0 Honeycomb", "http://devlup.com/wp-content/uploads/2010/05/android-honeycomb.jpg"));
        androidVersions.add(new AndroidVersions("Android 4.0 Ice Cream Sandwich", "http://devlup.com/wp-content/uploads/2011/05/android-icecream-sandwich.jpg"));
        androidVersions.add(new AndroidVersions("Android 4.1 Jelly Bean", "https://i.pinimg.com/564x/cf/3a/aa/cf3aaa861e38899277d198bb97e99619.jpg"));
        androidVersions.add(new AndroidVersions("Android 4.4 KitKat", "https://i.pinimg.com/564x/a2/1e/b9/a21eb9b8a277571a8c035ed590afc5af.jpg"));
        androidVersions.add(new AndroidVersions("Android 5.0 Lollipop", "https://suvitruf.ru/wp-content/uploads/2014/10/android-lollipop.png"));
        androidVersions.add(new AndroidVersions("Android 6.0 Marshmallow", "https://i.pinimg.com/564x/aa/54/76/aa5476df92de946419ddbd72a3970f85.jpg"));
        androidVersions.add(new AndroidVersions("Android 7.0 Nougat", "https://i.pinimg.com/564x/fa/55/68/fa5568f06f33984da3bfc2a755e24ce1.jpg"));
        androidVersions.add(new AndroidVersions("Android 8.0 Oreo", "https://i.pinimg.com/564x/b8/42/57/b84257cf6db9ffe3f40661a2a2a9a6b6.jpg"));
        androidVersions.add(new AndroidVersions("Android 9.0 Pie", "https://i.pinimg.com/564x/7b/df/a2/7bdfa22b717cd6033621d282e0e83d16.jpg"));
        androidVersions.add(new AndroidVersions("Android 10", "https://i.pinimg.com/564x/fe/95/6b/fe956b441971225a99cc5aecbd71444c.jpg"));
        androidVersions.add(new AndroidVersions("Android 11", "https://i.pinimg.com/564x/c9/0f/47/c90f479eed92f188f1e2e6ace6631d29.jpg"));
        return  androidVersions;
    }
}