package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_DUPLICATE;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VARIANT_1;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VARIANT_2;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> BOB_APT.getTags().remove(0));
    }

    @Test
    public void isSameAppointment() {
        // same dateTime and same patient -> return True
        assertTrue(ALICE_APT.isSame(ALICE_APT_VARIANT_1));
        // same dateTime and same Patient -> returns True
        assertTrue(ALICE_APT.isSame(ALICE_APT_DUPLICATE));
        // same dateTime but different Patient -> return False
        assertFalse(ALICE_APT.isSame(BOB_APT));
        // different dateTime -> return False
        assertFalse(ALICE_APT.isSame(CARL_APT));
        //same patient but different dateTime ->  returns False
        assertFalse(ALICE_APT.isSame(ALICE_APT_VARIANT_2));
    }

    @Test
    public void equals() {
        // same patient, exact same appointment -> returns True
        assertEquals(ALICE_APT, ALICE_APT_DUPLICATE);
        // different patient -> returns False
        assertNotEquals(CARL_APT, ALICE_APT);
    }

    @Test
    public void getPatient() {
        // same Patient
        assertEquals(ALICE, ALICE_APT.getPatient());
        // different Patient
        assertNotEquals(ALICE, BOB_APT.getPatient());
    }

}
