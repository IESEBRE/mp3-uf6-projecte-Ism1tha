package com.insebre.project.controller;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PasswordController {
    /* Constants */
    public static final int PASSWORD_LENGTH = 4;

    public static boolean checkProgramPassword(int programIndex, String password){
        String storedPassword = readProgramPassword(programIndex);
        return !storedPassword.equals(password);
    }

    public static void insertProgramPassword(int programIndex, String password){
        try (RandomAccessFile file = new RandomAccessFile("passwords.dat", "rw")) {
            int position = programIndex * PASSWORD_LENGTH;
            file.seek(position);
            file.write(password.getBytes());
        } catch (IOException ex) {
            ExceptionController.handleException(ex);
        }
    }

    public static String readProgramPassword(int programIndex){
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

    public static void deleteProgramPassword(int programIndex){
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
