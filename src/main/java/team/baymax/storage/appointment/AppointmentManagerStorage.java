package team.baymax.storage.appointment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;

public interface AppointmentManagerStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getAppointmentManagerStorageFilePath();

    /**
     * Returns AppointmentManager data as a {@link ReadOnlyListManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager)
            throws DataConversionException, IOException;

    /**
     * @see #readAppointments(PatientManager)
     */
    Optional<ReadOnlyListManager<Appointment>> readAppointments(PatientManager patientManager, Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyListManager} to the storage.
     * @param appointmentManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager) throws IOException;

    /**
     * @see #saveAppointments(ReadOnlyListManager)
     */
    void saveAppointments(ReadOnlyListManager<Appointment> appointmentManager, Path filePath) throws IOException;

}
