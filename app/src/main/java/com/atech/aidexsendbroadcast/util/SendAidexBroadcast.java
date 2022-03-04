package com.atech.aidexsendbroadcast.util;

import android.content.Intent;
import android.os.Bundle;

import com.eveningoutpost.dexdrip.UtilityModels.Pref;

import static com.atech.aidexsendbroadcast.BroadcastApplication.getAppContext;


/**
 * jamorham
 *
 * Locally broadcast an Aidex intent for other apps, caller should check enabled() first
 * handles different and legacy configuration options for package/permission destination
 */

public class SendAidexBroadcast {

    public static void send(final Intent intent, final Bundle bundle) {
        // TODO settings
        final String destination = Pref.getString("local_broadcast_specific_package_destination", "").trim();
        if (destination.length() > 3) {
            intent.setPackage(destination);
        }

        if (bundle != null) intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        // TODO settings
        if (Pref.getBooleanDefaultFalse("broadcast_data_through_intents_without_permission")) {
            getAppContext().sendBroadcast(intent);
        } else {
            getAppContext().sendBroadcast(intent, AidexBroadcastIntents.RECEIVER_PERMISSION);
        }
    }

    public static boolean enabled() {
        // TODO settings
        return Pref.getBooleanDefaultFalse("broadcast_data_through_intents");
    }

}
