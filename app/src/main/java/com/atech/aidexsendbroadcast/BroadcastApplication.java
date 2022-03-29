package com.atech.aidexsendbroadcast;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.atech.aidexsendbroadcast.data.AidexHistory;
import com.atech.aidexsendbroadcast.data.BgType;

import com.atech.aidexsendbroadcast.data.MessageType;
import com.atech.aidexsendbroadcast.data.SensorActionType;
import com.atech.aidexsendbroadcast.util.BroadcastData;
import com.microtechmd.blecomm.constant.cgm.History;
import com.microtechmd.blecomm.entity.BroadcastEntity;

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


    // this needs to be set from settings of app
    BgType bgType = BgType.MMOL_L;

    // needs to be set by App when new transmitter is accepted
    String transmitterSerial = "xxxxx";

    // this is generated when new sensor is started (only when physical sensor is changed)
    // newSensor(isNew=false) would still have same UUID (depending if sensorIndex is
    // changed when old sensor is restarted
    String sensorId = UUID.randomUUID().toString();

    // we need to set this when Sensor is Started (as new) or Restarted
    private boolean isSensorRestarted = false;


    AidexHistory lastBgAidexHistory;
    private int lastBroadcastHistoryEventIndex = 0;
    private String newSensorId;

    /**
     * THIS IS THE MAIN METHOD THAT WOULD HANDLE BROADCASTS RECEIVED FROM
     * TRANSMITTER
     *
     * This is simple logic that I am using in my test App, it should be easy to be
     * adapted to real GlucoRx_Aidex code
     */
    public synchronized void dataReceivedFromTransmitter(BroadcastEntity broadcastEntity) {

        AidexHistory currentAidexHistory = convertToAidexHistory(broadcastEntity);

        if (lastBroadcastHistoryEventIndex==currentAidexHistory.historyEventIndex) {
            return; // each broadcast is sent only once
        }

        if (currentAidexHistory.isValidGlucoseEntry()) {
            if (currentAidexHistory.glucose > 0) {
                BroadcastData.sendLocalBroadcastWithNewBgData(currentAidexHistory, lastBgAidexHistory, bgType);
                lastBgAidexHistory = currentAidexHistory;
            }
        }

        checkIfSpecialBroadcastNeeded(currentAidexHistory);

    }


    public AidexHistory convertToAidexHistory(BroadcastEntity broadcastEntity) {
        AidexHistory history = new AidexHistory();
        history.datetime = broadcastEntity.getDatetime()*1000L;
        history.battery = broadcastEntity.getBattery();
        history.state = broadcastEntity.getState();
        history.glucose = broadcastEntity.getGlucose();
        history.primaryClient = broadcastEntity.getPrimary();

        com.microtechmd.blecomm.entity.CgmHistoryEntity hst = (com.microtechmd.blecomm.entity.CgmHistoryEntity)broadcastEntity.getCgmHistory();

        history.historyDatetime = hst.getDatetime()*1000L;
        history.historySensorIndex = hst.getSensorIndex();
        history.historyEventIndex = hst.getEventIndex();
        history.historyEventType = hst.getEventType();
        history.historyEventValue = hst.getEventData();
        //history.historyRawValue = gson.toJson(hst.getRawData());

        history.sensorId = sensorId;
        history.tansmitterSn = transmitterSerial;

        return history;

    }





    private void checkIfSpecialBroadcastNeeded(AidexHistory data) {

        switch(data.historyEventType) {

            case History.HISTORY_GLUCOSE_RECOMMEND_CAL: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.CALIBRATION_REQUESTED,
                        null);
                return;
            }

            case History.HISTORY_CALIBRATION: {
                BroadcastData.sendLocalBroadcastWithCalibration(data.historyDatetime, data.historyEventValue, bgType);
                return;
            }

            case History.HISTORY_BATTERY_LOW: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.BATTERY_LOW,
                        null);
                return;
            }

            case History.HISTORY_BATTERY_EXHAUSTED: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.BATTERY_EMPTY,
                        null);
                return;
            }

            case History.HISTORY_SENSOR_ERROR: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.SENSOR_ERROR,
                        "" + data.historyEventValue);
                return;
            }


            case History.HISTORY_TRANSMITTER_ERROR: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.TRANSMITTER_ERROR,
                        "" + data.historyEventValue);
                return;
            }

            case History.HISTORY_GLUCOSE_INVALID: {
                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.GLUCOSE_INVALID,
                        "" + data.historyEventValue);
                return;
            }

            case History.HISTORY_SENSOR_NEW: {
                // we send sensor new only when sensor is really changed
                if (newSensorId==null) {
                    newSensorId = data.sensorId;

                    if (isSensorRestarted) {
                        BroadcastData.sendLocalBroadcastWithSensorAction(SensorActionType.RESTART_SENSOR,
                                data.historyDatetime, data.sensorId, data.tansmitterSn);
                        isSensorRestarted = false; // resetting variable
                    } else {
                        BroadcastData.sendLocalBroadcastWithSensorAction(SensorActionType.NEW_SENSOR,
                                data.historyDatetime, data.sensorId, data.tansmitterSn);
                    }

                    return;
                }

                if (!newSensorId.equals(data.sensorId)) {  // we send broadcast only on first report of new sensor
                    if (isSensorRestarted) {
                        BroadcastData.sendLocalBroadcastWithSensorAction(SensorActionType.RESTART_SENSOR,
                                data.historyDatetime, data.sensorId, data.tansmitterSn);
                        isSensorRestarted = false; // resetting variable
                    } else {
                        BroadcastData.sendLocalBroadcastWithSensorAction(SensorActionType.NEW_SENSOR,
                                data.historyDatetime, data.sensorId, data.tansmitterSn);
                    }
                    newSensorId = data.sensorId;
                }

                return;
            }


            case History.HISTORY_SENSOR_EXPIRATION: {
                BroadcastData.sendLocalBroadcastWithSensorAction(SensorActionType.STOP_SENSOR,
                        data.historyDatetime, data.sensorId, data.tansmitterSn);

                BroadcastData.sendLocalBroadcastWithMessage(
                        data.historyDatetime,
                        MessageType.SENSOR_EXPIRED,
                        "" + data.historyEventValue);

                return;
            }

            default:
                return;

        }
    }




    /**
     * When new data is received
     */
//    public void sendNewBroadcastData(long timestamp, float bgValue) {
//        BroadcastData.sendLocalBroadcastWithNewBgData(new BgReadingDto(timestamp, bgValue, sensorDto));
//    }

    public void sendNewSensor() {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                        sensorId,
                        transmitterSerial
        );
    }

    public void sendRestartSensor() {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                        sensorId,
                        transmitterSerial
                );
    }

    public void stopSensor() {
        BroadcastData.sendLocalBroadcastWithSensorAction(
                SensorActionType.NEW_SENSOR,
                        System.currentTimeMillis(),
                sensorId,
                transmitterSerial
                );
    }

    public void sendCalibration(float calibration, long timestamp) {
        BroadcastData.sendLocalBroadcastWithCalibration(timestamp,
                calibration,
                         bgType);
    }

    public void sendMessage(MessageType messageType, long timestamp, String messageText) {
        BroadcastData.sendLocalBroadcastWithMessage(timestamp,
                messageType,
                        messageText);
    }

}
