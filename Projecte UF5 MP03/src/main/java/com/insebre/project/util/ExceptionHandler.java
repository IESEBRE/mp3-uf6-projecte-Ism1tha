package com.insebre.project.util;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler {
    private Map<Class<? extends Exception>, String> exceptionMap;

    public ExceptionHandler() {
        exceptionMap = new HashMap<>();
        exceptionMap.put(ArrayIndexOutOfBoundsException.class, "Array index out of bounds!");
        exceptionMap.put(com.insebre.project.exception.CustomException.class, "Custom Exception!");
    }

    public String handleException(Exception e) {
        String message = exceptionMap.get(e.getClass());
        if (message != null) {
            return message;
        } else {
            return "An unknown exception occurred!";
        }
    }
}
