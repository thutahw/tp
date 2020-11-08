package team.baymax.testutil.appointment;

import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BOB;
import static team.baymax.testutil.patient.TypicalPatients.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.Description;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment ALICE_APT = new Appointment(ALICE,
            DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("desc 1"),
            new HashSet<>(), false);

    // Exact duplicate of APT1
    public static final Appointment ALICE_APT_DUPLICATE = new Appointment(ALICE,
            DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("desc 1"),
            new HashSet<>(), false);

    // Variant 1 - same patient and datetime with APT1, but other fields are different
    public static final Appointment ALICE_APT_VAR_1 = new Appointment(ALICE,
            DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("desc 2"),
            new HashSet<>(), true);

    // Variant 2 - same patient, but different datetime
    public static final Appointment ALICE_APT_VAR_2 = new Appointment(ALICE,
            DateTime.fromString("12-10-2020 12:45"),
            new Duration(60), new Description("desc 2"),
            new HashSet<>(), false);

    public static final Appointment CARL_APT = new Appointment(CARL,
            DateTime.fromString("11-11-2020 11:30"),
            new Duration(60), new Description("desc 3"),
            new HashSet<>(), false);

    public static final Appointment BOB_APT = new Appointment(BOB,
            DateTime.fromString("11-10-2020 12:45"),
            new Duration(60), new Description("desc 4"),
            new HashSet<>(), false);


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
        return new ArrayList<>(Arrays.asList(ALICE_APT, CARL_APT, BOB_APT));
    }
}
