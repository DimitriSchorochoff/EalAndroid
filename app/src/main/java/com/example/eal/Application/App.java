package com.example.eal.Application;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class App extends Application{

    private static Context mContext;
    private static AppCompatActivity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        App.mContext = getApplicationContext();
    }

    public static Context getContext(){ return mContext;}

    public static AppCompatActivity getActivity(){return mActivity;}

    public static void setActivity(AppCompatActivity activity){mActivity = activity;}
}