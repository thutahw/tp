package team.baymax.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends PatientManagerStorage, AppointmentManagerStorage, UserPrefsStorage {

    @Override
    Path getUserPrefsFilePath();

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ================ PatientManager methods ==============================
    @Override
    Path getPatientManagerStorageFilePath();

    @Override
    Optional<ReadOnlyListManager<Patient>> readPatients() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyListManager<Patient>> readPatients(Path filePath) throws DataConversionException, IOException;

    @Override
    void savePatients(ReadOnlyListManager<Patient> patientManager) throws IOException;

    @Override
    void savePatients(ReadOnlyListManager<Patient> patientManager, Path filePath) throws IOException;

    // ================ PatientManager methods ==============================
    @Override
    Path getAppointmentManagerStorageFilePath();

    @Override
    Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager)
            throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager, Path filePath)
            throws DataConversionException, IOException;

    @Override
    void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager) throws IOException;

    @Override
    void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager, Path filePath) throws IOException;

}
