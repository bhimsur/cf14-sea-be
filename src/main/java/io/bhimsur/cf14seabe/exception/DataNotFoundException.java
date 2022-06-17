package io.bhimsur.cf14seabe.exception;

public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7048429634171471542L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
