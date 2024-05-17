package com.insebre.project.controller;

import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Program;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Controller class responsible for managing passwords associated with program indices.
 * This class provides methods to check, insert, read, and delete program passwords using random access files.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class PasswordController {
    /* Constants */
    public static final int PASSWORD_LENGTH = 4;

    /**
     * Checks if the provided password matches the stored password for a specific program index.
     *
     * @param programID The index of the program for which to check the password.
     * @param password     The password to check.
     * @return true if the passwords do not match, false otherwise.
     */
    public static boolean checkProgramPassword(int programID, String password) throws DAOException {
        Program program = AppController.programDAO.get(programID);
        return !program.getPassword().equals(password);
    }
}
