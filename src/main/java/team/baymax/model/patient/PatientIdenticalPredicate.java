package team.baymax.model.patient;

import java.util.function.Predicate;

public class PatientIdenticalPredicate implements Predicate<Patient> {

    private final Patient patient;

    public PatientIdenticalPredicate(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean test(Patient patient) {
        return patient.equals(this.patient);
    }
}
