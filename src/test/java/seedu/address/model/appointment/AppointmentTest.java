package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.*;
import static seedu.address.testutil.TypicalPatients.ALICE;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> APT4.getTags().remove(0));
    }

    @Test
    public void isSameAppointment() {
        // same dateTime -> return True
        assertTrue(APT1.isSame(APT4));
        // different dateTime -> return False
        assertFalse(APT1.isSame(APT3));
        //same patient but different dateTime -> False
        assertFalse(APT1.isSame(APT2));

    }

    @Test
    public void equals() {
        // same Patient, exact same appointment -> True
        assertEquals(APT5, APT1);
        // different Patient -> False
        assertNotEquals(APT3, APT1);

    }

    @Test
    public void getPatient() {
        // same Patient
        assertEquals(ALICE, APT1.getPatient());
        // different Patient
        assertNotEquals(ALICE, APT4.getPatient());
    }

}
