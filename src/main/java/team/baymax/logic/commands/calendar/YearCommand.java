package team.baymax.logic.commands.calendar;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;


public class YearCommand extends Command {

    public static final String COMMAND_WORD = "year";
    public static final String MESSAGE_SUCCESS = "Switched to %1$s in %2$s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to a particular year and updates the calendar.\n"
            + "Parameters: YEAR (must be a positive number from year 2000 onwards).\n"
            + "Example: " + COMMAND_WORD + " 2020 ";
    public static final TabId TAB_ID = TabId.CALENDAR;

    private final Year year;

    public YearCommand(Year year) {
        this.year = year;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.setYear(year);
        } catch (DateTimeException ex) {
            throw new CommandException(Year.MESSAGE_CONSTRAINTS);
        }

        Month month = model.getAppointmentCalendar().getMonth();

        return new CommandResult(String.format(MESSAGE_SUCCESS, month, year), getTabId());
    }

    public Year getYear() {
        return year;
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearCommand // instanceof handles nulls
                && year.equals(((YearCommand) other).year));
    }
}
