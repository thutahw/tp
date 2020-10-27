package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
<<<<<<< HEAD
        // same dateTime -> return True
        assertTrue(APPT1.isSame(APPT4));
        // different dateTime -> return False
        assertFalse(APPT1.isSame(APPT3));
        //same patient but different dateTime -> False
        assertFalse(APPT1.isSame(APPT2));
=======
        // same dateTime -> returns True
        assertTrue(APT1.isSame(APT4));
        // same dateTime but different Patient -> return False
        assertFalse(APT1.isSame(APT4));
        // different dateTime -> return False
        assertFalse(APT1.isSame(APT3));
        //same patient but different dateTime ->  returns False
        assertFalse(APT1.isSame(APT2));
>>>>>>> 8d46a700f92abfcf9a6597878b4af12c8b6367d5

    }

    @Test
    public void equals() {
<<<<<<< HEAD
        // same Patient, exact same appointment -> True
        assertEquals(APPT5, APPT1);
        // different Patient -> False
        assertNotEquals(APPT3, APPT1);
=======
        // same Patient, exact same appointment -> returns True
        assertEquals(APT5, APT1);
        // different Patient -> returns False
        assertNotEquals(APT3, APT1);
>>>>>>> 8d46a700f92abfcf9a6597878b4af12c8b6367d5

    }

    @Test
    public void getPatient() {
<<<<<<< HEAD
        // same Patient
        assertEquals(ALICE, APPT1.getPatient());
        // different Patient
        assertNotEquals(ALICE, APPT4.getPatient());
=======
        // same Patient -> returns True
        assertEquals(ALICE, APT1.getPatient());
        // different Patient -> returns False
        assertNotEquals(ALICE, APT4.getPatient());
>>>>>>> 8d46a700f92abfcf9a6597878b4af12c8b6367d5
    }

}
