package com.ishizuka.demo.domain.exception;

public class FailedToSendMessageToQueueException extends RuntimeException {

    public FailedToSendMessageToQueueException(String message) {
        super(message);
    }

}
