package com.boneix.base.jms.entity;

/**
 * Created by rzhang on 2017/6/13.
 */
public class JmsSendResult {
    boolean status;
    String messageId;
    String destination;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "JmsSendResult[destination=" + destination + ", status=" + status + ", messageId=" + messageId + "]";
    }
}
