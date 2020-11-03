package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.baymax.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {

        // null Description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // blank description
        assertFalse(Description.isValidDescription("")); // empty description with no spaces

        // invalid description
        assertFalse(Description.isValidDescription(" ")); // empty description with one space
        assertFalse(Description.isValidDescription("   ")); // empty description with multiple spaces
    }
}
