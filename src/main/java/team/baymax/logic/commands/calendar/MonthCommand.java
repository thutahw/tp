package team.baymax.logic.commands.calendar;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

public class MonthCommand extends Command {

    public static final String COMMAND_WORD = "month";
    public static final String MESSAGE_SUCCESS = "Viewing the month %1$s in %2$s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to a particular month and updates the calendar.\n"
            + "Parameters: MONTH (must be a positive number from 1 to 12 ).\n"
            + "Example: " + COMMAND_WORD + " 12 ";
    public static final TabId TAB_ID = TabId.CALENDAR;

    private final Month month;

    public MonthCommand(Month month) {
        this.month = month;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setMonth(month);

        Year year = model.getAppointmentCalendar().getYear();

        return new CommandResult(String.format(MESSAGE_SUCCESS, month, year), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }
}
