package com.insebre.project.controller;

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
     * @param programIndex The index of the program for which to check the password.
     * @param password     The password to check.
     * @return true if the passwords do not match, false otherwise.
     */
    public static boolean checkProgramPassword(int programIndex, String password) {
        String storedPassword = readProgramPassword(programIndex);
        return !storedPassword.equals(password);
    }

    /**
     * Inserts a password for a specific program index into the passwords file.
     *
     * @param programIndex The index of the program for which to insert the password.
     * @param password     The password to insert.
     */
    public static void insertProgramPassword(int programIndex, String password) {
        try (RandomAccessFile file = new RandomAccessFile("passwords.dat", "rw")) {
            int position = programIndex * PASSWORD_LENGTH;
            file.seek(position);
            file.write(password.getBytes());
        } catch (IOException ex) {
            ExceptionController.handleException(ex);
        }
    }

    /**
     * Reads the password associated with a specific program index from the passwords file.
     *
     * @param programIndex The index of the program for which to read the password.
     * @return The password associated with the program index.
     */
    public static String readProgramPassword(int programIndex) {
        String password = null;
        try (RandomAccessFile file = new RandomAccessFile("passwords.dat", "r")) {
            int position = programIndex * PASSWORD_LENGTH;
            file.seek(position);
            byte[] passwordBytes = new byte[PASSWORD_LENGTH];
            file.read(passwordBytes);
            password = new String(passwordBytes).trim(); // Assuming passwords are null-terminated
        } catch (IOException ex) {
            ExceptionController.handleException(ex);
        }
        return password;
    }

    /**
     * Deletes the password associated with a specific program index from the passwords file.
     *
     * @param programIndex The index of the program for which to delete the password.
     */
    public static void deleteProgramPassword(int programIndex) {
        try (RandomAccessFile file = new RandomAccessFile("passwords.dat", "rw")) {
            byte[] newFile = new byte[(int) file.length() - PASSWORD_LENGTH];
            int position = programIndex * PASSWORD_LENGTH;
            for (int i = 0; i < position; i++) {
                newFile[i] = file.readByte();
            }
            position += PASSWORD_LENGTH;
            file.seek(position);
            for (int i = position; i < file.length(); i++) {
                newFile[i - PASSWORD_LENGTH] = file.readByte();
            }
            file.setLength(0);
            file.write(newFile);
        } catch (IOException ex) {
            ExceptionController.handleException(ex);
        }
    }
}
