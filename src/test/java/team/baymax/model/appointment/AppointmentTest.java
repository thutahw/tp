package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.TypicalAppointments.APT1;
import static team.baymax.testutil.TypicalAppointments.APT1_DUPLICATE;
import static team.baymax.testutil.TypicalAppointments.APT2;
import static team.baymax.testutil.TypicalAppointments.APT3;
import static team.baymax.testutil.TypicalAppointments.APT4;
import static team.baymax.testutil.TypicalPatients.ALICE;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> APT4.getTags().remove(0));
    }

    @Test
    public void isSameAppointment() {
        // same dateTime and same Patient -> returns True
        assertTrue(APT1.isSame(APT1_DUPLICATE));
        // same dateTime but different Patient -> return False
        assertFalse(APT1.isSame(APT4));
        // different dateTime -> return False
        assertFalse(APT1.isSame(APT3));
        //same patient but different dateTime ->  returns False
        assertFalse(APT1.isSame(APT2));
    }

    @Test
    public void equals() {
        // same Patient, exact same appointment -> returns True
        assertEquals(APT1_DUPLICATE, APT1);
        // different Patient -> returns False
        assertNotEquals(APT3, APT1);

    }

    @Test
    public void getPatient() {
        // same Patient -> returns True
        assertEquals(ALICE, APT1.getPatient());
        // different Patient -> returns False
        assertNotEquals(ALICE, APT4.getPatient());
    }

}
