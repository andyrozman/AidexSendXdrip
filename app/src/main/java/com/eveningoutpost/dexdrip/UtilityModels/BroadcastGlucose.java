package com.eveningoutpost.dexdrip.UtilityModels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.atech.aidexsendbroadcast.data.BgReadingDto;
import com.atech.aidexsendbroadcast.data.SensorDto;

// created by jamorham

public class BroadcastGlucose {

    private static final String TAG = "BroadcastGlucose";
    private static long lastTimestamp = 0;
    public static final long MINUTE_IN_MS = 60_000;

    public static void sendLocalBroadcast(final BgReadingDto bgReading, SensorDto sensor) {
        if (SendXdripBroadcast.enabled()) {
            final Bundle bundle = new Bundle();
            if (bgReading != null) {

                if (Math.abs(bgReading.timestamp - lastTimestamp) < MINUTE_IN_MS) {
                    String msg = String.format("Refusing to broadcast a reading with close timestamp to last broadcast:  %s (%d) vs %s (%d) ", dateTimeText(lastTimestamp), lastTimestamp, dateTimeText(bgReading.timestamp), bgReading.timestamp);
                    if (bgReading.timestamp == lastTimestamp) {
                        Log.d(TAG, msg);
                    } else {
                        Log.e(TAG, msg);
                    }
                    return;
                }

                //val sensor = Sensor.currentSensor();
                if (sensor == null) {
                    Log.e(TAG, "Refusing to broadcast a reading as no sensor is active");
                    return;
                }

                if (bgReading.timestamp <= sensor.started_at) {
                    Log.e(TAG, "Refusing to broadcast a reading before sensor start:  " + dateTimeText(sensor.started_at) + " vs " + dateTimeText(bgReading.timestamp));
                    return;
                }

                lastTimestamp = bgReading.timestamp;

                //BestGlucose.DisplayGlucose dg;

                Log.i("SENSOR QUEUE:", "Broadcast data");

                bundle.putString(Intents.XDRIP_DATA_SOURCE_DESCRIPTION, "AiDEX");
//                if (bgReading.source_info != null) {
//                    bundle.putString(Intents.XDRIP_DATA_SOURCE_INFO, bgReading.source_info);
//                }


//                final int noiseBlockLevel = Noise.getNoiseBlockLevel();
//                bundle.putInt(Intents.EXTRA_NOISE_BLOCK_LEVEL, noiseBlockLevel);


                // TODO added by Andy : EXTRA_BG_TYPE
                bundle.putString(Intents.EXTRA_BG_TYPE, "mmol/l"); // mmol/l or mg/dl
                bundle.putDouble(Intents.EXTRA_BG_ESTIMATE, bgReading.glucose.byteValue());
                bundle.putString(Intents.EXTRA_BG_SLOPE_NAME, getTrendSlope(bgReading));

                bundle.putLong(Intents.EXTRA_TIMESTAMP, bgReading.timestamp);

                //addCollectorStatus(bundle);
                final Intent intent = new Intent(Intents.ACTION_NEW_BG_ESTIMATE);
                SendXdripBroadcast.send(intent, bundle);
            } 
        }
    }

    public static String getTrendSlope(BgReadingDto reading) {

        // TODO slope needs to be calculated and correct TREND_ARROW_VALUES.friendlyTrendName() returned

        return TREND_ARROW_VALUES.FLAT.friendlyTrendName();

    }

    public static String dateTimeText(long timestamp) {
        return android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", timestamp).toString();
    }

    public enum TREND_ARROW_VALUES {
        NONE(0),
        DOUBLE_UP(1,"\u21C8", "DoubleUp", 40d),
        SINGLE_UP(2,"\u2191", "SingleUp", 3.5d),
        UP_45(3,"\u2197", "FortyFiveUp", 2d),
        FLAT(4,"\u2192", "Flat", 1d),
        DOWN_45(5,"\u2198", "FortyFiveDown", -1d),
        SINGLE_DOWN(6,"\u2193", "SingleDown", -2d),
        DOUBLE_DOWN(7,"\u21CA", "DoubleDown", -3.5d),
        NOT_COMPUTABLE(8, "", "NotComputable"),
        OUT_OF_RANGE(9, "", "RateOutOfRange");

        private String arrowSymbol;
        private String trendName;
        private int myID;
        private Double threshold;

        TREND_ARROW_VALUES(int id, String symbol, String name) {
            this.myID = id;
            this.arrowSymbol = symbol;
            this.trendName = name;
        }

        TREND_ARROW_VALUES(int id, String symbol, String name, Double threshold) {
            this.myID = id;
            this.arrowSymbol = symbol;
            this.trendName = name;
            this.threshold = threshold;
        }

        TREND_ARROW_VALUES(int id) {
            this(id,null, null, null);
        }

        public String Symbol() {
            if (arrowSymbol == null) {
                return "\u2194\uFE0E";
            } else {
                return arrowSymbol + "\uFE0E";
            }
        }

        public String trendName() {
            return this.trendName;
        }

        public String friendlyTrendName() {
            if (trendName == null) {
                return name().replace("_", " ");
            } else {
                return trendName;
            }
        }

        public int getID() {
            return myID;
        }

        public static TREND_ARROW_VALUES getTrend(double value) {
            TREND_ARROW_VALUES finalTrend = NONE;
            for (TREND_ARROW_VALUES trend : values()) {
                if (trend.threshold == null)
                    continue;

                if (value > trend.threshold)
                    return finalTrend;
                else
                    finalTrend = trend;
            }
            return finalTrend;
        }

        public static double getSlope(String value) {
            for (TREND_ARROW_VALUES trend : values())
                if (trend.trendName.equalsIgnoreCase(value))
                    return trend.threshold;
            throw new IllegalArgumentException();
        }

        public static TREND_ARROW_VALUES getEnum(int id) {
            for (TREND_ARROW_VALUES t : values()) {
                if (t.myID == id)
                    return t;
            }
            return OUT_OF_RANGE;
        }

        public static TREND_ARROW_VALUES getEnum(String value) {
            try {
                int val = Integer.parseInt(value);
                return getEnum(val);
            } catch (NumberFormatException e) {
                for (TREND_ARROW_VALUES trend : values())
                    if (trend.friendlyTrendName().equalsIgnoreCase(value) || trend.name().equalsIgnoreCase(value))
                        return trend;
            }
            return OUT_OF_RANGE;
        }

    }


//    private static void addCollectorStatus(final Bundle bundle) {
//        final String result = NanoStatus.nanoStatus("collector");
//        if (result != null) {
//            bundle.putString(Intents.EXTRA_COLLECTOR_NANOSTATUS, result);
//        }
//    }
}
