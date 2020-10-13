package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.listmanagers.PatientManager;
import seedu.address.model.listmanagers.ReadOnlyListManager;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AppointmentManager} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Nric("S0123456A"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Gender("M"), getTagSet("friends"), new Remark("Likes to swim")),
            new Patient(new Nric("T0123456A"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Gender("F"), getTagSet("colleagues", "friends"), new Remark("Likes to dance")),
            new Patient(new Nric("S6543210A"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Gender("F"), getTagSet("neighbours"), new Remark("Likes to sing")),
            new Patient(new Nric("T6543210A"), new Name("David Li"), new Phone("91031282"),
                    new Gender("M"), getTagSet("family"), new Remark("Likes to run")),
            new Patient(new Nric("T1548765D"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Gender("M"), getTagSet("classmates"), new Remark("Likes to read")),
            new Patient(new Nric("S4658753E"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Gender("M"), getTagSet("colleagues"), new Remark("Likes to game"))
        };
    }

    public static ReadOnlyListManager<Patient> getSamplePatientManager() {
        PatientManager samplePM = new PatientManager();
        for (Patient samplePatient : getSamplePatients()) {
            samplePM.addPatient(samplePatient);
        }
        return samplePM;
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
