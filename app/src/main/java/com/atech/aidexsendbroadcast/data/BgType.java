package com.atech.aidexsendbroadcast.data;

import com.atech.aidexsendbroadcast.util.AidexBroadcastIntents;

public enum BgType {

    MMOL_L(AidexBroadcastIntents.UNIT_MMOL_L),
    MG_DL(AidexBroadcastIntents.UNIT_MG_DL);

    private String unitDescription;

    BgType(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getUnit() {
        return unitDescription;
    }
}
