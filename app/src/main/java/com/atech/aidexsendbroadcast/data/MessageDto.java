package com.atech.aidexsendbroadcast.data;

public class MessageDto {

    public long timestamp;
    public MessageType messageType;
    public String messageValue;

    public MessageDto(MessageType messageType, long timestamp, String messageValue) {
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.messageValue = messageValue;
    }

}
