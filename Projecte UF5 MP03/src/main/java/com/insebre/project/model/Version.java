package com.insebre.project.model;

import java.io.Serializable;

public class Version implements Serializable, Comparable<Version> {

    private final String version;
    private final String commits;
    private final String date;

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

    @Override
    public int compareTo(Version other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null");
        }

        return this.version.compareTo(other.version);
    }
}
