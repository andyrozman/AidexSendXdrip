package com.atech.aidexsendbroadcast.data;

public class BgReadingDto {

    public Float glucose;
    public long timestamp; // time in MS (Epoch time: aka System.currentTimeMillis())

    public BgReadingDto(long time, float bg) {
        this.glucose = bg;
        this.timestamp = time;
    }
}
