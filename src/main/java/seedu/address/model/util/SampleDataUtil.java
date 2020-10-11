package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePersons() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Gender("alexyeoh@example.com"),
                getTagSet("friends"),
                new Remark("Likes to swim")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Gender("berniceyu@example.com"),
                getTagSet("colleagues", "friends"),
                new Remark("Likes to dance")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Gender("charlotte@example.com"),
                getTagSet("neighbours"),
                new Remark("Likes to sing")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Gender("lidavid@example.com"),
                getTagSet("family"),
                new Remark("Likes to run")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Gender("irfan@example.com"),
                getTagSet("classmates"),
                new Remark("Likes to read")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Gender("royb@example.com"),
                getTagSet("colleagues"),
                new Remark("Likes to game"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePersons()) {
            sampleAb.addPatient(samplePatient);
        }
        return sampleAb;
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
