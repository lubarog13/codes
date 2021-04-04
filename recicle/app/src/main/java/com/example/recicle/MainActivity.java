package com.example.recicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

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
        AndroidVersionsAdapter.onAndroidClickListener androidClickListener = new AndroidVersionsAdapter.onAndroidClickListener() {
            @Override
            public void onAndroidClick(AndroidVersions androidVersions, int position) {
                Intent intent = new Intent(MainActivity.this, AndroidVersion.class);
                intent.putExtra("name", androidVersions.getName());
                intent.putExtra("image", androidVersions.getImage());
                startActivity(intent);
            }
        };
        RecyclerView.Adapter adapter = new AndroidVersionsAdapter(generateAndroid(), androidClickListener);
        recyclerView.setAdapter(adapter);
    }
    private static List<AndroidVersions> generateAndroid(){
        List<AndroidVersions> androidVersions = new ArrayList<>();
        androidVersions.add(new AndroidVersions("Android 1.5 Cupcake", "https://i.pinimg.com/originals/92/ae/f1/92aef1b15de3c2ec95cc3bae440c91f7.jpg"));
        androidVersions.add(new AndroidVersions("Android 1.6 Donut", "https://i.pinimg.com/564x/8f/42/cf/8f42cf757116fd7b4511f7a1fa0cb675.jpg"));
        androidVersions.add(new AndroidVersions("Android 2.0/2.1 Eclair", "https://i.pinimg.com/564x/e2/0b/1f/e20b1f92718824e3817f9bf183688c31.jpg"));
        androidVersions.add(new AndroidVersions("Android 2.2 Froyo", "https://lh3.googleusercontent.com/proxy/efHvXQ4zh6kFqxqJSDwlLySoaer5R0cmN2Lyd82Aht1aUaO4tMU97NqrHWZOU98A4iE2vJuSi_p87sQOb2QZtQHBkGULAJFxcoji4Cae18lr7UCgKmtQEI62tV7majrA-_S0pCVSNBt_pg9y"));
        androidVersions.add(new AndroidVersions("Android 2.3 ginger bread", "https://i.pinimg.com/564x/57/75/a4/5775a4a93d3bac77f6a6841b64009bce.jpg"));
        androidVersions.add(new AndroidVersions("Android 3.0 Honeycomb", "https://i.pinimg.com/564x/1f/c3/2c/1fc32c434c350b20338f0087b8ad6475.jpg"));
                androidVersions.add(new AndroidVersions("Android 4.0\n Ice Cream Sandwich", "https://3dnews.ru/assets/external/illustrations/2011/10/19/618587/ics-android.png"));
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