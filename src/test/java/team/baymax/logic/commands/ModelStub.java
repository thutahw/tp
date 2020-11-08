package team.baymax.logic.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.baymax.commons.core.GuiSettings;
import team.baymax.model.Model;
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
import team.baymax.testutil.appointment.AppointmentBuilder;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getPatientStorageFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAppointmentStorageFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPatientStorageFilePath(Path patientStorageFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointmentStorageFilePath(Path appointmentStorageFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPatient(Patient patient) {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void addAppointment(Appointment appointment) {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void setPatientManager(ReadOnlyListManager<Patient> patientManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointmentManager(ReadOnlyListManager<Appointment> appointmentManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyListManager<Patient> getPatientManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyListManager<Appointment> getAppointmentManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPatient(Patient patient) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean doesAppointmentClash(Appointment appointment, Appointment toExclude) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePatient(Patient target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAppointment(Appointment target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearAllAppointmentsOfPatient(Patient patient) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Patient getPatient(Nric nric) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Patient getPatient(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(AppointmentBuilder.DEFAULT_PATIENT);

        return FXCollections.observableArrayList(patients);
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CalendarManager getCalendarManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public AppointmentCalendar getAppointmentCalendar() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDate(Date date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDay(Day day) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMonth(Month month) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setYear(Year year) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetCalendar() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Appointment findAppointmentByPredicate(Predicate<Appointment> predicate) {
        return null;
    }

    @Override
    public void resetAllListManagers() {
        throw new AssertionError("This method should not be called.");
    }
}
