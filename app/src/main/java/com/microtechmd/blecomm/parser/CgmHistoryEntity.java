package com.microtechmd.blecomm.parser;

public interface CgmHistoryEntity {
    void _setDatetime(long datetime);
    void _setEventIndex(int eventIndex);
    void _setSensorIndex(int sensorIndex);
    void _setEventType(int eventType);
    void _setEventValue(float eventValue);
    void _setRawValue(float[] rawValue);


}
