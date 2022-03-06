package com.atech.aidexsendbroadcast.data;

public class SensorDto {
    public long timeStarted;
    public String sensorId;
    public TransmitterDto transmitter;

    public SensorDto(String sensorId, long timeStarted, TransmitterDto trasnsmitter) {
        this.sensorId = sensorId;
        this.timeStarted = timeStarted;
        this.transmitter = trasnsmitter;
    }
}
