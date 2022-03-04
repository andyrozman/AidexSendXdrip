package com.atech.aidexsendbroadcast.util;

/**
 * For integration.
 */
public interface AidexBroadcastIntents {
    String RECEIVER_PERMISSION = "com.microtechmd.cgms.aidex.permissions.RECEIVE_BG_ESTIMATE";

    String AIDEX_NEW_BG_ESTIMATE = "com.microtechmd.cgms.aidex.BgEstimate";

    // DON'T CHANGE THIS
    String AIDEX_BG_TYPE = "com.microtechmd.cgms.aidex.BgType"; //  mmol/l or mg/dl
    String AIDEX_BG_VALUE = "com.microtechmd.cgms.aidex.BgValue";
    String AIDEX_BG_SLOPE_NAME = "com.microtechmd.cgms.aidex.BgSlopeName";
    String AIDEX_TIMESTAMP = "com.microtechmd.cgms.aidex.Time";  // epoch in ms
    String AIDEX_TRANSMITTER_SN = "com.microtechmd.cgms.aidex.TransmitterSerialNumber";
    String AIDEX_SENSOR_ID = "com.microtechmd.cgms.aidex.SensorId";

}
