package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_INDEX;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TIME;

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
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;

public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";
    public static final TabId TAB_ID = TabId.SCHEDULE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book.\n"
            + "Parameters: "
            + PREFIX_INDEX + "PATIENT_INDEX "
            + PREFIX_DATETIME + "DATETIME "
            + "(" + "or " + PREFIX_TIME + "TIME " + ") "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 "
            + PREFIX_DURATION + "60 "
            + PREFIX_DESCRIPTION + "Monthly health checkup. "
            + PREFIX_TAG + "DrGoh\n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TIME + "12:30 "
            + PREFIX_DURATION + "60 "
            + PREFIX_DESCRIPTION + "Monthly health checkup. "
            + PREFIX_TAG + "DrGoh\n";

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
     * Creates an @{code AddAppointmentCommand} to add the specified {@code Appointment} to the appointment book.
     * Note that this constructor takes in either a {@code DateTime} or {@code Time}, the {@code DateTime} will be
     * taken if both values are present.
     */
    public AddAppointmentCommand(Index patientIndex, DateTime dateTime, Time time, Duration duration,
                                 Description description, Set<Tag> tags) {
        requireAllNonNull(patientIndex, duration, description, tags);

        assert dateTime != null || time != null : "At least one must be non-null";

        this.patientIndex = patientIndex;
        this.dateTime = dateTime;
        this.time = time;
        this.duration = duration;
        this.description = description;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        assert patientIndex.getZeroBased() < model.getFilteredPatientList().size() : "Patient index should not "
                + "exceed size of filtered list";

        if (patientIndex.getZeroBased() >= model.getFilteredPatientList().size()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());

        DateTime dt;

        // dateTime takes precedence if both dateTime and time are non-null
        if (dateTime != null) {
            dt = dateTime;
        } else {
            Date date = Date.fromCalendar(model.getAppointmentCalendar());
            dt = DateTime.from(date, time);
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
        model.setYear(dt.getYear());
        model.setMonth(dt.getMonth());
        model.setDay(dt.getDay());

        model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(dt.getDate()));

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
