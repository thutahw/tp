package team.baymax.testutil;

import static team.baymax.testutil.TypicalPatients.ALICE;
import static team.baymax.testutil.TypicalPatients.BOB;
import static team.baymax.testutil.TypicalPatients.CARL;

import java.util.HashSet;

import team.baymax.model.calendar.DateTime;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APT1 = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT2 = new Appointment(ALICE, DateTime.fromString("12-10-2020 12:45"),
            AppointmentStatus.UPCOMING, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT3 = new Appointment(CARL, DateTime.fromString("11-11-2002 11:30"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT4 = new Appointment(BOB, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APT5 = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

}
