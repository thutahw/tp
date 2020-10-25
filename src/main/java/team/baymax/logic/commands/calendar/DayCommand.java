package team.baymax.logic.commands.calendar;

import static java.util.Objects.requireNonNull;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.calendar.Date;
import team.baymax.model.calendar.Day;

public class DayCommand extends Command {

    public static final String COMMAND_WORD = "day";
    public static final String MESSAGE_SUCCESS = "Viewing appointment schedule on %1$s.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the appointment schedule on the chosen day.\n"
            + "Parameters: DAY (must be a positive integer between the first and last day of the month).\n"
            + "Example: " + COMMAND_WORD + " 21 ";

    private final Day day;

    public DayCommand(Day day) {
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setDay(day);

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        return new CommandResult(String.format(MESSAGE_SUCCESS, date.toString()), getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(3);
    }
}
