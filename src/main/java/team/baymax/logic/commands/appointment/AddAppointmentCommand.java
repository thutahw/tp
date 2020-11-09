package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Optional;
import java.util.Set;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;

public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";
    public static final TabId TAB_ID = TabId.SCHEDULE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book.\n"
            + "Parameters: PATIENT_INDEX (or " + PREFIX_NRIC + "NRIC) "
            + PREFIX_DATETIME + "DATETIME "
            + "(" + "or " + PREFIX_TIME + "TIME " + ") "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "For example, " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 "
            + PREFIX_DURATION + "60 "
            + PREFIX_DESCRIPTION + "Monthly health checkup. "
            + PREFIX_TAG + "Xray\n"
            + "Alternatively, " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S0123456A "
            + PREFIX_TIME + "12:30 "
            + PREFIX_DURATION + "60 "
            + PREFIX_DESCRIPTION + "Monthly health checkup. "
            + PREFIX_TAG + "Xray\n";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
            + "appointment book";
    public static final String MESSAGE_CLASH_APPOINTMENT = "This appointment clashes with an existing appointment";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "The patient at the specified index does not exist.";
    public static final String MESSAGE_INVALID_NRIC = "The patient with the specified nric does not exist.";
    public static final String MESSAGE_INDEX_AND_NRIC_BOTH_EMPTY = "The patient index and NRIC should not "
            + "be both empty.";
    public static final String MESSAGE_DATETIME_AND_TIME_BOTH_EMPTY = "The datetime and time should not "
            + "be both empty.";

    public static final String MESSAGE_INVALID_DURATION = "The appointment cannot last to the next day.";

    private final Optional<Index> patientIndex;
    private final Optional<Nric> patientNric;
    private final Optional<DateTime> dateTime;
    private final Optional<Time> time;
    private final Duration duration;
    private final Description description;
    private final Set<Tag> tags;

    /**
     * Creates an @{code AddAppointmentCommand} to add the specified {@code Appointment} to the appointment book.
     * Note that this constructor takes in either a {@code DateTime} or {@code Time}, the {@code DateTime} will be
     * taken if both values are present.
     */
    public AddAppointmentCommand(Optional<Index> patientIndex, Optional<Nric> patientNric, Optional<DateTime> dateTime,
                                 Optional<Time> time, Duration duration,
                                 Description description, Set<Tag> tags) {

        requireAllNonNull(patientIndex, duration, description, tags);

        this.patientIndex = patientIndex;
        this.patientNric = patientNric;
        this.dateTime = dateTime;
        this.time = time;
        this.duration = duration;
        this.description = description;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient patient;
        DateTime dt;

        assert patientIndex.isPresent() || patientNric.isPresent() : "Patient index or NRIC should not be both empty.";
        assert dateTime.isPresent() || time.isPresent() : "Datetime or time should not be both empty.";

        if (patientIndex.isPresent()) {
            if (patientIndex.get().getZeroBased() >= model.getFilteredPatientList().size()) {
                throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
            }
            patient = model.getFilteredPatientList().get(patientIndex.get().getZeroBased());
        } else {
            try {
                patient = model.getPatient(patientNric.get());
            } catch (ElementNotFoundException e) {
                throw new CommandException(MESSAGE_INVALID_NRIC);
            }
        }

        // dateTime takes precedence if both dateTime and time are non-null
        if (dateTime.isPresent()) {
            dt = dateTime.get();
        } else {
            Date date = Date.fromCalendar(model.getAppointmentCalendar());
            dt = DateTime.from(date, time.get());
        }

        if (dt.extendsUntilNextDay(duration)) {
            throw new CommandException(MESSAGE_INVALID_DURATION);
        }

        Appointment toAdd = new Appointment(patient, dt, duration, description, tags,
                false);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        if (model.doesAppointmentClash(toAdd, null)) {
            throw new CommandException(MESSAGE_CLASH_APPOINTMENT);
        }

        model.addAppointment(toAdd);

        // switches calendar to the day of the appointment
        model.setDate(dt.getDate());

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
                && patientNric.equals(((AddAppointmentCommand) other).patientNric)
                && dateTime.equals(((AddAppointmentCommand) other).dateTime)
                && time.equals(((AddAppointmentCommand) other).time)
                && description.equals(((AddAppointmentCommand) other).description)
                && tags.equals(((AddAppointmentCommand) other).tags));
    }
}
