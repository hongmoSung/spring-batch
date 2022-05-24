package io.springbatch.springbatchlecture.retry.one;

public class RetryalbeException extends RuntimeException {

    public RetryalbeException() {
        super();
    }

    public RetryalbeException(String message) {
        super(message);
    }
}
