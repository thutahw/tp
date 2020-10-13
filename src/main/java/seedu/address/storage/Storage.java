package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.listmanagers.ReadOnlyListManager;
import seedu.address.model.patient.Patient;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.storage.patient.PatientManagerStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;

/**
 * API of the Storage component
 */
public interface Storage extends PatientManagerStorage, UserPrefsStorage {

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

}
