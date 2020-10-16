package team.baymax.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.baymax.commons.core.GuiSettings;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.listmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.ReadOnlyUserPrefs;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' patient storage file path.
     */
    Path getPatientStorageFilePath();

    /**
     * Returns the user prefs' appointment storage file path.
     */
    Path getAppointmentStorageFilePath();

    /**
     * Sets the user prefs' appointment book file path.
     */
    void setPatientStorageFilePath(Path patientStorageFilePath);

    void setAppointmentStorageFilePath(Path appointmentStorageFilePath);

    //============= PatientManager ================

    /**
     * Returns the PatientManager
     * @return
     */
    ReadOnlyListManager<Patient> getPatientManager();

    /**
     * Replaces PatientManager data with the data in {@code patientManager}
     */
    void setPatientManager(ReadOnlyListManager<Patient> patientManager);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the appointment book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Deletes the given patient.
     * The patient must exist in the appointment book.
     */
    void deletePatient(Patient target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the appointment book.
     */
    void addPatient(Patient patient);

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the appointment book.
     * The patient identity of {@code editedPatient} must not be the same as another existing patient in the
     * appointment book.
     */
    void setPatient(Patient target, Patient editedPatient);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    //============= AppointmentManager ================

    /**
     * Returns the PatientManager
     * @return
     */
    ReadOnlyListManager<Appointment> getAppointmentManager();

    /**
     * Replaces PatientManager data with the data in {@code patientManager}
     */
    void setAppointmentManager(ReadOnlyListManager<Appointment> appointmentmanager);

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Deletes the given appointment.
     * The appointment must exist in the appointment book.
     */
    void deleteAppointment(Appointment target);

    /**
     * Adds the given appointment.
     * {@code appointment} must not already exist in the appointment book.
     */
    void addAppointment(Appointment appointment);

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}.
     * {@code target} must exist in the appointment book.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing patient in the
     * appointment book.
     */
    void setAppointment(Appointment target, Appointment editedAppointment);


    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    //============= utils ================
    void resetAllListManagers();
}
