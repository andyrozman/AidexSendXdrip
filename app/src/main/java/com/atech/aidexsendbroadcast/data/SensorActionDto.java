package com.atech.aidexsendbroadcast.data;

public class SensorActionDto {

    public SensorActionType action;
    public String sensorId;

    public long timestamp;

    public TransmitterDto transmitter; //= new TransmitterDto();

    public SensorActionDto(SensorActionType action, long timestamp, String sensorId, TransmitterDto transmitter) {
        this.action = action;
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.transmitter = transmitter;
    }

}
