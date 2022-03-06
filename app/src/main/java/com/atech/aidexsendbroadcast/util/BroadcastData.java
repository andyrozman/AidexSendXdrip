package com.atech.aidexsendbroadcast.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.atech.aidexsendbroadcast.data.BgReadingDto;
import com.atech.aidexsendbroadcast.data.CalibrationDto;
import com.atech.aidexsendbroadcast.data.MessageDto;
import com.atech.aidexsendbroadcast.data.SensorActionDto;

// created by jamorham
// modified by Andy 4/3/2022

public class BroadcastData {

    private static final String TAG = "BroadcastData";
    private static long lastTimestamp = 0;
    public static final long MINUTE_IN_MS = 60_000;


    public static void sendLocalBroadcastWithSensorAction(final SensorActionDto sensorActionDto) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (sensorActionDto != null) {
                bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, sensorActionDto.timestamp); // epoch in ms (System.currentTimeMillis())
                bundle.putString(AidexBroadcastIntents.AIDEX_SENSOR_ID, sensorActionDto.sensorId);
                bundle.putString(AidexBroadcastIntents.AIDEX_TRANSMITTER_SN, sensorActionDto.transmitter.serialNumber);

                String action = null;

                switch(sensorActionDto.action) {

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
    }


    public static void sendLocalBroadcastWithCalibration(final CalibrationDto calibrationDto) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (calibrationDto != null) {
                bundle.putString(AidexBroadcastIntents.AIDEX_BG_TYPE, "mmol/l"); // mmol/l or mg/dl
                bundle.putDouble(AidexBroadcastIntents.AIDEX_BG_VALUE, calibrationDto.glucose);
                bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, calibrationDto.timestamp); // epoch in ms (System.currentTimeMillis())

                final Intent intent = new Intent(AidexBroadcastIntents.ACTION_CALIBRATION);
                SendAidexBroadcast.send(intent, bundle);
            }
        }
    }


    public static void sendLocalBroadcastWithMessage(final MessageDto message) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (message != null) {
                bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, message.timestamp); // epoch in ms (System.currentTimeMillis())
                bundle.putString(AidexBroadcastIntents.AIDEX_MESSAGE_TYPE, message.messageType.name()); // mmol/l or mg/dl
                bundle.putString(AidexBroadcastIntents.AIDEX_MESSAGE_VALUE, message.messageValue); // mmol/l or mg/dl

                final Intent intent = new Intent(AidexBroadcastIntents.ACTION_NOTIFICATION);
                SendAidexBroadcast.send(intent, bundle);
            }
        }
    }


    public static void sendLocalBroadcastWithNewBgData(final BgReadingDto bgReading) {
        if (SendAidexBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (bgReading != null) {

                if (Math.abs(bgReading.timestamp - lastTimestamp) < MINUTE_IN_MS) {
                    String msg = String.format("Refusing to broadcast a reading with close timestamp to last broadcast:  %s (%d) vs %s (%d) ", dateTimeText(lastTimestamp), lastTimestamp, dateTimeText(bgReading.timestamp), bgReading.timestamp);
                    if (bgReading.timestamp == lastTimestamp) {
                        Log.d(TAG, "Timestamp the same as previous one. " + msg);
                    } else {
                        Log.e(TAG, msg);
                    }
                    return;
                }

                // TODO you can add your own checks here

                //val sensor = Sensor.currentSensor();
                if (bgReading.sensor == null) {
                    Log.e(TAG, "Refusing to broadcast a reading as no sensor is active");
                    return;
                }

                if (bgReading.timestamp <= bgReading.sensor.timeStarted) {
                    Log.e(TAG, "Refusing to broadcast a reading before sensor start:  " + dateTimeText(bgReading.sensor.timeStarted) + " vs " + dateTimeText(bgReading.timestamp));
                    return;
                }

                lastTimestamp = bgReading.timestamp;

                bundle.putString(AidexBroadcastIntents.AIDEX_BG_TYPE, "mmol/l"); // mmol/l or mg/dl
                bundle.putDouble(AidexBroadcastIntents.AIDEX_BG_VALUE, bgReading.glucose);
                bundle.putString(AidexBroadcastIntents.AIDEX_BG_SLOPE_NAME, getTrendSlope(bgReading));
                bundle.putLong(AidexBroadcastIntents.AIDEX_TIMESTAMP, bgReading.timestamp); // epoch in ms (System.currentTimeMillis())
                bundle.putString(AidexBroadcastIntents.AIDEX_SENSOR_ID, bgReading.sensor.sensorId);
                bundle.putString(AidexBroadcastIntents.AIDEX_TRANSMITTER_SN, bgReading.sensor.transmitter.serialNumber);

                final Intent intent = new Intent(AidexBroadcastIntents.ACTION_NEW_BG_ESTIMATE);
                SendAidexBroadcast.send(intent, bundle);
            } 
        }
    }

    public static String getTrendSlope(BgReadingDto reading) {

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
