package com.insebre.project.controller;

import com.insebre.project.controller.form.MainFormController;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.DatabaseConnection;
import com.insebre.project.model.Program;
import com.insebre.project.modeldao.ProgramDAO;
import com.insebre.project.modeldao.VersionDAO;
import com.insebre.project.view.MainForm;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Controller class responsible for managing the application flow and interactions.
 * This class handles the main form and related functionalities.
 * It initializes the main form, loads data, and provides methods for form operations.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class AppController {

    static MainForm mainFormInstance;
    static MainFormController mainFormController;

    static DatabaseConnection connection;
    static DatabaseConnectionController connectionController;

    public static ProgramDAO programDAO;
    public static VersionDAO versionDAO;

    /**
     * Flag indicating whether the add program form is currently displayed.
     */
    public static boolean showingAddProgramForm = false;

    /**
     * Flag indicating whether the edit program form is currently displayed.
     */
    public static boolean showingEditProgramForm = false;

    /**
     * Flag indicating whether the view program version form is currently displayed.
     */
    public static boolean showingViewProgramVersionForm = false;

    /**
     * Runs the application by initializing the main form and loading data.
     * If an exception occurs during initialization, it is handled by the ExceptionController.
     */
    public void run() {
        try {
            Locale.setDefault(new Locale("ca", "ES")); // Estableix la configuració regional per defecte a català (Espanya)
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new DAOException("Unable to find 'db.properties' file");
                }
                props.load(input);
            }

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connection = new DatabaseConnection(url, username, password);
            connectionController = new DatabaseConnectionController(connection);
            if(connectionController.connect()) {
                programDAO = new ProgramDAO(connectionController);
                versionDAO = new VersionDAO(connectionController);
                openMainForm();
            }
        } catch (Exception e) {
            ExceptionController.handleException(e); // Handle any exceptions with the application's exception controller
        }
    }

    /**
     * Opens the main form of the application and initializes its controller.
     * This method sets the table data, loads images, and displays the main form.
     */
    public static void openMainForm() throws DAOException {
        mainFormInstance = new MainForm();
        mainFormController = new MainFormController(mainFormInstance);
        reloadMainFormTable();
        mainFormController.setImages();
        mainFormController.show();
    }

    /**
     * Refreshes the main form by updating the table data with the latest programs.
     */
    public static void reloadMainFormTable() throws DAOException {
        List<Program> programs = programDAO.getAll();
        mainFormController.setTableData(programs);
    }

    /**
     * Validates a software version string.
     * A valid version string consists of three numeric components separated by dots (e.g., "1.0.0").
     *
     * @param version The version string to validate.
     * @return true if the version string is invalid, false if it is valid.
     */
    public static boolean validateVersion(String version) {
        return !version.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$");
    }

    /**
     * Validates a release date string.
     * A valid release date string follows the ISO date format (e.g., "yyyy-MM-dd")
     * and is not in the past relative to the current date.
     *
     * @param releaseDate The release date string to validate.
     * @return true if the release date string is invalid, false if it is valid.
     */
    public static boolean validateReleaseDate(String releaseDate) {
        if (!releaseDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
            return true; // Invalid format if not matching "yyyy-MM-dd"
        }
        try {
            LocalDate parsedDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate currentDate = LocalDate.now();
            return !parsedDate.isBefore(currentDate); // Release date must not be in the past
        } catch (Exception e) {
            return true; // Any parsing exception results in an invalid date
        }
    }
}
