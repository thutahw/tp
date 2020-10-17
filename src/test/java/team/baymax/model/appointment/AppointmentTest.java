package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.TypicalAppointments.APPT1;
import static team.baymax.testutil.TypicalAppointments.APPT2;
import static team.baymax.testutil.TypicalAppointments.APPT3;
import static team.baymax.testutil.TypicalAppointments.APPT4;
import static team.baymax.testutil.TypicalAppointments.APPT5;
import static team.baymax.testutil.TypicalPatients.ALICE;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> APPT4.getTags().remove(0));
    }

    @Test
    public void isSameAppointment() {
        // same dateTime -> return True
        assertTrue(APPT1.isSame(APPT4));
        // different dateTime -> return False
        assertFalse(APPT1.isSame(APPT3));
        //same patient but different dateTime -> False
        assertFalse(APPT1.isSame(APPT2));

    }

    @Test
    public void equals() {
        // same Patient, exact same appointment -> True
        assertEquals(APPT5, APPT1);
        // different Patient -> False
        assertNotEquals(APPT3, APPT1);

    }

    @Test
    public void getPatient() {
        // same Patient
        assertEquals(ALICE, APPT1.getPatient());
        // different Patient
        assertNotEquals(ALICE, APPT4.getPatient());
    }

}
