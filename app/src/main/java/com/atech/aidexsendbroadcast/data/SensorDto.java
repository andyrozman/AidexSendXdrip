package com.atech.aidexsendbroadcast.data;

public class SensorDto {
    public long started_at = System.currentTimeMillis();
    public String sensorId;
    public TransmitterDto transmitterDto= new TransmitterDto();
}
