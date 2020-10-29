package team.baymax.storage.appointment;

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
import team.baymax.model.appointment.Appointment;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;


/**
 * A class to access AppointmentManager data stored as a json file on the hard disk.
 */
public class JsonAppointmentManagerStorage implements AppointmentManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentManagerStorage.class);

    private Path filePath;

    public JsonAppointmentManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAppointmentManagerStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager)
            throws DataConversionException {
        return readAppointments(patientManager, filePath);
    }

    /**
     * Similar to {@link #readAppointments(PatientManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager, Path filePath)
            throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableAppointmentManager> jsonAppointmentManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointmentManager.class);
        if (jsonAppointmentManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAppointmentManager.get().toModelType(patientManager));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAppointments(ReadOnlyListManager<Appointment> listManager) throws IOException {
        saveAppointments(listManager, filePath);
    }

    /**
     * Similar to {@link #saveAppointments(ReadOnlyListManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAppointments(ReadOnlyListManager<Appointment> listManager, Path filePath) throws IOException {
        requireNonNull(listManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppointmentManager(listManager), filePath);
    }
}
