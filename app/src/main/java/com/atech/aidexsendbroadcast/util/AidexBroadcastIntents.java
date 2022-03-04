package com.atech.aidexsendbroadcast.util;

/**
 * For integration.
 */
public interface AidexBroadcastIntents {
    // DON'T CHANGE THIS - START

    /**
     * Permission receiving application will need to use if not running in old model
      */
    String RECEIVER_PERMISSION = "com.microtechmd.cgms.aidex.permissions.RECEIVE_BG_ESTIMATE";

    /**
     * Aidex Intent with Data
     */
    String AIDEX_NEW_BG_ESTIMATE = "com.microtechmd.cgms.aidex.BgEstimate";


    // DATA
    /**
     * BG Type: Can be either mmol/l or mg/dl
     */
    String AIDEX_BG_TYPE = "com.microtechmd.cgms.aidex.BgType";

    /**
     * BG Value: Its float (so it can be 10.0 or 181.0)
     */
    String AIDEX_BG_VALUE = "com.microtechmd.cgms.aidex.BgValue";

    /**
     * BG Slope Name: following values are valid:
     * "DoubleUp", "SingleUp", "FortyFiveUp", "Flat", "FortyFiveDown", "SingleDown",
     * "DoubleDown", "NotComputable", "RateOutOfRange"
     */
    String AIDEX_BG_SLOPE_NAME = "com.microtechmd.cgms.aidex.BgSlopeName";

    /**
     * Timestamp as Epoch (System.currentTimeMillis()) in miliseconds
     */
    String AIDEX_TIMESTAMP = "com.microtechmd.cgms.aidex.Time";  // epoch in ms

    /**
     * Transmitter Id (String)
     */
    String AIDEX_TRANSMITTER_SN = "com.microtechmd.cgms.aidex.TransmitterSerialNumber";

    /**
     * Sensor Id (String)
     */
    String AIDEX_SENSOR_ID = "com.microtechmd.cgms.aidex.SensorId";
    // DON'T CHANGE THIS - END

    // You can add your own data here

}
