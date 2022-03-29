package com.microtechmd.blecomm.entity;

import com.microtechmd.blecomm.parser.CgmBroadcastEntity;
import com.microtechmd.blecomm.parser.CgmHistoryEntity;


public class BroadcastEntity implements CgmBroadcastEntity {

    public long datetime;
    int battery;
    int state;
    float glucose;
    public CgmHistoryEntity  cgmHistory;
    private int primary;

    private long receivedTime; // this is internal variable set by receiver

    public long getDatetime() {
        return datetime;
    }

    public int getBattery() {
        return battery;
    }

    public int getState() {
        return state;
    }

    public float getGlucose() {
        return glucose;
    }

    public int getPrimary() {
        return primary;
    }

    public CgmHistoryEntity getCgmHistory() {
        return cgmHistory;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    @Override
    public void _setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @Override
    public void _setBattery(int battery) {
        this.battery = battery;
    }

    @Override
    public void _setState(int state) {
        this.state = state;
    }

    @Override
    public void _setGlucose(float glucose) {
        this.glucose = glucose;
    }

    @Override
    public void _setPrimary(int i) {
        this.primary = i;
    }

    @Override
    public void _setHistory(CgmHistoryEntity history) {
        this.cgmHistory = history;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BroadcastEntity [");
        sb.append("datetime=").append(datetime);
        sb.append(", battery=").append(battery);
        sb.append(", state=").append(state);
        sb.append(", glucose=").append(glucose);
        sb.append(", cgmHistory=").append(cgmHistory);
        sb.append(", primary=").append(primary);
        sb.append(", receivedTime=").append(receivedTime);
        sb.append(']');
        return sb.toString();
    }
}
