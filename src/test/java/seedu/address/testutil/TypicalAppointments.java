package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;

import java.time.LocalDateTime;
import java.util.HashSet;

import seedu.address.commons.core.time.DateTime;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.Description;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APT1 = new Appointment(ALICE, DateTime.fromString("2020-10-11T12:45:30"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT2 = new Appointment(ALICE, DateTime.fromString("2020-10-12T12:45:30"),
            AppointmentStatus.UPCOMING, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT3 = new Appointment(CARL, DateTime.fromString("2002-11-11T11:30:20"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT4 = new Appointment(BOB, DateTime.fromString("2020-10-11T12:45:30"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

}
