package com.insebre.project.model;

import com.insebre.project.controller.AppController;
import com.insebre.project.exception.dao.DAOException;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a software program with its details and a collection of versions.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class Program implements Serializable {

    private final int id;
    private String name;
    private String description;
    private String category;
    private String language;
    private String releaseDate;
    private String password;
    private boolean paid;

    /**
     * Constructs a new Program instance with the specified details.
     *
     * @param id          The ID of the program.
     * @param name        The name of the program.
     * @param description The description of the program.
     * @param category    The category of the program.
     * @param language    The programming language used for the program.
     * @param releaseDate The release date of the program.
     * @param password    The password of the program.
     * @param paid        The program was paid?
     */
    public Program(int id, String name, String description, String category, String language, String releaseDate, String password, boolean paid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.language = language;
        this.releaseDate = releaseDate;
        this.password = password;
        this.paid = paid;
    }

    /**
     * Retrieves the ID of the program.
     *
     * @return The ID of the program.
     */
    public int getId() { return id; }

    /**
     * Retrieves the name of the program.
     *
     * @return The name of the program.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of the program.
     *
     * @return The description of the program.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the category of the program.
     *
     * @return The category of the program.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Retrieves the programming language used for the program.
     *
     * @return The programming language used for the program.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Retrieves the latest version of the program.
     *
     * @return The latest version of the program, or "No versions" if no versions exist.
     */
    public String getVersion() throws DAOException {
        List<Version> versions = AppController.versionDAO.getByProgramID(id);
        if (versions.isEmpty()) {
            return "No versions";
        }
        return versions.get(versions.size() - 1).getVersion();
    }

    /**
     * Retrieves the release date of the program.
     *
     * @return The release date of the program.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the name of the program.
     *
     * @param name The new name of the program.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the program.
     *
     * @param description The new description of the program.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the category of the program.
     *
     * @param category The new category of the program.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the programming language used for the program.
     *
     * @param language The new programming language used for the program.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Sets the release date of the program.
     *
     * @param releaseDate The new release date of the program.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Sets the password of the program.
     *
     * @param password The new password of the program.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the password of the program.
     *
     * @return The password of the program.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the paid status of the program.
     *
     * @return The paid status of the program.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the paid status of the program.
     *
     * @param paid The new paid status of the program.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
