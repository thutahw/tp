package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class CommandResultTest {

    private Index tabNumber = Index.fromOneBased(1);

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback", tabNumber);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", tabNumber)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false,
                false, tabNumber)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different", tabNumber)));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true,
                false, tabNumber)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false,
                true, tabNumber)));

        // different tabId value -> return false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                Index.fromOneBased(2))));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback", tabNumber);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback", tabNumber).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different", tabNumber).hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", true, false, tabNumber).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, true, tabNumber).hashCode());

        // different tabId value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false,
                        Index.fromOneBased(2)).hashCode());
    }
}
