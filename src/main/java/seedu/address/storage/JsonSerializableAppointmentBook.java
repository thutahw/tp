package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.patient.Patient;

/**
 * An Immutable AppointmentBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAppointmentBook {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAppointmentBook} with the given patients.
     */
    @JsonCreator
    public JsonSerializableAppointmentBook(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyAppointmentBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppointmentBook}.
     */
    public JsonSerializableAppointmentBook(ReadOnlyAppointmentBook source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this appointment book into the model's {@code AppointmentBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AppointmentBook toModelType() throws IllegalValueException {
        AppointmentBook appointmentBook = new AppointmentBook();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (appointmentBook.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            appointmentBook.addPatient(patient);
        }
        return appointmentBook;
    }

}
