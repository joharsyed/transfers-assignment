package com.nium.interview.transfers.exception;

/**
 * Class representing the exceptions related to the file operations
 */
public class FileException extends RuntimeException {

    /**
     * @param message Exception message
     * @param e       Exception
     */
    public FileException(String message, Throwable e) {
        super(message, e);
    }
}
