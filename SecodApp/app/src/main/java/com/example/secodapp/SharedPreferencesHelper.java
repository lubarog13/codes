package com.example.secodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SharedPreferencesHelper {
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String USERS_KEY = "USERS_KEY";
    public static final Type USERS_TYPE = new TypeToken<String>() {
    }.getType();


    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getBrowser() {
        String browser = mGson.fromJson(mSharedPreferences.getString(USERS_KEY, ""), USERS_TYPE);
        if (browser==null) {
            addBrowser("Google");
        }
        return browser == null ? "Google" : browser;
    }

    @SuppressLint("CommitPrefEdits")
    public void addBrowser(String browser) {
        mSharedPreferences.edit().clear();
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(browser, USERS_TYPE)).apply();
    }


    public int login() {
        String browser = getBrowser();
        switch (browser){
            case "Google":
                return R.id.google;
            case "Yandex":
                return R.id.yandex;
            default:
                return R.id.bing;
        }
    }
    public String search(String str){
        str = str.replace(' ', '+');
        String browser = getBrowser();
        switch (browser){
            case "Google":
                return "https://www.google.com/search?q="+str+"&rlz=1C1GIVA_enRU947RU947&oq=rg&aqs=chrome..69i57j0l4j0i1i10j0j0i1i10j46i199i291j0.1759j0j7&sourceid=chrome&ie=UTF-8";
            case "Yandex":
                return "https://yandex.ru/search/?lr=2&text="+str;
            default:
                return "https://www.bing.com/search?q="+str+"&form=QBLH&sp=-1&pq=grd&sc=8-3&qs=n&sk=&cvid=07DC76AE63EE4510802A88DB8EE47099";
        }
    }

}
