package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;

public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";
    public static final TabId TAB_ID = TabId.SCHEDULE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book.\n"
            + "Parameters: "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 "
            + PREFIX_DURATION + "60 "
            + PREFIX_DESCRIPTION + "Removal of braces. "
            + PREFIX_TAG + "DrGoh ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
            + "appointment book";
    public static final String MESSAGE_CLASH_APPOINTMENT = "This appointment clashes with an existing appointment";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "The patient at the specified index does not exist.";

    private final Index patientIndex;
    private final DateTime dateTime;
    private final Time time;
    private final Duration duration;
    private final Description description;
    private final Set<Tag> tags;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Index patientIndex, DateTime dateTime, Duration duration, Set<Tag> tags,
                                 Description description) {
        requireAllNonNull(patientIndex, dateTime, duration, description, tags);
        this.patientIndex = patientIndex;
        this.dateTime = dateTime;
        this.duration = duration;
        this.description = description;
        this.tags = tags;
        this.time = null;
    }

    public AddAppointmentCommand(Index patientIndex, Time time, Duration duration, Set<Tag> tags,
                                 Description description) {
        requireAllNonNull(patientIndex, time, duration, description, tags);
        this.patientIndex = patientIndex;
        this.time = time;
        this.duration = duration;
        this.description = description;
        this.tags = tags;
        this.dateTime = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (patientIndex.getZeroBased() >= model.getFilteredPatientList().size()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());

        DateTime dt;

        if (dateTime == null) {
            Date date = Date.fromCalendar(model.getAppointmentCalendar());
            dt = DateTime.from(date, time);
        } else {
            dt = dateTime;
        }

        Appointment toAdd = new Appointment(patient, dt, duration, description, tags,
                AppointmentStatus.UPCOMING);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        if (model.doesAppointmentClash(toAdd)) {
            throw new CommandException(MESSAGE_CLASH_APPOINTMENT);
        }

        model.addAppointment(toAdd);

        // switches calendar to the day of the appointment
        model.setYear(dateTime.getYear());
        model.setMonth(dateTime.getMonth());
        model.setDay(dateTime.getDay());


        model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(dateTime.getDate()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && patientIndex.equals(((AddAppointmentCommand) other).patientIndex)
                && dateTime.equals(((AddAppointmentCommand) other).dateTime)
                && description.equals(((AddAppointmentCommand) other).description)
                && tags.equals(((AddAppointmentCommand) other).tags));
    }
}
