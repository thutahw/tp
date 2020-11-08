package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT_VAR_2;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.SAME_DATETIME_AND_PATIENT_PREDICATE;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME6;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

public class SameDatetimeAndPatientPredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SameDatetimeAndPatientPredicate(null, ALICE));
        assertThrows(NullPointerException.class, () -> new SameDatetimeAndPatientPredicate(DATETIME1, null));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SAME_DATETIME_AND_PATIENT_PREDICATE.test(null));
    }

    @Test
    void test_differentInput_returnsFalse() {
        assertFalse(SAME_DATETIME_AND_PATIENT_PREDICATE.test(ALICE_APT));
        assertFalse(SAME_DATETIME_AND_PATIENT_PREDICATE.test(CARL_APT));
        assertFalse(SAME_DATETIME_AND_PATIENT_PREDICATE.test(BOB_APT_VAR_2));
    }

    @Test
    void test_sameInput_returnsTrue() {
        assertTrue(SAME_DATETIME_AND_PATIENT_PREDICATE.test(BOB_APT));
    }

    @Test
    void equals_differentValue_returnsFalse() {
        assertNotEquals(null, SAME_DATETIME_AND_PATIENT_PREDICATE);
        assertNotEquals(new SameDatetimeAndPatientPredicate(DATETIME1, BOB), SAME_DATETIME_AND_PATIENT_PREDICATE);
    }

    @Test
    void equals_sameValue_returnsTrue() {
        assertEquals(new SameDatetimeAndPatientPredicate(DATETIME6, BOB), SAME_DATETIME_AND_PATIENT_PREDICATE);
    }
}
