package io.bhimsur.cf14seabe.exception;

public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = -7221307493843668494L;

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
