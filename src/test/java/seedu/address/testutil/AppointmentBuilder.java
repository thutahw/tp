package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final Patient DEFAULT_PATIENT = ALICE;
    public static final String DEFAULT_DATETIME = "2020-12-12T23:59:59";
    private static final AppointmentStatus DEFAULT_APPOINTMENT_STATUS = AppointmentStatus.UPCOMING;
    private static final String DEFAULT_DESCRIPTION = "long term patient";

    private Patient patient;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private Set<Tag> tags;
    private Description description;

    /**
     * Creates an AppointmentBuilder with the default details.
     */
    public AppointmentBuilder() {
        patient = DEFAULT_PATIENT;
        dateTime = LocalDateTime.parse(DEFAULT_DATETIME);
        status = DEFAULT_APPOINTMENT_STATUS;
        tags = new HashSet<>();
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initialises an AppointmentBuilder with the data of appointmentToCopy
     * @param appointmentToCopy
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        patient = appointmentToCopy.getPatient();
        dateTime = appointmentToCopy.getDateTime();
        description = appointmentToCopy.getDescription();
        tags = appointmentToCopy.getTags();
        status = appointmentToCopy.getStatus();
    }

    /**
     * Sets the patient of the appointment we are building to the input
     * @param patient
     */
    public AppointmentBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Sets the status of the appointment we are building to the input
     * @param status
     */
    public AppointmentBuilder withStatus(AppointmentStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the description of the appointment we are building to the input
     * @param description
     */
    public AppointmentBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the input into a LocaLDateTime and sets it as the dateTime of the appointment we are building
     * @param dateTime
     */
    public AppointmentBuilder withTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime);
        return this;
    }

    /**
     * Parses the input into a Set of Tags and sets it as the tags of the appointment we are building
     * @param tags
     * @return
     */
    public AppointmentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Appointment build() {
        return new Appointment(patient, dateTime, tags, description, status);
    }
}
