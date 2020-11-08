package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_2;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME2;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_SECOND_PATIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.ModelStub;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.Description;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;
import team.baymax.testutil.appointment.AppointmentBuilder;

public class AddAppointmentCommandTest {

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, null,
                null, null, null, null, null));
    }

    @Test
    public void executeAddAppointmentByIndex_appointmentAcceptedByModel_addSuccessful() throws Exception {
        Appointment validAppointment = new AppointmentBuilder().build();

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                Optional.ofNullable(Index.fromOneBased(1)),
                Optional.empty(),
                Optional.ofNullable(validAppointment.getDateTime()),
                Optional.ofNullable(validAppointment.getTime()),
                validAppointment.getDuration(),
                validAppointment.getDescription(), validAppointment.getTags());

        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();

        CommandResult commandResult = addAppointmentCommand.execute(modelStub);

        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Appointment validAppointment = new AppointmentBuilder().build();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                Optional.ofNullable(Index.fromOneBased(1)),
                Optional.empty(),
                Optional.ofNullable(validAppointment.getDateTime()),
                Optional.ofNullable(validAppointment.getTime()),
                validAppointment.getDuration(),
                validAppointment.getDescription(), validAppointment.getTags());

        ModelStub modelStub = new ModelStubWithAppointment(validAppointment);

        assertThrows(CommandException.class, AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT, () ->
                addAppointmentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddAppointmentCommand firstCommand = new AddAppointmentCommand(Optional.ofNullable(INDEX_FIRST_PATIENT),
                Optional.empty(), Optional.ofNullable(DATETIME1), Optional.empty(),
                new Duration(60), new Description(VALID_DESCRIPTION_1), new HashSet<>());
        AddAppointmentCommand secondCommand = new AddAppointmentCommand(Optional.ofNullable(INDEX_SECOND_PATIENT),
                Optional.empty(), Optional.ofNullable(DATETIME2), Optional.empty(),
                new Duration(60), new Description(VALID_DESCRIPTION_2), new HashSet<>());

        // same object -> returns True
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns True
        AddAppointmentCommand firstCommandCopy = new AddAppointmentCommand(Optional.ofNullable(INDEX_FIRST_PATIENT),
                Optional.empty(), Optional.ofNullable(DATETIME1), Optional.empty(),
                new Duration(60), new Description(VALID_DESCRIPTION_1), new HashSet<>());
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns False
        assertFalse(firstCommand.equals(1));

        // null -> returns False
        assertFalse(firstCommand.equals(null));

        // different details -> returns False
        assertFalse(firstCommand.equals(secondCommand));

    }

    /**
     * A Model stub that contains a single appointment.
     */
    private class ModelStubWithAppointment extends ModelStub {
        private final Appointment appointment;

        ModelStubWithAppointment(Appointment appointment) {
            requireNonNull(appointment);
            this.appointment = appointment;
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return this.appointment.isSame(appointment);
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return appointmentsAdded.stream().anyMatch(appointment::isSame);
        }

        @Override
        public void addAppointment(Appointment appointment) {
            requireNonNull(appointment);
            appointmentsAdded.add(appointment);
        }

        @Override
        public boolean doesAppointmentClash(Appointment appointment, Appointment toExclude) {
            if (appointmentsAdded.size() == 0) {
                return false;
            }

            for (Appointment appt : appointmentsAdded) {
                if (appointmentsAdded.equals(toExclude)) {
                    continue;
                } else if (appointmentsAdded.equals(appt)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void setDate(Date date) {}

        @Override
        public void setYear(Year year) { }

        @Override
        public void setMonth(Month month) { }

        @Override
        public void setDay(Day day) { }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) { }
    }
}
