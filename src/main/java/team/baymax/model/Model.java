package team.baymax.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.baymax.commons.core.GuiSettings;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.ReadOnlyUserPrefs;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

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

    Patient getPatient(Nric nric);

    Patient getPatient(Name name);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    //============= AppointmentManager ================

    /**
     * Returns the AppointmentManager
     * @return
     */
    ReadOnlyListManager<Appointment> getAppointmentManager();

    /**
     * Replaces AppointmentManager data with the data in {@code AppointmentManager}
     */
    void setAppointmentManager(ReadOnlyListManager<Appointment> appointmentmanager);

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Returns true if there is an appointment existing in the manager that clashes with
     * {@code appointment} in time.
     * @param appointment
     * @param toExclude the appointment to exclude for checking clashes
     * @return
     */
    boolean doesAppointmentClash(Appointment appointment, Appointment toExclude);

    /**
     * Deletes the given appointment.
     * The appointment must exist in the appointment book.
     */
    void deleteAppointment(Appointment target);

    /**
     * Deletes all appointments belonging to the specified patient.
     * {@code patient} must exist in the appointment book.
     */
    void clearAllAppointmentsOfPatient(Patient patient);

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

    //=========== CalendarManager ========

    CalendarManager getCalendarManager();

    AppointmentCalendar getAppointmentCalendar();

    void setDate(Date date);

    void setDay(Day day);

    void setMonth(Month month);

    void setYear(Year year);

    void resetCalendar();

    Appointment findAppointmentByPredicate(Predicate<Appointment> predicate);

    //============= utils ================
    void resetAllListManagers();
}
