package seedu.address.storage.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.listmanagers.PatientManager;
import seedu.address.model.listmanagers.ReadOnlyListManager;
import seedu.address.model.patient.Patient;

/**
 * An Immutable AppointmentBook that is serializable to JSON format.
 */
@JsonRootName(value = "patientmanager")
class JsonSerializablePatientManager {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePatientManager} with the given patients.
     */
    @JsonCreator
    public JsonSerializablePatientManager(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyListManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePatientManager}.
     */
    public JsonSerializablePatientManager(ReadOnlyListManager<Patient> source) {
        patients.addAll(source.getReadOnlyList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this patient manager into the model's {@code PatientManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PatientManager toModelType() throws IllegalValueException {
        PatientManager patientManager = new PatientManager();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (patientManager.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            patientManager.addPatient(patient);
        }
        return patientManager;
    }

}
