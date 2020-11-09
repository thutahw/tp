package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.KEYWORDS_PREDICATE;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AppointmentContainsKeywordsPredicateTest {
    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentContainsKeywordsPredicate(null));
    }

    @Test
    void test_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> KEYWORDS_PREDICATE.test(null));
    }

    @Test
    void test_wrongInput_returnsFalse() {
        assertFalse(KEYWORDS_PREDICATE.test(ALICE_APT));
        assertFalse(KEYWORDS_PREDICATE.test(ALICE_APT_VAR_1));
        assertFalse(KEYWORDS_PREDICATE.test(CARL_APT));
    }

    @Test
    void test_correctInput_returnsTrue() {
        assertTrue(KEYWORDS_PREDICATE.test(BOB_APT));
    }

    @Test
    void equals_differentValue_returnsFalse() {
        assertNotEquals(null, KEYWORDS_PREDICATE);
        assertNotEquals(1, KEYWORDS_PREDICATE);
        assertNotEquals(List.of("Diabetes", "LTP"), KEYWORDS_PREDICATE);
        assertNotEquals(new AppointmentContainsKeywordsPredicate(List.of("Different", "keywords")), KEYWORDS_PREDICATE);
    }

    @Test
    void equals_sameValue_returnsTrue() {
        assertEquals(new AppointmentContainsKeywordsPredicate(List.of("Diabetes", "LTP")), KEYWORDS_PREDICATE);
    }
}
