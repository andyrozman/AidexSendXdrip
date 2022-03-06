package com.atech.aidexsendbroadcast;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.atech.aidexsendbroadcast.data.BgReadingDto;
import com.atech.aidexsendbroadcast.data.CalibrationDto;
import com.atech.aidexsendbroadcast.data.MessageDto;
import com.atech.aidexsendbroadcast.data.MessageType;
import com.atech.aidexsendbroadcast.data.SensorActionDto;
import com.atech.aidexsendbroadcast.data.SensorActionType;
import com.atech.aidexsendbroadcast.data.SensorDto;
import com.atech.aidexsendbroadcast.data.TransmitterDto;
import com.atech.aidexsendbroadcast.util.BroadcastData;

import java.util.UUID;

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

    TransmitterDto transmitterDto= new TransmitterDto();
    private SensorDto sensorDto = new SensorDto(UUID.randomUUID().toString(), System.currentTimeMillis(), transmitterDto);

    /**
     * When new data is received
     */
    public void sendNewBroadcastData(long timestamp, float bgValue) {
        BroadcastData.sendLocalBroadcastWithNewBgData(new BgReadingDto(timestamp, bgValue, sensorDto));
    }

    public void sendNewSensor(SensorDto dto) {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                new SensorActionDto(SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                        dto.sensorId,
                        dto.transmitter
        ));
    }

    public void sendRestartSensor(SensorDto dto) {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                new SensorActionDto(SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                        dto.sensorId,
                        dto.transmitter
                ));
    }

    public void stopSensor(SensorDto dto) {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                new SensorActionDto(SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                        dto.sensorId,
                        dto.transmitter
                ));
    }

    public void sendCalibration(float calibration, long timestamp) {
        BroadcastData.sendLocalBroadcastWithCalibration(
                new CalibrationDto(calibration,
                        timestamp));
    }

    public void sendMessage(MessageType messageType, long timestamp, String messageText) {
        BroadcastData.sendLocalBroadcastWithMessage(
                new MessageDto(messageType,
                        timestamp,
                        messageText));
    }

}
