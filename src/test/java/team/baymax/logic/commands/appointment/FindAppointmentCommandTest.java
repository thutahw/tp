package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.appointment.FindAppointmentCommand.MESSAGE_APPOINTMENTS_LISTED_SUCCESS;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.appointment.TypicalAppointments.DANIEL_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ELLE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.AppointmentContainsKeywordsPredicate;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.userprefs.UserPrefs;

public class FindAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());
    private Model expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindAppointmentCommand(null));
    }

    @Test
    public void execute_noKeywords_zeroAppointmentsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS, 0);
        AppointmentContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_partialKeyword_correctPatientFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS, 1);
        AppointmentContainsKeywordsPredicate predicate = preparePredicate("blo");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE_APT), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_differentCapitalisationKeyword_onePatientFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS, 1);
        AppointmentContainsKeywordsPredicate predicate = preparePredicate("BLOOD");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE_APT), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_oneKeyword_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS, 2);
        AppointmentContainsKeywordsPredicate predicate = preparePredicate("Dr");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL_APT, ELLE_APT), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS, 2);
        AppointmentContainsKeywordsPredicate predicate = preparePredicate("xray test");
        FindAppointmentCommand command = new FindAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL_APT, ELLE_APT), model.getFilteredAppointmentList());
    }

    @Test
    public void equals() {
        AppointmentContainsKeywordsPredicate firstPredicate =
                new AppointmentContainsKeywordsPredicate(Collections.singletonList("first"));
        AppointmentContainsKeywordsPredicate secondPredicate =
                new AppointmentContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAppointmentCommand findFirstCommand = new FindAppointmentCommand(firstPredicate);
        FindAppointmentCommand findSecondCommand = new FindAppointmentCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAppointmentCommand findFirstCommandCopy = new FindAppointmentCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different keyword -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code AppointmentContainsKeywordsPredicate}.
     */
    private AppointmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AppointmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
