package com.atech.aidexsendbroadcast.data;

import com.microtechmd.blecomm.constant.cgm.History;

/**
 * This is just little bit enriched BroadcastEntity
 */
public class AidexHistory  {

    public AidexHistory() {
        super();
    }

    public long datetime;

    public int battery;

    public int state;

    public float glucose;

    public int primaryClient;

    public String sensorId;

    public String tansmitterSn;

    public long historyDatetime;

    public int historyEventIndex;

    public int historySensorIndex;

    public int historyEventType;

    public Float historyEventValue;

    public String historyRawValue;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AidexHistory{");
        sb.append("datetime=").append(datetime);
        sb.append(", battery=").append(battery);
        sb.append(", state=").append(state);
        sb.append(", glucose=").append(glucose);
        sb.append(", primaryClient=").append(primaryClient);
        sb.append(", sensorId='").append(sensorId).append('\'');
        sb.append(", tansmitterSn='").append(tansmitterSn).append('\'');
        sb.append(", historyDatetime=").append(historyDatetime);
        sb.append(", historyEventIndex=").append(historyEventIndex);
        sb.append(", historySensorIndex=").append(historySensorIndex);
        sb.append(", historyEventType=").append(historyEventType);
        sb.append(", historyEventValue=").append(historyEventValue);
        sb.append(", historyRawValue='").append(historyRawValue).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean isValidGlucoseEntry() {
        return this.historyEventType == History.HISTORY_GLUCOSE ||
                this.historyEventType == History.HISTORY_GLUCOSE_RECOMMEND_CAL ||
                this.historyEventType == History.HISTORY_HYPER ||
                this.historyEventType == History.HISTORY_HYPO ||
                this.historyEventType == History.HISTORY_IMPENDANCE ||
                this.historyEventType == History.HISTORY_PLACEHOLDER
                ;
    }
}
