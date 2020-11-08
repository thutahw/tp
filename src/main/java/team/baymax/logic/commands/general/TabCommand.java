package team.baymax.logic.commands.general;

import static java.util.Objects.requireNonNull;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTimeUtil;

public class TabCommand extends Command {

    public static final String COMMAND_WORD_DASHBOARD = "dashboard";
    public static final String COMMAND_WORD_CALENDAR = "calendar";
    public static final String COMMAND_WORD_SCHEDULE = "schedule";
    public static final String COMMAND_WORD_PATIENT = "patient";
    public static final String COMMAND_WORD_APPOINTMENT = "appt";

    public static final String MESSAGE_SUCCESS = "Switched to %1$s tab";
    public static final String MESSAGE_INVALID_TAB = "This tab does not exist.";

    private final Index tabNumber;

    /**
     * Constructs a {@code TabCommand} given a {@code tabNumber}.
     *
     */
    public TabCommand(Index tabNumber) {
        requireNonNull(tabNumber);
        this.tabNumber = tabNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TabId tabId = TabId.valueOf(tabNumber.getOneBased());

        Date today = DateTimeUtil.getCurrentDate();

        if (tabId == null) {
            throw new CommandException(MESSAGE_INVALID_TAB);
        }

        if (tabId == TabId.DASHBOARD || tabId == TabId.SCHEDULE) {
            model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(today));
        } else {
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }

        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tabId), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TabId.valueOf(tabNumber.getOneBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TabCommand // instanceof handles nulls
                && tabNumber.equals(((TabCommand) other).tabNumber));
    }
}
