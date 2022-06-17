package io.bhimsur.cf14seabe.exception;

public class DataAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = -2474529765295558151L;

    public DataAlreadyExistException(String message) {
        super(message);
    }
}
