package team.baymax.testutil;

import static team.baymax.testutil.TypicalPatients.ALICE;
import static team.baymax.testutil.TypicalPatients.BOB;
import static team.baymax.testutil.TypicalPatients.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APT1 = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("description 1"), new HashSet<>(), AppointmentStatus.DONE);

    public static final Appointment APT1_VARIANT_1 = new Appointment(ALICE,
            DateTime.fromString("11-10-2020 12:45"), new Duration(60),
            new Description("appointment 1 with same datetime and patient"), new HashSet<>(), AppointmentStatus.MISSED);
    public static final Appointment APT1_DUPLICATE = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("appointment 1 duplicate"), new HashSet<>(), AppointmentStatus.DONE);

    public static final Appointment APT2 = new Appointment(ALICE, DateTime.fromString("12-10-2020 12:45"),
            new Duration(60), new Description("long term patient"), new HashSet<>(), AppointmentStatus.UPCOMING);

    public static final Appointment APT3 = new Appointment(CARL, DateTime.fromString("11-11-2002 11:30"),
            new Duration(60), new Description("long term patient"), new HashSet<>(), AppointmentStatus.DONE);

    public static final Appointment APT4 = new Appointment(BOB, DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("long term patient"), new HashSet<>(), AppointmentStatus.DONE);



    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns a {@code PatientManager} with all the typical patients.
     */
    public static AppointmentManager getTypicalAppointmentManager() {
        AppointmentManager appointmentManager = new AppointmentManager();
        for (Appointment appointment : getTypicalAppointments()) {
            appointmentManager.addAppointment(appointment);
        }
        return appointmentManager;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APT1, APT2, APT3));
    }
}
