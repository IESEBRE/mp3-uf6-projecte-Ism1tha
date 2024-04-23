package com.insebre.project.exception;

public class FileDataGenerateErrorException extends Exception {

    public FileDataGenerateErrorException() {
        super();
    }

    public FileDataGenerateErrorException(String message) {
        super(message);
    }

    public FileDataGenerateErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDataGenerateErrorException(Throwable cause) {
        super(cause);
    }
}
