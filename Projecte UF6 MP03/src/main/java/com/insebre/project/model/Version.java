package com.insebre.project.model;

import java.io.Serializable;

/**
 * Represents a version of a software program.
 * Each version has a version number, release date, and associated commits.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class Version implements Serializable {

    private final int id;
    private final int program_id;
    private final String version;
    private final String date;
    private final String commits;

    /**
     * Constructs a new Version object with the specified version number, release date, and commits.
     *
     * @param version The version number (e.g., "1.0", "2.1.3").
     * @param date    The release date of the version (e.g., "2024-05-10").
     * @param commits A description of the commits included in this version.
     */
    public Version(int id, int program_id, String version, String date, String commits) {
        this.id = id;
        this.program_id = program_id;
        this.version = version;
        this.date = date;
        this.commits = commits;
    }

    /**
     * Retrieves the unique identifier of this version.
     *
     * @return The unique identifier of this version.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the program ID associated with this version.
     *
     * @return The program ID as an integer.
     */
    public int getProgramId() {
        return program_id;
    }

    /**
     * Retrieves the version number of this version.
     *
     * @return The version number as a string.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Retrieves the commit description associated with this version.
     *
     * @return A string describing the commits in this version.
     */
    public String getCommits() {
        return commits;
    }

    /**
     * Retrieves the release date of this version.
     *
     * @return The release date as a string.
     */
    public String getDate() {
        return date;
    }
}
