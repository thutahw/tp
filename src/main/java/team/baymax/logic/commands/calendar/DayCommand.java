package team.baymax.logic.commands.calendar;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;


public class DayCommand extends Command {

    public static final String COMMAND_WORD = "day";
    public static final String MESSAGE_SUCCESS = "Viewing appointment schedule on %1$s.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the appointment schedule on the chosen day.\n"
            + "Parameters: DAY (must be a positive number between the first and last day of the month).\n"
            + "Example: " + COMMAND_WORD + " 21 ";

    public static final TabId TAB_ID = TabId.SCHEDULE;

    private final Day day;

    public DayCommand(Day day) {
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.setDay(day);
        } catch (DateTimeException ex) {
            throw new CommandException(Day.MESSAGE_CONSTRAINTS);
        }

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        return new CommandResult(String.format(MESSAGE_SUCCESS, date.toString()), getTabId());
    }

    public Day getDay() {
        return day;
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DayCommand // instanceof handles nulls
                && day.equals(((DayCommand) other).day));
    }
}
