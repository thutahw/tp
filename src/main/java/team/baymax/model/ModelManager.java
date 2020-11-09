package team.baymax.model;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.baymax.commons.core.GuiSettings;
import team.baymax.commons.core.LogsCenter;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.ReadOnlyUserPrefs;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;


/**
 * Represents the in-memory model of the appointment book data.
 * Contains a filtered list of unique patients.
 * Contains a filtered list of unique appointments.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PatientManager patientManager;
    private final AppointmentManager appointmentManager;
    private final CalendarManager calendarManager;
    private final UserPrefs userPrefs;

    private final FilteredList<Patient> filteredPatientsList;
    private final FilteredList<Appointment> filteredAppointmentsList;

    /**
     * Initializes a ModelManager with the given appointmentBook and userPrefs.
     */
    public ModelManager(ReadOnlyListManager<Patient> patientManager,
                        ReadOnlyListManager<Appointment> appointmentManager,
                        ReadOnlyUserPrefs userPrefs,
                        CalendarManager calendarManager) {
        super();
        requireAllNonNull(patientManager, appointmentManager, userPrefs);

        logger.fine("Initializing with Baymax: " + patientManager + " and user prefs " + userPrefs);

        this.patientManager = new PatientManager(patientManager);
        this.appointmentManager = new AppointmentManager(appointmentManager);

        this.calendarManager = calendarManager;

        this.userPrefs = new UserPrefs(userPrefs);

        filteredPatientsList = new FilteredList<>(this.patientManager.getReadOnlyList());
        filteredAppointmentsList = new FilteredList<>(this.appointmentManager.getReadOnlyList());
    }

    public ModelManager() {
        this(new PatientManager(), new AppointmentManager(), new UserPrefs(), new CalendarManager());
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
        appointmentManager.updatePatientAppointments(target, editedPatient);
    }

    @Override
    public Patient getPatient(Nric nric) throws ElementNotFoundException {
        return patientManager.getPatient(nric);
    }

    @Override
    public Patient getPatient(Name name) throws ElementNotFoundException {
        return patientManager.getPatient(name);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
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
    public boolean doesAppointmentClash(Appointment appointment, Appointment toExclude) {
        requireNonNull(appointment);
        return appointmentManager.doesAppointmentClash(appointment, toExclude);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        appointmentManager.removeAppointment(target);
    }

    @Override
    public void clearAllAppointmentsOfPatient(Patient patient) {
        appointmentManager.clearAllAppointmentsOfPatient(patient);
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

    @Override
    public Appointment findAppointmentByPredicate(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        return appointmentManager.getApptByPred(predicate);
    }

    //=========== Filtered Appointment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointmentsList;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointmentsList.setPredicate(predicate);
    }

    //=========== CalendarManager ================================================================================

    @Override
    public CalendarManager getCalendarManager() {
        return calendarManager;
    }

    @Override
    public AppointmentCalendar getAppointmentCalendar() {
        return calendarManager.getAppointmentCalendar();
    }

    @Override
    public void setDate(Date date) {
        requireNonNull(date);
        calendarManager.setDate(date);
    }

    @Override
    public void setDay(Day day) {
        requireNonNull(day);
        calendarManager.setDay(day);
    }

    @Override
    public void setMonth(Month month) {
        requireNonNull(month);
        calendarManager.setMonth(month);
    }

    @Override
    public void setYear(Year year) {
        requireNonNull(year);
        calendarManager.setYear(year);
    }

    @Override
    public void resetCalendar() {
        calendarManager.resetCalendar(new CalendarManager());
    }

    //=========== utils ================================================================================

    @Override
    public void resetAllListManagers() {
        patientManager.resetData(new PatientManager());
        appointmentManager.resetData(new AppointmentManager());
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
                && appointmentManager.equals(other.appointmentManager)
                && calendarManager.equals(other.calendarManager)
                && filteredPatientsList.equals(other.filteredPatientsList)
                && filteredAppointmentsList.equals(other.filteredAppointmentsList);
    }

}
