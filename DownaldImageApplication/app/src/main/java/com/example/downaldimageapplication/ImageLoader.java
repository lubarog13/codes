package com.example.downaldimageapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.nio.charset.Charset;
import java.util.Random;

public class ImageLoader extends AsyncTaskLoader<String> {
    private String url;
    public ImageLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            byte[] array = new byte[15]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("US-ASCII"));
            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri Download_Uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle("GadgetSaint Downloading " + "Sample" + ".png");
            request.setDescription("Downloading " + "Sample" + ".png");
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/GadgetSaint/" + "/" + generatedString + ".png");
            long refid = downloadManager.enqueue(request);
            Thread.sleep(200);
            return generatedString;
        } catch (Exception e){
            Log.e("error", e.toString());
            return null;
        }
    }

}
