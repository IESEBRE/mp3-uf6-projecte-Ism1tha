package com.insebre.project.model;

import java.io.Serializable;

public class Version implements Serializable, Comparable<Version> {

    private final String version;
    private final String date;
    private final String commits;

    public Version(String version, String date, String commits) {
        this.version = version;
        this.date = date;
        this.commits = commits;
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

    @Override
    public int compareTo(Version other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null");
        }

        return this.version.compareTo(other.version);
    }
}
