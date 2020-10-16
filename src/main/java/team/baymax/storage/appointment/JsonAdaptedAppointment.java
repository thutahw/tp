package team.baymax.storage.appointment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.baymax.commons.core.time.DateTime;
import team.baymax.commons.exceptions.IllegalValueException;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;
import team.baymax.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String patientNric;
    private final String dateTime;
    private final AppointmentStatus status;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientNRIC") String patientNric,
                                  @JsonProperty("dateTime") String dateTime,
                                  @JsonProperty("status") AppointmentStatus status,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.patientNric = patientNric;
        this.dateTime = dateTime;
        this.status = status;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.patientNric = source.getPatient().getNric().nric;
        this.dateTime = source.getDateTime().getStorageFormat();
        this.status = source.getStatus();
        this.description = source.getDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType(PatientManager patientManager)
            throws IllegalValueException, ElementNotFoundException {
        final List<Tag> patientTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            patientTags.add(tag.toModelType());
        }

        if (patientNric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }

        if (!Nric.isValidNric(patientNric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }

        final Nric modelNric = new Nric(patientNric);

        if (!patientManager.hasPatient(modelNric)) {
            throw new ElementNotFoundException();
        }

        final Patient modelPatient = patientManager.getPatientByNric(modelNric);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        DateTime modelDateTime = DateTime.fromString(dateTime);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentStatus.class.getSimpleName()));
        }

        AppointmentStatus modelStatus = status;


        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        final Description modelDescription = new Description(description);


        final Set<Tag> modelTags = new HashSet<>(patientTags);
        return new Appointment(modelPatient, modelDateTime, modelStatus, modelDescription, modelTags);
    }
}
