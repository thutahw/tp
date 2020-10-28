package team.baymax.testutil;

import static team.baymax.testutil.TypicalPatients.ALICE;

import java.util.HashSet;
import java.util.Set;

import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.SampleDataUtil;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;


/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final Patient DEFAULT_PATIENT = ALICE;
    public static final String DEFAULT_DATETIME = "11-10-2020 12:45";
    public static final int DEFAULT_DURATION = 60;
    public static final Time DEFAULT_TIME = null;
    public static final String DEFAULT_DESCRIPTION = "Monthly checkup.";
    public static final AppointmentStatus DEFAULT_APPOINTMENT_STATUS = AppointmentStatus.UPCOMING;

    private Patient patient;
    private DateTime dateTime;
    private Time time;
    private Duration duration;
    private AppointmentStatus status;
    private Set<Tag> tags;
    private Description description;

    /**
     * Creates an AppointmentBuilder with the default details.
     */
    public AppointmentBuilder() {
        patient = DEFAULT_PATIENT;
        dateTime = DateTime.fromString(DEFAULT_DATETIME);
        time = DEFAULT_TIME;
        duration = new Duration(DEFAULT_DURATION);
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
        time = appointmentToCopy.getTime();
        duration = appointmentToCopy.getDuration();
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
     * Parses the input into a {@code DateTime} and sets it as the dateTime of the appointment we are building
     *
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = DateTime.fromString(dateTime);
        return this;
    }

    /**
     * Parses the input into a {@code Time} and sets it as the time of the appointment we are building
     *
     */
    public AppointmentBuilder withTime(String time) {
        this.time = Time.fromString(time);
        return this;
    }

    /**
     * Parses the input into a Duration and sets it as the duration of the appointment we are building
     * @param duration
     */
    public AppointmentBuilder withDuration(Duration duration) {
        this.duration = duration;
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
        return new Appointment(patient, dateTime, duration, description, tags, status);
    }
}
