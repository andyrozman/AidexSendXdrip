package com.microtechmd.blecomm.constant.cgm;

public class History {
    public static final int HISTORY_TRANSMITTER_STARTUP = 0;
    public static final int HISTORY_TRANSMITTER_ERROR = 1;
    public static final int HISTORY_BATTERY_LOW = 2;
    public static final int HISTORY_BATTERY_EXHAUSTED = 3;
    public static final int HISTORY_SENSOR_NEW = 4;
    public static final int HISTORY_SENSOR_ERROR = 5;
    public static final int HISTORY_SENSOR_EXPIRATION = 6;
    public static final int HISTORY_GLUCOSE = 7;
    public static final int HISTORY_GLUCOSE_RECOMMEND_CAL = 8;
    public static final int HISTORY_GLUCOSE_INVALID = 9;
    public static final int HISTORY_HYPO = 10;
    public static final int HISTORY_HYPER = 11;
    public static final int HISTORY_IMPENDANCE = 12;
    public static final int HISTORY_BLOOD_GLUCOSE = 13;
    public static final int HISTORY_CALIBRATION = 14;
    public static final int HISTORY_HYPO_SETTING = 15;
    public static final int HISTORY_HYPER_SETTING = 16;
    public static final int HISTORY_PLACEHOLDER = 0x1E & 255; // 30
    public static final int HISTORY_INVALID = 0x1F & 255; // 31

    public static final long YEAR_2000 = 946656000;
}
