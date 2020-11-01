package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.baymax.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import team.baymax.model.util.datetime.Duration;

public class DurationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        Integer invalidDuration = 0;
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }

    @Test
    public void invalidDuration() {
        // Maximum value is 24 * 60 (equivalent to one day)
        assertFalse(Duration.isValidDuration(24 * 60 + 1));

        // Minimum value is 1
        assertFalse(Duration.isValidDuration(0));

        // Negative duration
        assertFalse(Duration.isValidDuration(-1));
    }
}
