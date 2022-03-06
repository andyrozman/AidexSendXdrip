package com.atech.aidexsendbroadcast.data;

public class CalibrationDto {

    public Float glucose;
    public long timestamp; // time in MS (Epoch time: aka System.currentTimeMillis())

    public CalibrationDto(Float glucose, long timestamp) {
        this.glucose = glucose;
        this.timestamp = timestamp;
    }

}
