package com.microtechmd.blecomm.entity;

import android.content.res.Resources;

import java.util.Arrays;
import java.util.Date;

public final class CgmHistoryEntity implements com.microtechmd.blecomm.parser.CgmHistoryEntity {
    
    private Long idx;
    
    private String id;
    
    private String deviceSn;
    private long datetime;
    private Date deviceTime = new Date();
    private int eventIndex;
    private int sensorIndex;
    private int dataStatus;
    
    private Long recordIndex;
    private int eventType = 31;
    
    private Float eventData;

    private String deviceId;
    
    private String authorizationId;
    
    private String recordUuid;
    
    private Float rawData1;
    
    private Float rawData2;
    
    private Float rawData3;
    
    private Float rawData4;
    
    private Float rawData5;
    
    private Float rawData6;
    
    private Float rawData7;
    
    private Float rawData8;
    
    private Float rawData9;

    private float[] rawData;

    private Date time;


    public CgmHistoryEntity() {
        this.time = this.deviceTime;
    }


    
    public Long getIdx() {
        return this.idx;
    }

    public void setIdx( Long var1) {
        this.idx = var1;
    }

    
    public final String getId() {
        return this.id;
    }

    public final void setId( String var1) {
        this.id = var1;
    }

    
    public final String getDeviceSn() {
        return this.deviceSn;
    }

    public final void setDeviceSn( String var1) {
        this.deviceSn = var1;
    }


    public final Date getDeviceTime() {
        return this.deviceTime;
    }

    public final void setDeviceTime(Date var1) {
        this.deviceTime = var1;
    }

    public final int getEventIndex() {
        return this.eventIndex;
    }

    public final void setEventIndex(int var1) {
        this.eventIndex = var1;
    }

    public final int getSensorIndex() {
        return this.sensorIndex;
    }

    public final void setSensorIndex(int var1) {
        this.sensorIndex = var1;
    }

    public final int getDataStatus() {
        return this.dataStatus;
    }

    public final void setDataStatus(int var1) {
        this.dataStatus = var1;
    }

    
    public Long getRecordIndex() {
        return this.recordIndex;
    }

    public void setRecordIndex( Long var1) {
        this.recordIndex = var1;
    }

    public final int getEventType() {
        return this.eventType;
    }

    public final void setEventType(int var1) {
        this.eventType = var1;
    }

    
    public final Float getEventData() {
        return this.eventData;
    }

    public final void setEventData( Float var1) {
        this.eventData = var1;
    }

    
    public final String getDeviceId() {
        return this.deviceId;
    }

    public final void setDeviceId( String var1) {
        this.deviceId = var1;
    }

    
    public String getAuthorizationId() {
        return this.authorizationId;
    }

    public void setAuthorizationId( String var1) {
        this.authorizationId = var1;
    }

    
    public final String getRecordUuid() {
        return this.recordUuid;
    }

    public final void setRecordUuid( String var1) {
        this.recordUuid = var1;
    }

    
    public final Float getRawData1() {
        return this.rawData1;
    }

    public final void setRawData1( Float var1) {
        this.rawData1 = var1;
    }

    
    public final Float getRawData2() {
        return this.rawData2;
    }

    public final void setRawData2( Float var1) {
        this.rawData2 = var1;
    }

    
    public final Float getRawData3() {
        return this.rawData3;
    }

    public final void setRawData3( Float var1) {
        this.rawData3 = var1;
    }

    
    public final Float getRawData4() {
        return this.rawData4;
    }

    public final void setRawData4( Float var1) {
        this.rawData4 = var1;
    }

    
    public final Float getRawData5() {
        return this.rawData5;
    }

    public final void setRawData5( Float var1) {
        this.rawData5 = var1;
    }

    
    public final Float getRawData6() {
        return this.rawData6;
    }

    public final void setRawData6( Float var1) {
        this.rawData6 = var1;
    }

    
    public final Float getRawData7() {
        return this.rawData7;
    }

    public final void setRawData7( Float var1) {
        this.rawData7 = var1;
    }

    
    public final Float getRawData8() {
        return this.rawData8;
    }

    public final void setRawData8( Float var1) {
        this.rawData8 = var1;
    }

    
    public final Float getRawData9() {
        return this.rawData9;
    }

    public final void setRawData9( Float var1) {
        this.rawData9 = var1;
    }

    public Date getTime() {
        return this.deviceTime;
    }

    public void setTime(Date time) {

        this.time = time;
        this.deviceTime = time;
    }


    public String getEventDescription(Resources res) {
        int var2 = this.eventType;
        return "";
    }


    public String getValueDescription(Resources res) {
        int var2 = this.eventType;
        return "";
    }


    public void _setDatetime(long datetime) {
        this.datetime = datetime * 1000;
        this.deviceTime = new Date(datetime * (long) 1000);
        this.time = this.deviceTime;
    }

    public void _setEventIndex(int eventIndex) {
        this.eventIndex = eventIndex;
    }

    public void _setSensorIndex(int sensorIndex) {
        this.sensorIndex = sensorIndex;
    }

    public void _setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void _setEventValue(float eventValue) {
        this.eventData = eventValue;
    }

    public void _setRawValue(float[] rawValue) {
        this.rawData = rawValue;
        this.rawData1 = rawValue[0];
        this.rawData2 = rawValue[1];
        this.rawData3 = rawValue[2];
        this.rawData4 = rawValue[3];
        this.rawData5 = rawValue[4];
        this.rawData6 = rawValue[5];
        this.rawData7 = rawValue[6];
        this.rawData8 = rawValue[7];
        this.rawData9 = rawValue[8];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CgmHistoryEntity [");
        sb.append("idx=").append(idx);
        sb.append(", id='").append(id).append('\'');
        sb.append(", deviceSn='").append(deviceSn).append('\'');
        sb.append(", datetime=").append(datetime);
        sb.append(", deviceTime=").append(deviceTime);
        sb.append(", eventIndex=").append(eventIndex);
        sb.append(", sensorIndex=").append(sensorIndex);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", recordIndex=").append(recordIndex);
        sb.append(", eventType=").append(eventType);
        sb.append(", eventData=").append(eventData);
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", authorizationId='").append(authorizationId).append('\'');
        sb.append(", recordUuid='").append(recordUuid).append('\'');
        sb.append(", rawData1=").append(rawData1);
        sb.append(", rawData2=").append(rawData2);
        sb.append(", rawData3=").append(rawData3);
        sb.append(", rawData4=").append(rawData4);
        sb.append(", rawData5=").append(rawData5);
        sb.append(", rawData6=").append(rawData6);
        sb.append(", rawData7=").append(rawData7);
        sb.append(", rawData8=").append(rawData8);
        sb.append(", rawData9=").append(rawData9);
        sb.append(", rawData=").append(Arrays.toString(rawData));
        sb.append(", time=").append(time);
        sb.append(']');
        return sb.toString();
    }

    public long getDatetime() {
        return datetime;
    }

    public float[] getRawData() {
        return rawData;
    }
}


