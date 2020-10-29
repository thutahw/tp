package team.baymax.storage.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.baymax.commons.exceptions.IllegalValueException;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;


/**
 * An Immutable AppointmentManager that is serializable to JSON format.
 */
@JsonRootName(value = "appointmentmanager")
public class JsonSerializableAppointmentManager {

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointments list contains duplicate appointment(s).";

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAppointmentManager} with the given appointments.
     */
    @JsonCreator
    public JsonSerializableAppointmentManager(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments.addAll(appointments);
    }

    /**
     * Converts a given {@code ReadOnlyListManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppointmentManager}.
     */
    public JsonSerializableAppointmentManager(ReadOnlyListManager<Appointment> source) {
        appointments.addAll(source.getReadOnlyList().stream().map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this appointment manager into the model's {@code AppointmentManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AppointmentManager toModelType(PatientManager patientManager) throws IllegalValueException {
        AppointmentManager appointmentManager = new AppointmentManager();
        for (JsonAdaptedAppointment jsonAdaptedPatient : appointments) {
            Appointment appointment = jsonAdaptedPatient.toModelType(patientManager);
            if (appointmentManager.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            appointmentManager.addAppointment(appointment);
        }
        return appointmentManager;
    }

}
