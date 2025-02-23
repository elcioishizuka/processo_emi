package com.ishizuka.demo.domain.exception;

public class DebitCancelAlreadyInQueueOrProcessedException extends RuntimeException {

    public DebitCancelAlreadyInQueueOrProcessedException(String message) {
        super(message);
    }

}
