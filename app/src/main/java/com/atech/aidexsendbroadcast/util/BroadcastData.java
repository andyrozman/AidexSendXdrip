package com.atech.aidexsendbroadcast.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.atech.aidexsendbroadcast.data.AidexHistory;
import com.atech.aidexsendbroadcast.data.BgReadingDto;
import com.atech.aidexsendbroadcast.data.BgType;


import com.atech.aidexsendbroadcast.data.MessageType;
import com.atech.aidexsendbroadcast.data.SensorActionType;
import com.microtechmd.blecomm.entity.BroadcastEntity;

// created by jamorham
// modified by Andy 4/3/2022

public class BroadcastData {

    private static final String TAG = "BroadcastData";
    private static long lastTimestamp = 0;
    public static final long MINUTE_IN_MS = 60_000;


    public static void sendLocalBroadcastWithSensorAction(final SensorActionType sensorAction,
                                                          final long timestamp,
                                                          final String sensorId,
                                                          final String transmitterSerial) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();

            bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, timestamp); // epoch in ms (System.currentTimeMillis())
            bundle.putString(AidexBroadcastIntents.AIDEX_SENSOR_ID, sensorId);
            bundle.putString(AidexBroadcastIntents.AIDEX_TRANSMITTER_SN, transmitterSerial);

            String action = null;

            switch(sensorAction) {

                case NEW_SENSOR:
                    action = AidexBroadcastIntents.ACTION_SENSOR_NEW;
                    break;
                case RESTART_SENSOR:
                    action = AidexBroadcastIntents.ACTION_SENSOR_RESTART;
                    break;
                case STOP_SENSOR:
                    action = AidexBroadcastIntents.ACTION_SENSOR_STOP;
                    break;
            }

            final Intent intent = new Intent(action);
            SendAidexBroadcast.send(intent, bundle);
        }
    }


    public static void sendLocalBroadcastWithCalibration(final long timestamp, final float glucose, final BgType bgType) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();

            bundle.putString(AidexBroadcastIntents.AIDEX_BG_TYPE, bgType.getUnit()); // mmol/l or mg/dl
            bundle.putDouble(AidexBroadcastIntents.AIDEX_BG_VALUE, glucose);
            bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, timestamp); // epoch in ms (System.currentTimeMillis())

            final Intent intent = new Intent(AidexBroadcastIntents.ACTION_CALIBRATION);
            SendAidexBroadcast.send(intent, bundle);
        }
    }


    public static void sendLocalBroadcastWithMessage(final long timestamp, MessageType messageType, String messageValue) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();

            bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, timestamp); // epoch in ms (System.currentTimeMillis())
            bundle.putString(AidexBroadcastIntents.AIDEX_MESSAGE_TYPE, messageType.name()); // message Type
            bundle.putString(AidexBroadcastIntents.AIDEX_MESSAGE_VALUE, messageValue); // value, can be null

            final Intent intent = new Intent(AidexBroadcastIntents.ACTION_NOTIFICATION);
            SendAidexBroadcast.send(intent, bundle);
        }
    }


    public static void sendLocalBroadcastWithNewBgData(final AidexHistory bgReading, AidexHistory previous, BgType bgType) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (bgReading != null) {

                if (Math.abs(bgReading.datetime - lastTimestamp) < MINUTE_IN_MS) {
                    String msg = String.format("Refusing to broadcast a reading with close timestamp to last broadcast:  %s (%d) vs %s (%d) ", dateTimeText(lastTimestamp), lastTimestamp, dateTimeText(bgReading.datetime), bgReading.datetime);
                    if (bgReading.datetime == lastTimestamp) {
                        Log.d(TAG, "Timestamp the same as previous one. " + msg);
                    } else {
                        Log.e(TAG, msg);
                    }
                    return;
                }

                lastTimestamp = bgReading.datetime;

                bundle.putString(AidexBroadcastIntents.AIDEX_BG_TYPE, bgType.getUnit()); // mmol/l or mg/dl
                bundle.putDouble(AidexBroadcastIntents.AIDEX_BG_VALUE, bgReading.glucose);
                bundle.putString(AidexBroadcastIntents.AIDEX_BG_SLOPE_NAME, getTrendSlope(bgReading, previous));
                bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, bgReading.datetime); // epoch in ms (System.currentTimeMillis())
                bundle.putString(AidexBroadcastIntents.AIDEX_SENSOR_ID, bgReading.sensorId);
                bundle.putString(AidexBroadcastIntents.AIDEX_TRANSMITTER_SN, bgReading.tansmitterSn);

                final Intent intent = new Intent(AidexBroadcastIntents.ACTION_NEW_BG_ESTIMATE);
                SendAidexBroadcast.send(intent, bundle);
            }
        }
    }


    public static String getTrendSlope(AidexHistory current, AidexHistory previous) {

        // TODO slope needs to be calculated and correct String returned

        String[] possibleValues = {
                "DoubleUp", "SingleUp", "FortyFiveUp",
                "Flat",
                "FortyFiveDown", "SingleDown", "DoubleDown",
                "NotComputable", "RateOutOfRange"};

        return "Flat";

    }

    public static String dateTimeText(long timestamp) {
        return android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", timestamp).toString();
    }


}
