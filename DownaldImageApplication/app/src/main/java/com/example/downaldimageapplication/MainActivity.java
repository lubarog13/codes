package com.example.downaldimageapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText mEditText;
    ImageView mImage;
    Button mButton1;
    Button mButton2;
    private String url;
    private String image_name;
    ArrayList<Long> list = new ArrayList<>();
    private static final int LOADER_ID = 10000;
    private LoaderManager loaderManager;
    public static final int WRITE_PERMISSION_RC = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text);
        mImage = findViewById(R.id.image);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        this.loaderManager = LoaderManager.getInstance(this);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText() == null) {
                    Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_SHORT).show();
                } else if((!mEditText.getText().toString().contains("/")) || (!mEditText.getText().toString().contains("."))) {
                    Toast.makeText(MainActivity.this, "Введен текст, а не ссылка", Toast.LENGTH_SHORT).show();
                }
                else {
                    String purl = mEditText.getText().toString();
                    String[] strings = purl.split("/");
                    try {
                        String ext = strings[strings.length - 1].split("\\.")[1];
                        Log.d("1", ext);
                        if (ext.equals("jpeg") || ext.equals("png") || ext.equals("bmp")) {
                            url = purl;
                            downloadWithPermissions();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Неправильная ссылка", Toast.LENGTH_LONG).show();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Toast.makeText(MainActivity.this, "Введен текст, а не ссылка", Toast.LENGTH_SHORT).show();
                    }
                }
                mButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadImage();
                    }
                });
            }
        });
    }
    private void loadImage() {
        File imgFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, "/GadgetSaint/"+image_name+".png");
        // File imgFile = new  File("/storage/emulated/0/Download/GadgetSaint/Sample.png");
        Log.d("1", imgFile.getAbsolutePath());
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mButton2.setEnabled(false);
            mImage.setImageBitmap(myBitmap);

        } else {
            Toast.makeText(this, "Неправильная ссылка", Toast.LENGTH_SHORT).show();
        }

    }
    private void downloadImage() {
            LoaderManager.LoaderCallbacks<String> loaderCallbacks = this;
            Bundle args = new Bundle();
            Loader<String> loader = this.loaderManager.initLoader(LOADER_ID, args, loaderCallbacks);
            loader.forceLoad();
    }

    private  boolean isWritePermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private  boolean isReadPermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestWritePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setMessage("Без разрешения нельзя скачать файл")
                    .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_RC);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_RC);
        }
    }
    private void downloadWithPermissions(){
        if(isWritePermissionsGranted()){
            Log.d("1", "yes, write");
            downloadImage();
        } else {
            Log.d("1", "not write");
            requestWritePermission();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode!=WRITE_PERMISSION_RC) return;
        if(grantResults.length!=1) return;
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            downloadWithPermissions();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Вы можете дать разрешение в настройках устройства")
                    .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_RC);
                        }
                    }).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if(id==LOADER_ID){
            Log.i("info", "onLoaderCreate");
            return new ImageLoader(MainActivity.this, url);
        }
        throw new RuntimeException("TODO..");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data!=null){
            mButton2.setEnabled(true);
            image_name = data;
        } else {
            Toast.makeText(this, "Error loading", Toast.LENGTH_SHORT).show();
        }
        this.loaderManager.destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
