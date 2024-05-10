package com.insebre.project.model;

import java.io.Serializable;

/**
 * Represents a software program with its details and a collection of versions.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class Program implements Serializable {

    private String name;
    private String description;
    private String category;
    private String language;
    private String releaseDate;
    private final SuperCollection<Version> versions;

    /**
     * Constructs a new Program instance with the specified details.
     *
     * @param name        The name of the program.
     * @param description The description of the program.
     * @param category    The category of the program.
     * @param language    The programming language used for the program.
     * @param releaseDate The release date of the program.
     */
    public Program(String name, String description, String category, String language, String releaseDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.language = language;
        this.releaseDate = releaseDate;
        this.versions = new SuperCollection<>(SuperCollection.CollectionType.ARRAY_LIST);
    }

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
    public String getVersion() {
        if (this.versions.isEmpty()) {
            return "No versions";
        }
        Version lastVersion = this.versions.get(this.versions.size() - 1);
        return lastVersion.getVersion();
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
     * Retrieves the collection of versions associated with the program.
     *
     * @return The collection of versions associated with the program.
     */
    public SuperCollection<Version> getVersions() {
        return versions;
    }

    /**
     * Adds a new version to the program.
     *
     * @param version     The version number.
     * @param releaseDate The release date of the version.
     * @param commits     Details of commits made in this version.
     */
    public void addVersion(String version, String releaseDate, String commits) {
        versions.add(new Version(version, releaseDate, commits));
    }

    /**
     * Edits an existing version of the program.
     *
     * @param index       The index of the version to edit.
     * @param version     The updated version number.
     * @param releaseDate The updated release date of the version.
     * @param commits     Updated details of commits made in this version.
     */
    public void editVersion(int index, String version, String releaseDate, String commits) {
        versions.customSet(index, new Version(version, releaseDate, commits));
    }

    /**
     * Deletes a version from the program.
     *
     * @param index The index of the version to delete.
     */
    public void deleteVersion(int index) {
        versions.customDelete(index);
    }

    /**
     * Switches the type of collection used for storing versions between ArrayList and TreeSet.
     */
    public void switchSuperCollectionType() {
        if (versions.getType() == SuperCollection.CollectionType.ARRAY_LIST) {
            versions.setType(SuperCollection.CollectionType.TREE_SET);
        } else {
            versions.setType(SuperCollection.CollectionType.ARRAY_LIST);
        }
    }

}
