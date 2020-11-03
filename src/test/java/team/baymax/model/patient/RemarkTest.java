package team.baymax.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.baymax.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void equals() {
        assertEquals(new Remark(""), new Remark(""));
        assertEquals(new Remark("@#$%"), new Remark("@#$%"));
        assertEquals(new Remark("A remark."), new Remark("A remark."));
        assertNotEquals(new Remark("R1"), new Remark("R2"));
    }

}
