package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_2;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.MATCHES_DATE_PREDICATE;

import org.junit.jupiter.api.Test;

public class AppointmentMatchesDatePredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentMatchesDatePredicate(null));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> MATCHES_DATE_PREDICATE.test(null));
    }

    @Test
    void test_differentInput_returnsFalse() {
        assertFalse(MATCHES_DATE_PREDICATE.test(ALICE_APT_VAR_1));
        assertFalse(MATCHES_DATE_PREDICATE.test(ALICE_APT));
        assertFalse(MATCHES_DATE_PREDICATE.test(BOB_APT));
        assertFalse(MATCHES_DATE_PREDICATE.test(CARL_APT));
    }

    @Test
    void test_sameInput_returnsTrue() {
        assertTrue(MATCHES_DATE_PREDICATE.test(ALICE_APT_VAR_2));
    }
}
