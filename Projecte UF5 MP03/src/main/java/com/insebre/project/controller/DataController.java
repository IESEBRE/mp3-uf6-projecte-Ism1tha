package com.insebre.project.controller;

import com.insebre.project.exception.FileCorruptDataException;
import com.insebre.project.exception.FileDataGenerateErrorException;
import com.insebre.project.exception.FileNullOnSaveException;
import com.insebre.project.model.Program;

import javax.swing.*;
import java.io.*;

/**
 * Controller class responsible for managing program data and file I/O operations.
 * This class handles loading, saving, and manipulating program data stored in a file.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class DataController {

    /* Constants */
    public static final int MAX_PROGRAMS = 200;

    /* Program Data */
    public static Program[] appData = new Program[MAX_PROGRAMS];
    public static int appDataIndex = 0;

    /**
     * Loads program data from the file "programs.dat".
     * If the file does not exist, it creates an empty file and displays a message.
     * Handles exceptions related to file operations and data corruption.
     */
    public static void loadData() {
        try {
            File data = new File(System.getProperty("user.dir"), "programs.dat");
            FileInputStream fis = new FileInputStream(data);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            while (true) {
                Program item = (Program) ois.readObject();
                appData[appDataIndex] = item;
                appDataIndex++;
            }
        } catch (NullPointerException | FileNotFoundException ex) {
            String dataFile = System.getProperty("user.dir") + "/programs.dat";
            try {
                FileOutputStream fos = new FileOutputStream(dataFile);
                fos.close();
                JOptionPane.showMessageDialog(null,
                        "The data file has been generated successfully.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                ExceptionController.handleException(new FileDataGenerateErrorException());
            }
        } catch (EOFException ex) {
            JOptionPane.showMessageDialog(null,
                    "The data file was found and loaded successfully.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ExceptionController.handleException(new FileCorruptDataException());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    ex,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Data loaded successfully.");
    }

    /**
     * Saves the program data to the file "programs.dat".
     * Handles exceptions related to file not found or other I/O errors.
     */
    public static void saveData() {
        try {
            File data = new File(System.getProperty("user.dir"), "programs.dat");
            FileOutputStream fos = new FileOutputStream(data);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            for (int i = 0; i < appDataIndex; i++) {
                if (appData[i] != null) {
                    oos.writeObject(appData[i]);
                }
            }
            oos.close();
            bos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            ExceptionController.handleException(new FileNullOnSaveException());
        } catch (IOException ex) {
            ExceptionController.handleException(ex);
        }
    }

    /**
     * Retrieves the array of program data.
     *
     * @return The array of Program objects.
     */
    public static Program[] getData() {
        return appData;
    }

    /**
     * Sets the array of program data to a new array.
     *
     * @param newData The new array of Program objects.
     */
    public static void setData(Program[] newData) {
        appData = newData;
    }

    /**
     * Reallocates the array of program data to remove null elements.
     */
    public static void reallocateDataObjects() {
        Program[] appDataTmp = new Program[MAX_PROGRAMS];
        int tmpIndex = 0;
        for (int i = 0; i < MAX_PROGRAMS; i++) {
            if (appData[i] != null) {
                appDataTmp[tmpIndex] = appData[i];
                tmpIndex++;
            }
        }
        appData = appDataTmp;
    }

    /**
     * Converts the program data into a 2D array for display purposes.
     *
     * @return A 2D array representing the parsed program data.
     */
    public static Object[][] getParsedPrograms() {
        Program[] programs = DataController.getData();
        Object[][] parsedPrograms = new Object[appDataIndex][6];
        for (int i = 0; i < appDataIndex; i++) {
            parsedPrograms[i][0] = programs[i].getName();
            parsedPrograms[i][1] = programs[i].getDescription();
            parsedPrograms[i][2] = programs[i].getCategory();
            parsedPrograms[i][3] = programs[i].getLanguage();
            parsedPrograms[i][4] = programs[i].getVersion();
            parsedPrograms[i][5] = programs[i].getReleaseDate();
        }
        return parsedPrograms;
    }

    /**
     * Converts the program versions of a specific program into a 2D array for display purposes.
     *
     * @param programIndex The index of the program in the data array.
     * @return A 2D array representing the parsed program versions.
     */
    public static Object[][] getParsedProgramVersions(int programIndex) {
        Program program = appData[programIndex];
        if (program == null || program.getVersions() == null || program.getVersions().isEmpty()) {
            return new Object[0][3]; // Return an empty 2D array with 0 rows and 3 columns
        }

        Object[][] parsedProgramVersions = new Object[program.getVersions().size()][3];
        for (int i = 0; i < program.getVersions().size(); i++) {
            parsedProgramVersions[i][0] = program.getVersions().get(i).getVersion();
            parsedProgramVersions[i][1] = program.getVersions().get(i).getDate();
            parsedProgramVersions[i][2] = program.getVersions().get(i).getCommits();
        }
        return parsedProgramVersions;
    }

    /**
     * Adds a new version to the program at the specified index.
     *
     * @param programIndex The index of the program in the data array.
     * @param version      The version number of the new version.
     * @param releaseDate  The release date of the new version.
     * @param commits      The commits associated with the new version.
     */
    public static void addProgramVersion(int programIndex, String version, String releaseDate, String commits) {
        Program program = appData[programIndex];
        if (program == null) {
            return;
        }
        program.addVersion(version, releaseDate, commits);
        saveData();
    }

    /**
     * Edits an existing program version at the specified indices.
     *
     * @param programIndex The index of the program in the data array.
     * @param versionIndex The index of the version to be edited.
     * @param version      The updated version number.
     * @param releaseDate  The updated release date.
     * @param commits      The updated commits.
     */
    public static void editProgramVersion(int programIndex, int versionIndex, String version, String releaseDate, String commits) {
        Program program = appData[programIndex];
        if (program == null) {
            return;
        }
        program.editVersion(versionIndex, version, releaseDate, commits);
        saveData();
    }

    /**
     * Deletes an existing program version at the specified indices.
     *
     * @param programIndex The index of the program in the data array.
     * @param versionIndex The index of the version to be deleted.
     */
    public static void deleteProgramVersion(int programIndex, int versionIndex) {
        Program program = appData[programIndex];
        if (program == null) {
            return;
        }
        program.deleteVersion(versionIndex);
        saveData();
    }

    /**
     * Switches the collection type used for program versions at the specified index.
     *
     * @param programIndex The index of the program in the data array.
     */
    public static void switchProgramSuperCollectionType(int programIndex) {
        Program program = appData[programIndex];
        if (program == null) {
            return;
        }
        program.switchSuperCollectionType();
        saveData();
    }
}
