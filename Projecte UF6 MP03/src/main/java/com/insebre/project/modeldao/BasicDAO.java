package com.insebre.project.modeldao;

import com.insebre.project.exception.dao.DAOException;

import java.sql.SQLException;
import java.util.List;

/**
 * The BasicDAO interface provides methods for basic CRUD (Create, Read, Update, Delete) operations
 * on a generic type T.
 *
 * @param <T> the type of objects managed by this DAO
 */
public interface BasicDAO<T> {

    /**
     * Retrieves an object of type T from the database based on its unique identifier.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object retrieved from the database
     * @throws DAOException if there is an error accessing the data source
     * @throws SQLException if a database access error occurs
     */
    T get(int id) throws DAOException, SQLException;

    /**
     * Retrieves all objects of type T from the database.
     *
     * @return a list containing all objects of type T retrieved from the database
     * @throws DAOException if there is an error accessing the data source
     */
    List<T> getAll() throws DAOException;

    /**
     * Inserts a new object into the database.
     *
     * @param obj the object to be inserted into the database
     * @throws DAOException if there is an error accessing the data source
     */
    void create(T obj) throws DAOException;

    /**
     * Updates an existing object in the database.
     *
     * @param obj the object to be updated in the database
     * @throws DAOException if there is an error accessing the data source
     */
    void update(T obj) throws DAOException;

    /**
     * Deletes an object from the database based on its unique identifier.
     *
     * @param id the unique identifier of the object to delete
     * @throws DAOException if there is an error accessing the data source
     */
    void delete(int id) throws DAOException;
}
