package team.baymax.testutil.patient;

import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.patient.Patient;

/**
 * A utility class to help with building PatientManager objects.
 * Example usage: <br>
 *     {@code PatientManager ab = new PatientManagerBuilder().withPatient("John", "Doe").build();}
 */
public class PatientManagerBuilder {

    private PatientManager patientManager;

    public PatientManagerBuilder() {
        patientManager = new PatientManager();
    }

    public PatientManagerBuilder(PatientManager patientManager) {
        this.patientManager = patientManager;
    }

    /**
     * Adds a new {@code Patient} to the {@code PatientManager} that we are building.
     */
    public PatientManagerBuilder withPatient(Patient patient) {
        patientManager.addPatient(patient);
        return this;
    }

    public PatientManager build() {
        return patientManager;
    }
}
