package com.project.busapp.exception;

public class NotEnoughSeatsException extends RuntimeException { // Extend RuntimeException
    public NotEnoughSeatsException(String message) {
        super(message);
    }
}