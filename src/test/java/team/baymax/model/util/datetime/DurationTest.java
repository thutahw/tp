package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;

import org.junit.jupiter.api.Test;

public class DurationTest {

    @Test
    void constructor_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    void isValidDuration_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Duration.isValidDuration(null));
    }

    @Test
    void isValidDuration_invalidValue_returnsFalse() {
        assertFalse(Duration.isValidDuration(-1));
        assertFalse(Duration.isValidDuration(0));
        assertFalse(Duration.isValidDuration(24 * 60 + 1));
    }

    @Test
    void isValidDuration_validValue_returnsTrue() {
        assertTrue(Duration.isValidDuration(1));
        assertTrue(Duration.isValidDuration(24 * 60));
    }

    @Test
    void toString_validDuration_returnsString() {
        assertEquals("1 Hour(s)", ALICE_APT.getDuration().toString());
        assertEquals("40 Minute(s)", BOB_APT.getDuration().toString());
    }

    @Test
    void equals_differentValues_returnsFalse() {
        assertNotEquals(ALICE_APT.getDuration(), null);
        assertNotEquals(ALICE_APT.getDuration(), new Duration(45));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        assertEquals(ALICE_APT.getDuration(), ALICE_APT_VAR_1.getDuration());
    }

    @Test
    void hashCode_differentValue() {
        assertNotEquals(ALICE_APT.hashCode(), BOB_APT.hashCode());
    }

    @Test
    void hashCode_sameValue() {
        assertEquals(ALICE_APT.getDuration().hashCode(), new Duration(60).hashCode());
    }
}
