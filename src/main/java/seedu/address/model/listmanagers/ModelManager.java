package seedu.address.model.listmanagers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AppointmentManager;
import seedu.address.model.Model;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Represents the in-memory model of the appointment book data.
 * Contains a filtered list of unique patients.
 * Contains a filtered list of unique appointments.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PatientManager patientManager;
    private final AppointmentManager appointmentManager;
    private final UserPrefs userPrefs;

    private final FilteredList<Patient> filteredPatientsList;
    private final FilteredList<Appointment> filteredAppointmentsList;

    /**
     * Initializes a ModelManager with the given appointmentBook and userPrefs.
     */
    public ModelManager(ReadOnlyListManager<Patient> patientManager,
                        ReadOnlyListManager<Appointment> appointmentManager,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(patientManager, appointmentManager, userPrefs);

        logger.fine("Initializing with Baymax: " + patientManager + " and user prefs " + userPrefs);

        this.patientManager = new PatientManager(patientManager);
        this.appointmentManager = new AppointmentManager(appointmentManager);

        this.userPrefs = new UserPrefs(userPrefs);

        filteredPatientsList = new FilteredList<>(this.patientManager.getReadOnlyList());
        filteredAppointmentsList = new FilteredList<>(this.appointmentManager.getReadOnlyList());
    }

    public ModelManager() {
        this(new PatientManager(), new AppointmentManager(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPatientStorageFilePath() {
        return userPrefs.getPatientStorageFilePath();
    }


    @Override
    public Path getAppointmentStorageFilePath() {
        return userPrefs.getAppointmentStorageFilePath();
    }

    @Override
    public void setPatientStorageFilePath(Path patientStorageFilePath) {
        requireNonNull(patientStorageFilePath);
        userPrefs.setPatientStorageFilePath(patientStorageFilePath);
    }

    @Override
    public void setAppointmentStorageFilePath(Path appointmentStorageFilePath) {
        requireNonNull(appointmentStorageFilePath);
        userPrefs.setAppointmentStorageFilePath(appointmentStorageFilePath);
    }

    //=========== PatientManager ================================================================================

    @Override
    public void setPatientManager(ReadOnlyListManager<Patient> patientManager) {
        this.patientManager.resetData(patientManager);
    }

    @Override
    public ReadOnlyListManager<Patient> getPatientManager() {
        return patientManager;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patientManager.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        patientManager.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        patientManager.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);
        patientManager.setPatient(target, editedPatient);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        System.out.println("In model.getFilteredPatientlist()");
        System.out.println(filteredPatientsList);
        return filteredPatientsList;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatientsList.setPredicate(predicate);
    }


    //=========== AppointmentManager ================================================================================

    @Override
    public void setAppointmentManager(ReadOnlyListManager<Appointment> appointmentManager) {
        this.appointmentManager.resetData(appointmentManager);
    }

    @Override
    public ReadOnlyListManager<Appointment> getAppointmentManager() {
        return appointmentManager;
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointmentManager.hasAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        appointmentManager.removeAppointment(target);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        appointmentManager.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);
        appointmentManager.setAppointment(target, editedAppointment);
    }

    //=========== Filtered Appointment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        System.out.println("In model.getFilteredAppointmentlist()");
        System.out.println(filteredAppointmentsList);
        return filteredAppointmentsList;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointmentsList.setPredicate(predicate);
    }

    @Override
    public void resetAllListManagers() {
        this.patientManager.resetData(new PatientManager());
        this.appointmentManager.resetData(new AppointmentManager());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && patientManager.equals(other.patientManager)
                && filteredPatientsList.equals(other.filteredPatientsList)
                && appointmentManager.equals(other.appointmentManager)
                && filteredAppointmentsList.equals(other.filteredAppointmentsList);
    }

}
