package com.insebre.project.controller;

import com.insebre.project.exception.CustomException;

public class ExceptionController {

    public void throwCustomException() throws CustomException {
        // Some logic that may throw the custom exception
        throw new CustomException("This is a custom exception message.");
    }

}