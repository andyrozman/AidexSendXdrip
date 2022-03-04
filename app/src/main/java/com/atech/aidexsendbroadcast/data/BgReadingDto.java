package com.atech.aidexsendbroadcast.data;

public class BgReadingDto {

    public Float glucose;
    public long timestamp; // time in MS (Epoch time: aka System.currentTimeMillis())
    public SensorDto sensor;

    public BgReadingDto(long time, float bg, SensorDto sensor) {
        this.glucose = bg;
        this.timestamp = time;
        this.sensor = sensor;
    }
}
