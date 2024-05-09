package com.insebre.project.model;

import java.io.Serializable;

public class Program implements Serializable {

    private String name;
    private String description;
    private String category;
    private String language;
    private String releaseDate;
    private final SuperCollection<Version> versions;

    public Program(String name, String description, String category, String language, String releaseDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.language = language;
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
        if(this.versions.isEmpty()) {
            return "No versions";
        }
        Version lastVersion = this.versions.get(this.versions.size() - 1);
        return lastVersion.getVersion();
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
