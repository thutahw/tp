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

public class MarkAppointmentDoneCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());
    private Model expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentDoneCommand(null));
    }

    @Test
    public void constructor_nullDatetime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentDoneCommand(null, FIRST_NAME_ALICE));
    }

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAppointmentDoneCommand(DATETIME1, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MarkAppointmentDoneCommand.MESSAGE_MARK_AS_DONE_SUCCESS,
                appt);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appt).withIsMissed(false).build();

        expectedModel.setAppointment(appt, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                outOfBoundIndex);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);

        Appointment appt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MarkAppointmentDoneCommand.MESSAGE_MARK_AS_DONE_SUCCESS,
                appt);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appt).withIsMissed(false).build();

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
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(outOfBoundIndex);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateTimeAndNameUnfilteredList_success() {
        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                appointmentChosen.getDateTime(), appointmentChosen.getPatient().getName());

        String expectedMessage = String.format(MarkAppointmentDoneCommand.MESSAGE_MARK_AS_DONE_SUCCESS,
                appointmentChosen);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appointmentChosen)
                .withIsMissed(false).build();

        expectedModel.setAppointment(appointmentChosen, markedAsDoneAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsDoneAppointment));

        CommandTestUtil.assertCommandSuccess(markAppointmentDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDatetimeAndInvalidNameUnfilteredList_throwsCommandException() {
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                DATETIME1, FIRST_NAME_HANSON);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }


    @Test
    public void execute_invalidDatetimeUnfilteredList_throwsCommandException() {
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                DATETIME1, FIRST_NAME_ALICE);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Appointment existingAppt = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                existingAppt.getDateTime(), FIRST_NAME_HANSON);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_validDateTimeAndNameFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_SECOND_APPOINTMENT);

        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                appointmentChosen.getDateTime(), appointmentChosen.getPatient().getName());

        String expectedMessage = String.format(MarkAppointmentDoneCommand.MESSAGE_MARK_AS_DONE_SUCCESS,
                appointmentChosen);
        Appointment markedAsDoneAppointment = new AppointmentBuilder(appointmentChosen)
                .withIsMissed(false).build();

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
        MarkAppointmentDoneCommand markAppointmentDoneCommand = new MarkAppointmentDoneCommand(
                DATETIME1, FIRST_NAME_GEORGE);

        assertAppointmentCommandFailure(markAppointmentDoneCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        MarkAppointmentDoneCommand markDoneCommand1 = new MarkAppointmentDoneCommand(INDEX_FIRST_APPOINTMENT);
        MarkAppointmentDoneCommand markDoneCommand2 = new MarkAppointmentDoneCommand(INDEX_SECOND_APPOINTMENT);
        MarkAppointmentDoneCommand markDoneCommand3 = new MarkAppointmentDoneCommand(DATETIME1, FIRST_NAME_ALICE);
        MarkAppointmentDoneCommand markDoneCommand4 = new MarkAppointmentDoneCommand(DATETIME2, FIRST_NAME_BENSON);

        // same object -> returns True
        assertTrue(markDoneCommand1.equals(markDoneCommand1));

        // same values (index constructor) -> returns True
        MarkAppointmentDoneCommand markDoneCommand1Copy = new MarkAppointmentDoneCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(markDoneCommand1.equals(markDoneCommand1Copy));

        // same values (dateTime and name constructor) -> returns True
        MarkAppointmentDoneCommand markDoneCommand3Copy = new MarkAppointmentDoneCommand(DATETIME1, FIRST_NAME_ALICE);
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
