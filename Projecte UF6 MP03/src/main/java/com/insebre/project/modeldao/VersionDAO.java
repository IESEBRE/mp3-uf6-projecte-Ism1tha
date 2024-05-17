package com.insebre.project.modeldao;

import com.insebre.project.controller.DatabaseConnectionController;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Version;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The VersionDAO class implements the BasicDAO interface for managing Version objects in the database.
 */
public class VersionDAO implements BasicDAO<Version> {

    private final DatabaseConnectionController connectionController;

    /**
     * Constructs a new VersionDAO instance with the specified DatabaseConnectionController.
     *
     * @param connectionController the DatabaseConnectionController to use for database operations
     */
    public VersionDAO(DatabaseConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    @Override
    public Version get(int id) throws DAOException {
        throw new DAOException("Method unsupported for Version");
    }

    @Override
    public List<Version> getAll() throws DAOException {
        throw new DAOException("Method unsupported for Version");
    }

    /**
     * Retrieves all versions associated with a specific program from the database.
     *
     * @param programID the unique identifier of the program
     * @return a list containing all versions associated with the specified program
     * @throws DAOException if there is an error accessing the data source
     */
    public List<Version> getByProgramID(int programID) throws DAOException {
        String query = "SELECT * FROM versions WHERE program_id = ?";
        ResultSet results = connectionController.executeQuery(query, programID);
        List<Version> versions = new ArrayList<>();
        try {
            while (results.next()) {
                int id = results.getInt("id");
                String versionStr = results.getString("version");
                String releaseDate = formatDatabaseDate(results.getString("release_date"));
                String commits = results.getString("commits");
                Version version = new Version(id, programID, versionStr, releaseDate, commits);
                versions.add(version);
            }
        } catch (SQLException e) {
            throw new DAOException("Error processing results from database", e);
        }
        return versions;
    }

    @Override
    public void create(Version obj) throws DAOException {
        String query = "INSERT INTO versions (program_id, version, release_date, commits) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
        connectionController.executeQuery(query, obj.getProgramId(), obj.getVersion(), obj.getDate(), obj.getCommits());
    }

    @Override
    public void update(Version obj) throws DAOException {
        String query = "UPDATE versions SET version = ?, release_date = TO_DATE(?, 'YYYY-MM-DD'), commits = ? WHERE id = ?";
        connectionController.executeQuery(query, obj.getVersion(), obj.getDate(), obj.getCommits(), obj.getId());
    }

    @Override
    public void delete(int id) throws DAOException {
        String query = "DELETE FROM versions WHERE id = ?";
        connectionController.executeQuery(query, id);
    }

    /**
     * Formats a date string retrieved from the database to match the "yyyy-MM-dd" pattern.
     *
     * @param dateStringWithTime the date string retrieved from the database
     * @return the formatted date string
     */
    public String formatDatabaseDate(String dateStringWithTime) {
        LocalDate localDate = LocalDate.parse(dateStringWithTime.substring(0, 10));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
