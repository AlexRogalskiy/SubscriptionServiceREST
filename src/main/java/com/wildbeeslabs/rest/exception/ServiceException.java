package com.wildbeeslabs.rest.exception;

/**
 *
 * Service REST Application Exception
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
