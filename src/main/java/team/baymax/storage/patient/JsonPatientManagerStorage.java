package team.baymax.storage.patient;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.baymax.commons.core.LogsCenter;
import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.commons.exceptions.IllegalValueException;
import team.baymax.commons.util.FileUtil;
import team.baymax.commons.util.JsonUtil;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;

/**
 * A class to access PatientManager data stored as a json file on the hard disk.
 */
public class JsonPatientManagerStorage implements PatientManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPatientManagerStorage.class);

    private Path filePath;

    public JsonPatientManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getPatientManagerStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyListManager<Patient>> readPatients() throws DataConversionException {
        return readPatients(filePath);
    }

    /**
     * Similar to {@link #readPatients()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyListManager<Patient>> readPatients(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePatientManager> jsonPatientManager = JsonUtil.readJsonFile(
                filePath, JsonSerializablePatientManager.class);
        if (jsonPatientManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPatientManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePatients(ReadOnlyListManager<Patient> listManager) throws IOException {
        savePatients(listManager, filePath);
    }

    /**
     * Similar to {@link #savePatients(ReadOnlyListManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void savePatients(ReadOnlyListManager<Patient> listManager, Path filePath) throws IOException {
        requireNonNull(listManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePatientManager(listManager), filePath);
    }
}
