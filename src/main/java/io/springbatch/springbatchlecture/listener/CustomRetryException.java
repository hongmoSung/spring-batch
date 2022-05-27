package io.springbatch.springbatchlecture.listener;

public class CustomRetryException extends Exception {
    public CustomRetryException(String message) {
        super(message);
    }
}
