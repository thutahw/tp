package team.baymax.testutil;

import static team.baymax.testutil.TypicalPatients.ALICE;

import java.util.HashSet;
import java.util.Set;

import team.baymax.model.appointment.DateTime;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.SampleDataUtil;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final Patient DEFAULT_PATIENT = ALICE;
    public static final String DEFAULT_DATETIME = "2020-12-12T23:59:59";
    private static final AppointmentStatus DEFAULT_APPOINTMENT_STATUS = AppointmentStatus.UPCOMING;
    private static final String DEFAULT_DESCRIPTION = "long term patient";

    private Patient patient;
    private DateTime dateTime;
    private AppointmentStatus status;
    private Set<Tag> tags;
    private Description description;

    /**
     * Creates an AppointmentBuilder with the default details.
     */
    public AppointmentBuilder() {
        patient = DEFAULT_PATIENT;
        dateTime = DateTime.fromString(DEFAULT_DATETIME);
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
        this.dateTime = DateTime.fromString(dateTime);
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
        return new Appointment(patient, dateTime, status, description, tags);
    }
}
