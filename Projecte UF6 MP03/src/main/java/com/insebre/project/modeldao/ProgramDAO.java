package com.insebre.project.modeldao;

import com.insebre.project.controller.DatabaseConnectionController;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Program;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProgramDAO class implements the BasicDAO interface for managing Program objects in the database.
 */
public class ProgramDAO implements BasicDAO<Program> {

    private final DatabaseConnectionController connectionController;

    /**
     * Constructs a new ProgramDAO instance with the specified DatabaseConnectionController.
     *
     * @param connectionController the DatabaseConnectionController to use for database operations
     */
    public ProgramDAO(DatabaseConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    @Override
    public Program get(int id) throws DAOException {
        String query = "SELECT * FROM programs WHERE id = ?";
        try (ResultSet results = connectionController.executeQuery(query, id)) {
            if (results.next()) {
                return new Program(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("description"),
                        results.getString("category"),
                        results.getString("language"),
                        formatDatabaseDate(results.getString("release_date")),
                        results.getString("password"),
                        results.getBoolean("paid")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving program", e);
        }
        return null;
    }

    @Override
    public List<Program> getAll() throws DAOException {
        String query = "SELECT * FROM programs";
        List<Program> programs = new ArrayList<>();
        try (ResultSet results = connectionController.executeQuery(query)) {
            while (results.next()) {
                Program program = new Program(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("description"),
                        results.getString("category"),
                        results.getString("language"),
                        formatDatabaseDate(results.getString("release_date")),
                        null,
                        results.getBoolean("paid")
                );
                programs.add(program);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving programs", e);
        }
        return programs;
    }

    @Override
    public void create(Program obj) throws DAOException {
        int paid = obj.isPaid() ? 1 : 0;
        String query = "INSERT INTO programs (name, description, category, language, release_date, password, paid) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
        connectionController.executeQuery(query, obj.getName(), obj.getDescription(), obj.getCategory(), obj.getLanguage(), obj.getReleaseDate(), obj.getPassword(), paid);
    }

    @Override
    public void update(Program obj) throws DAOException {
        int paid = obj.isPaid() ? 1 : 0;
        String query = "UPDATE programs SET name = ?, description = ?, category = ?, language = ?, release_date = TO_DATE(?, 'YYYY-MM-DD'), password = ?, paid = ? WHERE id = ?";
        connectionController.executeQuery(query, obj.getName(), obj.getDescription(), obj.getCategory(), obj.getLanguage(), obj.getReleaseDate(), obj.getPassword(), paid, obj.getId());
    }

    @Override
    public void delete(int id) throws DAOException {
        String query = "DELETE FROM programs WHERE id = ?";
        connectionController.executeQuery(query, id);
    }

    /**
     * Formats a date string retrieved from the database to match the "yyyy-MM-dd" pattern.
     *
     * @param dateStringWithTime the date string retrieved from the database
     * @return the formatted date string
     */
    private String formatDatabaseDate(String dateStringWithTime) {
        LocalDate localDate = LocalDate.parse(dateStringWithTime.substring(0, 10));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
