package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.APPOINTMENT_CLASH_PREDICATE;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT_CLASH_WITH_BOBV2;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT_VAR_2;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;

import org.junit.jupiter.api.Test;

public class AppointmentClashPredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentClashPredicate(null, ALICE_APT));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> APPOINTMENT_CLASH_PREDICATE.test(null));
    }

    @Test
    void test_invalidInput_returnsFalse() {
        assertFalse(APPOINTMENT_CLASH_PREDICATE.test(ALICE_APT));
        assertFalse(APPOINTMENT_CLASH_PREDICATE.test(CARL_APT));
    }

    @Test
    void test_validInput_returnsTrue() {
        assertTrue(APPOINTMENT_CLASH_PREDICATE.test(BOB_APT_CLASH_WITH_BOBV2));
        assertTrue(APPOINTMENT_CLASH_PREDICATE.test(BOB_APT_VAR_2));
    }
}
