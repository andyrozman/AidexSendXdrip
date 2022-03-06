# Aidex Send Broadcast

## Actions
I defined 6 different actions that can be sent (code is there)


Key  | Value (Intent) | Description
------------- | ------------- | ------
ACTION_NEW_BG_ESTIMATE  | com.microtechmd.cgms.aidex.action.BgEstimate | new BG data
ACTION_CALIBRATION  | com.microtechmd.cgms.aidex.action.Calibration | calibration added
ACTION_SENSOR_NEW | com.microtechmd.cgms.aidex.action.SensorNew | when sensor is started
ACTION_SENSOR_RESTART | com.microtechmd.cgms.aidex.action.SensorRestart | sensor restarted
ACTION_SENSOR_STOP | com.microtechmd.cgms.aidex.action.SensorStop | sensor stopped
ACTION_NOTIFICATION | com.microtechmd.cgms.aidex.action.Notification | send notification

------------

## Action: ACTION_NEW_BG_ESTIMATE

Parameters required:

| Parameter Key  | Possible values  | Description  |
| ------------ | ------------ | ------------ |
|  AIDEX_BG_TYPE | mmol/l or mg/dl  | Unit used for BG  |
|  AIDEX_BG_VALUE | float value (10.0 or 180.0)  |  BG Value |
| AIDEX_BG_SLOPE_NAME  | "DoubleUp", "SingleUp", "FortyFiveUp", "Flat", "FortyFiveDown", "SingleDown", "DoubleDown", "NotComputable", "RateOutOfRange"  | Name of slope  |
|  AIDEX_TIMESTAMP | time in ms (aka System.currentTimeMillis())  |  Timestamp |
| AIDEX_TRANSMITTER_SN  |  serial of transmiter (string) | Serial Number of transmitter  |
| AIDEX_SENSOR_ID  |  Sensor Id  | Id of sensor (needs to be unique) |

------------

## Action: ACTION_CALIBRATION

Parameters required:

| Parameter Key  | Possible values  | Description  |
| ------------ | ------------ | ------------ |
|  AIDEX_BG_TYPE | mmol/l or mg/dl  | Unit used for BG  |
|  AIDEX_BG_VALUE | float value (10.0 or 180.0)  |  BG Value |
|  AIDEX_TIMESTAMP | time in ms (aka System.currentTimeMillis())  |  Timestamp |


------------


## Action: ACTION_SENSOR_NEW or ACTION_SENSOR_RESTART or ACTION_SENSOR_STOP

Parameters required:

| Parameter Key  | Possible values  | Description  |
| ------------ | ------------ | ------------ |
|  AIDEX_TIMESTAMP | time in ms (aka System.currentTimeMillis())  |  Timestamp |
| AIDEX_TRANSMITTER_SN  |  serial of transmiter (string) | Serial Number of transmitter  |
| AIDEX_SENSOR_ID  |  Sensor Id  | Id of sensor (needs to be unique) |

------------

## Action: ACTION_NOTIFICATION

Parameters required:

| Parameter Key  | Possible values  | Description  |
| ------------ | ------------ | ------------ |
|  AIDEX_TIMESTAMP | time in ms (aka System.currentTimeMillis())  |  Timestamp |
| AIDEX_MESSAGE_TYPE  |  SENSOR_ERROR, CALIBRATION_REQUESTED, BATTERY_LOW, BATTERY_EMPTY, SENSOR_EXPIRED, OTHER | Message type  |
| AIDEX_MESSAGE_VALUE  |  String value  | Value, depending of message type, can be empty |

Message type can be extended, but using application might not support it.


## What needs to be implemented

If all of actions can be implemented that would be the best (you can just use the classes here
but you might need to change/replace data classes, to corespond with Aidex implementation)

