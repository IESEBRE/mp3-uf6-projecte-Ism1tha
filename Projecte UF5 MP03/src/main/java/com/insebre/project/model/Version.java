package com.insebre.project.model;

public class Version {

    private String version;
    private String commits;
    private String date;

    public Version(String version, String commits, String date) {
        this.version = version;
        this.commits = commits;
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public String getCommits() {
        return commits;
    }

    public String getDate() {
        return date;
    }
}
