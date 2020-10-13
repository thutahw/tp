package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.listmanagers.ReadOnlyListManager;
import seedu.address.model.patient.Patient;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.storage.patient.PatientManagerStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;

/**
 * Manages storage of AppointmentBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PatientManagerStorage patientManagerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a new {@code StorageManager}
     * @param patientManagerStorage     the patient manager storage
     * @param userPrefsStorage          the user prefs storage
     */
    public StorageManager(PatientManagerStorage patientManagerStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.patientManagerStorage = patientManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Patientmanager methods ==============================

    @Override
    public Path getPatientManagerStorageFilePath() {
        return patientManagerStorage.getPatientManagerStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyListManager<Patient>> readPatients() throws DataConversionException, IOException {
        return readPatients(patientManagerStorage.getPatientManagerStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyListManager<Patient>> readPatients(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return patientManagerStorage.readPatients(filePath);
    }


    @Override
    public void savePatients(ReadOnlyListManager<Patient> patientManager) throws IOException {
        savePatients(patientManager, patientManagerStorage.getPatientManagerStorageFilePath());
    }

    @Override
    public void savePatients(ReadOnlyListManager<Patient> patientManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        patientManagerStorage.savePatients(patientManager, filePath);
    }

}
