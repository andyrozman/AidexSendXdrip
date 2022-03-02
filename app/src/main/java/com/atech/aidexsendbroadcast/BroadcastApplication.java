package com.atech.aidexsendbroadcast;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.atech.aidexsendbroadcast.data.BgReadingDto;
import com.atech.aidexsendbroadcast.data.SensorDto;
import com.eveningoutpost.dexdrip.UtilityModels.BroadcastGlucose;

public class BroadcastApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        BroadcastApplication.context = getApplicationContext();
        super.onCreate();
    }

    public static Context getAppContext() {
        return BroadcastApplication.context;
    }


    public void sendBroadcastData() {
        SensorDto sensorDto = new SensorDto();
        BgReadingDto reading = new BgReadingDto(System.currentTimeMillis(), 5.7f);

        BroadcastGlucose.sendLocalBroadcast(reading, sensorDto);
    }


}
