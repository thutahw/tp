package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class AppointmentTest {

    private final LocalDateTime dateTimeOne = LocalDateTime.parse("2020-10-11T12:45:30");
    private final LocalDateTime dateTimeTwo = LocalDateTime.parse("2002-11-11T11:30:20");
    private final Description description = new Description("long term patient");
    private final Set<Tag> tagSet = new HashSet<>();

    private final Appointment apt1 = new Appointment(ALICE, dateTimeOne, tagSet, description,
            AppointmentStatus.DONE);
    private final Appointment apt2 = new Appointment(BOB, dateTimeOne, tagSet, description,
            AppointmentStatus.UPCOMING);
    private final Appointment apt3 = new Appointment(CARL, dateTimeTwo, tagSet, description,
            AppointmentStatus.DONE);
    private final Appointment apt4 = new Appointment(ALICE, dateTimeTwo, tagSet, description,
            AppointmentStatus.UPCOMING);

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> apt4.getTags().remove(0));
    }

    @Test
    public void isSameAppointment() {
        // same dateTime -> return True
        assertTrue(apt1.isSameAppointment(apt2));
        // different dateTime -> return False
        assertFalse(apt1.isSameAppointment(apt3));
        //same patient but different dateTime -> False
        assertFalse(apt1.isSameAppointment(apt4));

    }

    @Test
    public void equals() {
        // same Patient -> True
        assertEquals(apt4, apt1);
        // different Patient -> False
        assertNotEquals(apt2, apt1);

    }

    @Test
    public void getPatient() {
        // same Patient
        assertEquals(ALICE, apt1.getPatient());
        // different Patient
        assertNotEquals(ALICE, apt2.getPatient());
    }

}
