package team.baymax.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.baymax.model.util.TabId;

public class CommandResultTest {

    private TabId tabId = TabId.DASHBOARD;

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback", tabId);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", tabId)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false,
                false, tabId)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different", tabId)));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true,
                false, tabId)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false,
                true, tabId)));

        // different tabId value -> return false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                TabId.SCHEDULE)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback", tabId);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback", tabId).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different", tabId).hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", true, false, tabId).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, true, tabId).hashCode());

        // different tabId value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false,
                        TabId.SCHEDULE).hashCode());
    }
}
