package team.baymax.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.baymax.commons.core.LogsCenter;
import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.ReadOnlyUserPrefs;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.storage.appointment.AppointmentManagerStorage;
import team.baymax.storage.patient.PatientManagerStorage;
import team.baymax.storage.userprefs.UserPrefsStorage;

/**
 * Manages storage of Baymax data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PatientManagerStorage patientManagerStorage;
    private AppointmentManagerStorage appointmentManagerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a new {@code StorageManager}
     * @param patientManagerStorage     the patient manager storage
     * @param userPrefsStorage          the user prefs storage
     */
    public StorageManager(PatientManagerStorage patientManagerStorage,
                          AppointmentManagerStorage appointmentManagerStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.patientManagerStorage = patientManagerStorage;
        this.appointmentManagerStorage = appointmentManagerStorage;
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
    public Optional<ReadOnlyListManager<Patient>> readPatients(Path filePath)
            throws DataConversionException, IOException {
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

    // ================ AppointmentManager methods ==============================

    @Override
    public Path getAppointmentManagerStorageFilePath() {
        return patientManagerStorage.getPatientManagerStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager)
            throws DataConversionException, IOException {
        return readAppointments(patientManager, appointmentManagerStorage.getAppointmentManagerStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager, Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return appointmentManagerStorage.readAppointments(patientManager, filePath);
    }

    @Override
    public void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager) throws IOException {
        saveAppointments(appointmentManager, appointmentManagerStorage.getAppointmentManagerStorageFilePath());
    }

    @Override
    public void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        appointmentManagerStorage.saveAppointments(appointmentManager, filePath);
    }

}
