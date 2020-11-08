package team.baymax.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.Description;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Phone;
import team.baymax.model.patient.Remark;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;


/**
 * Contains utility methods for populating {@code AppointmentManager} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Nric("S0123456A"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Gender("M"), getTagSet(), new Remark("Allergic to ibuprofen.")),
            new Patient(new Nric("T0123456A"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Gender("F"), getTagSet(),
                    new Remark("Only available on Saturdays.")),
            new Patient(new Nric("S6543210A"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Gender("F"), getTagSet("CHAS", "LTC"), new Remark("Under long term care.")),
            new Patient(new Nric("T6543210A"), new Name("David Li"), new Phone("91031282"),
                    new Gender("M"), getTagSet("Paediatric"), new Remark("Diabetic.")),
            new Patient(new Nric("T1548765D"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Gender("M"), getTagSet("CHAS"), new Remark("Allergic to aspirin.")),
            new Patient(new Nric("S4658753E"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Gender("M"), getTagSet("LTC"), new Remark("Currently taking warfarin."))
        };
    }

    public static ReadOnlyListManager<Patient> getSamplePatientManager() {
        PatientManager samplePM = new PatientManager();
        for (Patient samplePatient : getSamplePatients()) {
            samplePM.addPatient(samplePatient);
        }
        return samplePM;
    }

    public static Appointment[] getSampleAppointments() {
        Patient[] patients = getSamplePatients();
        return new Appointment[] {
            new Appointment(patients[0], DateTime.fromString("13-11-2020 09:00"), new Duration(60),
                    new Description("Full body checkup"), getTagSet("Xray"), false),
            new Appointment(patients[3], DateTime.fromString("13-11-2020 10:00"), new Duration(60),
                    new Description("Diabetes checkup"), getTagSet(), false),
            new Appointment(patients[0], DateTime.fromString("13-11-2021 11:00"), new Duration(60),
                    new Description("Blood pressure checkup"), getTagSet(), false),
            new Appointment(patients[2], DateTime.fromString("13-11-2020 13:00"), new Duration(60),
                    new Description("Wrist fracture checkup"), getTagSet("Xray"), false),
            new Appointment(patients[1], DateTime.fromString("14-11-2020 09:00"), new Duration(60),
                    new Description("Pregnancy checkup"), getTagSet("Gynaecology"),
                    false),
            new Appointment(patients[1], DateTime.fromString("15-11-2021 10:00"), new Duration(60),
                    new Description("Pregnancy checkup"), getTagSet("Gynaecology"),
                    false)
        };
    }

    public static ReadOnlyListManager<Appointment> getSampleAppointmentManager() {
        AppointmentManager sampleAppointmentManager = new AppointmentManager();
        for (Appointment sampleAppointment : getSampleAppointments()) {
            sampleAppointmentManager.addAppointment(sampleAppointment);
        }
        return sampleAppointmentManager;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
