package team.baymax.testutil;

import static team.baymax.testutil.TypicalPatients.ALICE;
import static team.baymax.testutil.TypicalPatients.BOB;
import static team.baymax.testutil.TypicalPatients.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import team.baymax.commons.core.time.DateTime;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.listmanagers.AppointmentManager;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPT1 = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APPT2 = new Appointment(ALICE, DateTime.fromString("12-10-2020 12:45"),
            AppointmentStatus.UPCOMING, new Description("long term patient"), new HashSet<>());

    public static final Appointment APPT3 = new Appointment(CARL, DateTime.fromString("11-11-2002 11:30"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APPT4 = new Appointment(BOB, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APPT5 = new Appointment(ALICE, DateTime.fromString("11-10-2020 12:45"),
            AppointmentStatus.DONE, new Description("long term patient"), new HashSet<>());

    public static final Appointment APPT6 = new Appointment(BOB, DateTime.fromString("09-09-2020 10:10"),
            AppointmentStatus.UPCOMING, new Description("long term patient"), new HashSet<>());

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
        return new ArrayList<>(Arrays.asList(APPT1, APPT2, APPT3));
    }
}
