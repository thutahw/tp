package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_2;
import static team.baymax.testutil.appointment.TypicalAppointments.BELONGS_TO_PATIENT_PREDICATE;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

public class BelongsToPatientPredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BelongsToPatientPredicate(null));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> BELONGS_TO_PATIENT_PREDICATE.test(null));
    }

    @Test
    void test_wrongInput_returnsFalse() {
        assertFalse(BELONGS_TO_PATIENT_PREDICATE.test(ALICE_APT));
        assertFalse(BELONGS_TO_PATIENT_PREDICATE.test(ALICE_APT_VAR_1));
        assertFalse(BELONGS_TO_PATIENT_PREDICATE.test(ALICE_APT_VAR_2));
        assertFalse(BELONGS_TO_PATIENT_PREDICATE.test(CARL_APT));
    }

    @Test
    void test_correctInput_returnsTrue() {
        assertTrue(BELONGS_TO_PATIENT_PREDICATE.test(BOB_APT));
    }

    @Test
    void equals_diffInput_returnsFalse() {
        assertNotEquals(null, BELONGS_TO_PATIENT_PREDICATE);
        assertNotEquals(new BelongsToPatientPredicate(ALICE), BELONGS_TO_PATIENT_PREDICATE);
        assertNotEquals(BOB, BELONGS_TO_PATIENT_PREDICATE);
    }

    @Test
    void equals_sameInput_returnsTrue() {
        assertEquals(new BelongsToPatientPredicate(BOB), BELONGS_TO_PATIENT_PREDICATE);
    }
}
