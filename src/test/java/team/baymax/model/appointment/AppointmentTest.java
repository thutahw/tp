package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_DUPLICATE;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_1;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT_VAR_2;
import static team.baymax.testutil.appointment.TypicalAppointments.BOB_APT;
import static team.baymax.testutil.appointment.TypicalAppointments.CARL_APT;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BOB;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;

public class AppointmentTest {
    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, DateTime.fromString("01-01-2020 00:00"),
                    new Duration(60), new Description("Test."), new HashSet<Tag>(), false));
        assertThrows(NullPointerException.class, () -> new Appointment(ALICE, null,
                new Duration(60), new Description("Test."), new HashSet<Tag>(), false));
        assertThrows(NullPointerException.class, () -> new Appointment(ALICE, DateTime.fromString("01-01-2020 00:00"),
                null, new Description("Test."), new HashSet<Tag>(), false));
        assertThrows(NullPointerException.class, () -> new Appointment(ALICE, DateTime.fromString("01-01-2020 00:00"),
                new Duration(60), null, new HashSet<Tag>(), false));
        assertThrows(NullPointerException.class, () -> new Appointment(ALICE, DateTime.fromString("01-01-2020 00:00"),
                new Duration(60), new Description("Test."), null, false));
    }

    @Test
    public void getDateTime() {
        assertEquals(DateTime.fromString("30-12-2020 12:45"), ALICE_APT.getDateTime());
        assertEquals(DateTime.fromString("25-12-2020 11:30"), CARL_APT.getDateTime());
        assertEquals(DateTime.fromString("11-01-2020 12:45"), BOB_APT.getDateTime());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> BOB_APT.getTags().remove(0));
    }

    @Test
    public void getDate() {
        assertEquals(Date.fromString("30-12-2020"), ALICE_APT.getDate());
        assertEquals(Date.fromString("25-12-2020"), CARL_APT.getDate());
        assertEquals(Date.fromString("11-01-2020"), BOB_APT.getDate());
    }

    @Test
    public void getTime() {
        assertEquals(Time.fromString("12:45"), ALICE_APT.getTime());
        assertEquals(Time.fromString("11:30"), CARL_APT.getTime());
        assertEquals(Time.fromString("12:45"), BOB_APT.getTime());
    }

    @Test
    public void getDuration() {
        assertEquals(new Duration(60), ALICE_APT.getDuration());
        assertEquals(new Duration(50), CARL_APT.getDuration());
        assertEquals(new Duration(40), BOB_APT.getDuration());
    }

    @Test
    public void getEndDateTime() {
        assertEquals(DateTime.fromString("30-12-2020 13:45"), ALICE_APT.getEndDateTime());
        assertEquals(DateTime.fromString("25-12-2020 12:20"), CARL_APT.getEndDateTime());
        assertEquals(DateTime.fromString("11-01-2020 13:25"), BOB_APT.getEndDateTime());
    }

    @Test
    public void getDescription() {
        assertEquals(new Description("desc 1"), ALICE_APT.getDescription());
        assertEquals(new Description("desc 3"), CARL_APT.getDescription());
        assertEquals(new Description("desc 4"), BOB_APT.getDescription());
    }

    @Test
    public void getStatus() {
        assertEquals(AppointmentStatus.UPCOMING, ALICE_APT.getStatus());
        assertEquals(AppointmentStatus.MISSED, ALICE_APT_VAR_1.getStatus());
        assertEquals(AppointmentStatus.DONE, BOB_APT.getStatus());
    }

    @Test
    public void isSameAppointment() {
        // same dateTime and same patient -> return True
        assertTrue(ALICE_APT.isSame(ALICE_APT_VAR_1));
        // same dateTime and same Patient -> returns True
        assertTrue(ALICE_APT.isSame(ALICE_APT_DUPLICATE));
        // same dateTime but different Patient -> return False
        assertFalse(ALICE_APT.isSame(BOB_APT));
        // different dateTime -> return False
        assertFalse(ALICE_APT.isSame(CARL_APT));
        //same patient but different dateTime ->  returns False
        assertFalse(ALICE_APT.isSame(ALICE_APT_VAR_2));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        assertNotEquals(ALICE_APT, ALICE_APT_VAR_1);
        assertNotEquals(ALICE_APT, ALICE_APT_VAR_2);
        assertNotEquals(null, ALICE_APT_VAR_2);
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        assertEquals(ALICE_APT, ALICE_APT);
        assertEquals(ALICE_APT, new Appointment(ALICE,
                DateTime.fromString("30-12-2020 12:45"),
                new Duration(60), new Description("desc 1"),
                new HashSet<>(), false));
    }

    @Test
    public void hashCode_differentValue_differentHashcode() {
        assertNotEquals(ALICE_APT.hashCode(), BOB_APT.hashCode());
        assertNotEquals(ALICE_APT.hashCode(), CARL_APT.hashCode());
        assertNotEquals(ALICE_APT.hashCode(), ALICE_APT_VAR_1.hashCode());
    }

    @Test
    public void hashCode_sameValue_sameHashcode() {
        assertEquals(ALICE_APT.hashCode(), new Appointment(ALICE,
                DateTime.fromString("30-12-2020 12:45"),
                new Duration(60), new Description("desc 1"),
                new HashSet<>(), false).hashCode());
        assertEquals(BOB_APT.hashCode(), new Appointment(BOB,
                DateTime.fromString("11-01-2020 12:45"),
                new Duration(40), new Description("desc 4"),
                BOB.getTags(), false).hashCode());
    }

    @Test
    public void equals() {
        // same patient, exact same appointment -> returns True
        assertEquals(ALICE_APT, ALICE_APT_DUPLICATE);
        // different patient -> returns False
        assertNotEquals(CARL_APT, ALICE_APT);
    }

    @Test
    public void getPatient() {
        // same Patient
        assertEquals(ALICE, ALICE_APT.getPatient());
        // different Patient
        assertNotEquals(ALICE, BOB_APT.getPatient());
    }

    @Test
    public void toString_appointments() {
        assertEquals("\nPatient Name: Alice Pauline | NRIC: T1234567A | Gender: Female | Phone: 94351253"
                + "\n\t   Remark: remark Alice | Tags: [tag1]"
                + "\nDate-time: 30 Dec 2020, 12:45PM"
                + "\nDuration: 1 Hour(s)"
                + "\nDescription: desc 1", ALICE_APT.toString());
        assertEquals("\nPatient Name: Alice Pauline | NRIC: T1234567A | Gender: Female | Phone: 94351253"
                + "\n\t   Remark: remark Alice | Tags: [tag1]"
                + "\nDate-time: 30 Dec 2020, 12:45PM"
                + "\nDuration: 1 Hour(s)"
                + "\nDescription: desc 2", ALICE_APT_VAR_1.toString());
        assertEquals("\nPatient Name: Bob Choo | NRIC: S3322115E | Gender: Male | Phone: 81763222"
                + "\n\t   Remark: Only free on weekends. | Tags: [Diabetic][LTP]"
                + "\nDate-time: 11 Jan 2020, 12:45PM"
                + "\nDuration: 40 Minute(s)"
                + "\nDescription: desc 4"
                + "\nTags: [Diabetic][LTP]", BOB_APT.toString());
        assertEquals("\nPatient Name: Carl Kurz | NRIC: S8546464H | Gender: Female | Phone: 95352563"
                + "\n\t   Remark: remark Carl"
                + "\nDate-time: 25 Dec 2020, 11:30AM"
                + "\nDuration: 50 Minute(s)"
                + "\nDescription: desc 3", CARL_APT.toString());
    }
}
