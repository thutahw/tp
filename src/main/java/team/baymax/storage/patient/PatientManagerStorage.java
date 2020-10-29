package team.baymax.storage.patient;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;

public interface PatientManagerStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getPatientManagerStorageFilePath();

    /**
     * Returns PatientManager data as a {@link ReadOnlyListManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyListManager<Patient>> readPatients() throws DataConversionException, IOException;

    /**
     * @see #readPatients()
     */
    Optional<ReadOnlyListManager<Patient>> readPatients(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyListManager} to the storage.
     * @param patientManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePatients(ReadOnlyListManager<Patient> patientManager) throws IOException;

    /**
     * @see #savePatients(ReadOnlyListManager)
     */
    void savePatients(ReadOnlyListManager<Patient> patientManager, Path filePath) throws IOException;

}
