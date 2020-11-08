package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.IDENTICAL_PREDICATE;

import org.junit.jupiter.api.Test;

public class AppointmentIdenticalPredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentIdenticalPredicate(null));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> IDENTICAL_PREDICATE.test(null));
    }

    @Test
    void test_diffInput_returnsFalse() {
        assertFalse(IDENTICAL_PREDICATE.test(BOB_APT));
        assertFalse(IDENTICAL_PREDICATE.test(ALICE_APT_VAR_1));
        assertFalse(IDENTICAL_PREDICATE.test(CARL_APT));
    }

    @Test
    void test_sameInput_returnsTrue() {
        assertTrue(IDENTICAL_PREDICATE.test(ALICE_APT));
    }
}
