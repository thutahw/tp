package team.baymax.model.userprefs;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Objects;

import team.baymax.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {
    private static final Path DEFAULT_STORAGE_PATH = Path.of("data");

    private GuiSettings guiSettings = new GuiSettings();

    private Path patientStorageFilePath = DEFAULT_STORAGE_PATH.resolve("patients.json");
    private Path appointmentStorageFilePath = DEFAULT_STORAGE_PATH.resolve("appointments.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setPatientStorageFilePath(newUserPrefs.getPatientStorageFilePath());
        setAppointmentStorageFilePath(newUserPrefs.getAppointmentStorageFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPatientStorageFilePath() {
        return patientStorageFilePath;
    }

    public Path getAppointmentStorageFilePath() {
        return appointmentStorageFilePath;
    }

    public void setPatientStorageFilePath(Path patientStorageFilePath) {
        requireNonNull(patientStorageFilePath);
        this.patientStorageFilePath = patientStorageFilePath;
    }

    public void setAppointmentStorageFilePath(Path appointmentStorageFilePath) {
        requireNonNull(appointmentStorageFilePath);
        this.appointmentStorageFilePath = appointmentStorageFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && patientStorageFilePath.equals(o.patientStorageFilePath)
                && appointmentStorageFilePath.equals(o.appointmentStorageFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, patientStorageFilePath, appointmentStorageFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nPatient data file location : " + patientStorageFilePath);
        sb.append("\nAppointment data file location : " + appointmentStorageFilePath);
        return sb.toString();
    }

}
