package team.baymax.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import team.baymax.commons.core.GuiSettings;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the PatientManager.
     *
     * @see Model#getPatientManager() ()
     */
    ReadOnlyListManager<Patient> getPatientManager();

    /**
     * Returns the AppointmentManager.
     *
     * @see Model#getAppointmentManager() ()
     */
    ReadOnlyListManager<Appointment> getAppointmentManager();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of appointments */
    ObservableList<Appointment> getFilteredAppointmentList();

    AppointmentCalendar getAppointmentCalendar();

    /**
     * Returns the user prefs' patient manager file path.
     */
    Path getPatientStorageFilePath();

    /**
     * Returns the user prefs' appointment manager file path.
     */
    Path getAppointmentStorageFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
