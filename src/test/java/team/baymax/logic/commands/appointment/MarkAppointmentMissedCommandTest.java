package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.assertAppointmentCommandFailure;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.showAppointmentAtIndex;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_SECOND_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME2;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_ALICE;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_BENSON;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_GEORGE;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_HANSON;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.appointment.AppointmentBuilder;

public class MarkAppointmentMissedCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());
    private Model expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentMissedCommand(null));
    }

    @Test
    public void constructor_nullDatetime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentMissedCommand(null, FIRST_NAME_ALICE));
    }

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentMissedCommand(DATETIME1, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentMissedCommand markAppointmentDoneCommand =
                new MarkAppointmentMissedCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MarkAppointmentMissedCommand.MESSAGE_MARK_AS_MISSED_SUCCESS,
                appt);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appt).withIsMissed(true).build();

        expectedModel.setAppointment(appt, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                outOfBoundIndex);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);

        Appointment appt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        MarkAppointmentMissedCommand markAppointmentDoneCommand =
                new MarkAppointmentMissedCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MarkAppointmentMissedCommand.MESSAGE_MARK_AS_MISSED_SUCCESS,
                appt);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appt).withIsMissed(true).build();

        expectedModel.setAppointment(appt, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;

        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(outOfBoundIndex);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateTimeAndNameUnfilteredList_success() {
        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                appointmentChosen.getDateTime(), appointmentChosen.getPatient().getName());

        String expectedMessage = String.format(MarkAppointmentMissedCommand.MESSAGE_MARK_AS_MISSED_SUCCESS,
                appointmentChosen);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appointmentChosen)
                .withIsMissed(true).build();

        expectedModel.setAppointment(appointmentChosen, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDatetimeAndInvalidNameUnfilteredList_throwsCommandException() {
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                DATETIME1, FIRST_NAME_HANSON);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }


    @Test
    public void execute_invalidDatetimeUnfilteredList_throwsCommandException() {
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                DATETIME1, FIRST_NAME_ALICE);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Appointment existingAppt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                existingAppt.getDateTime(), FIRST_NAME_HANSON);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_validDateTimeAndNameFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);

        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                appointmentChosen.getDateTime(), appointmentChosen.getPatient().getName());

        String expectedMessage = String.format(MarkAppointmentMissedCommand.MESSAGE_MARK_AS_MISSED_SUCCESS,
                appointmentChosen);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appointmentChosen)
                .withIsMissed(true).build();

        expectedModel.setAppointment(appointmentChosen, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateTimeAndNameFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;

        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
        MarkAppointmentMissedCommand markAppointmentDoneCommand = new MarkAppointmentMissedCommand(
                DATETIME1, FIRST_NAME_GEORGE);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        MarkAppointmentMissedCommand markDoneCommand1 = new MarkAppointmentMissedCommand(INDEX_FIRST_APPOINTMENT);
        MarkAppointmentMissedCommand markDoneCommand2 = new MarkAppointmentMissedCommand(INDEX_SECOND_APPOINTMENT);
        MarkAppointmentMissedCommand markDoneCommand3 = new MarkAppointmentMissedCommand(DATETIME1, FIRST_NAME_ALICE);
        MarkAppointmentMissedCommand markDoneCommand4 = new MarkAppointmentMissedCommand(DATETIME2, FIRST_NAME_BENSON);

        // same object -> returns True
        assertTrue(markDoneCommand1.equals(markDoneCommand1));

        // same values (index constructor) -> returns True
        MarkAppointmentMissedCommand markDoneCommand1Copy = new MarkAppointmentMissedCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(markDoneCommand1.equals(markDoneCommand1Copy));

        // same values (dateTime and name constructor) -> returns True
        MarkAppointmentMissedCommand markDoneCommand3Copy =
                new MarkAppointmentMissedCommand(DATETIME1, FIRST_NAME_ALICE);
        assertTrue(markDoneCommand3.equals(markDoneCommand3Copy));

        // different types -> returns False
        assertFalse(markDoneCommand1.equals(1));

        // null -> returns False
        assertFalse(markDoneCommand1.equals(null));

        // different details -> returns False
        assertFalse(markDoneCommand1.equals(markDoneCommand2));
        assertFalse(markDoneCommand1.equals(markDoneCommand3));
        assertFalse(markDoneCommand3.equals(markDoneCommand4));
    }
}
