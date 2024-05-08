package com.insebre.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Program implements Serializable {

    private String name;
    private String description;
    private String category;
    private String language;
    private String version;
    private String releaseDate;

    /* Collection of commits using generics */
    private final SuperCollection<Version> versions;

    public Program(String name, String description, String category, String language, String version, String releaseDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.language = language;
        this.version = version;
        this.releaseDate = releaseDate;
        this.versions = new SuperCollection<>(SuperCollection.CollectionType.ARRAY_LIST);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getVersion() {
        return version;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public SuperCollection<Version> getVersions() {
        return versions;
    }

    public void addVersion(String version, String releaseDate, String commits) {
        versions.add(new Version(version, releaseDate, commits));
    }

    public void editVersion(int index, String version, String releaseDate, String commits) {
        versions.customSet(index, new Version(version, releaseDate, commits));
    }

    public void deleteVersion(int index) {
        versions.customDelete(index);
    }

    public void switchSuperCollectionType() {
        if (versions.getType() == SuperCollection.CollectionType.ARRAY_LIST) {
            versions.setType(SuperCollection.CollectionType.TREE_SET);
        } else {
            versions.setType(SuperCollection.CollectionType.ARRAY_LIST);
        }
    }

}
