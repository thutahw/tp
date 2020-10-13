package seedu.address.model.listmanagers;

import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;
import seedu.address.model.util.uniquelist.UniqueList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the PatientManager level
 * Duplicates are not allowed (by.equals comparison)
 */
public class PatientManager implements ReadOnlyListManager<Patient> {
    private final UniqueList<Patient> patients;

    {
        patients = new UniqueList<Patient>();
    }

    public PatientManager() {}

    /**
     * Creates a PatientManager using the Patients in {@code toBeCopied}
     */
    public PatientManager(ReadOnlyListManager<Patient> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setElements(patients);
    }

    /**
     * Resets the existing data of this {@code PatientManager} with {@code newData}.
     */
    public void resetData(ReadOnlyListManager<Patient> newData) {
        requireNonNull(newData);
        setPatients(newData.getReadOnlyList());
    }

    /**
     * Sorts the patient list with the specified comparator.
     */
    public void sortPatientList(Comparator<Patient> cmp) {
        patients.sort(cmp);
    }

    // patient-level operations

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the patient list.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Adds a patient to the PatientManager.
     * The patient must not already exist in the PatientManager.
     */
    public void addPatient(Patient p) {
        patients.add(p);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the patient manager.
     * The patient identity of {@code editedPatient} must not be the same as another existing patient in the
     * patient manager.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        patients.setElement(target, editedPatient);
    }

    /**
     * Removes {@code key} from this {@code PatientManager}.
     * {@code key} must exist in the patient manager.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
    }

    // util methods

    @Override
    public String toString() {
        return "PatientManager:\n"
                + patients.stream().map(Patient::toString).collect(Collectors.joining("\n"))
                +"\nTotal number of patients: " +patients.size();
    }

    @Override
    public ObservableList<Patient> getReadOnlyList() {
        return patients.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientManager // instanceof handles nulls
                && patients.equals(((PatientManager) other).patients));
    }

    @Override
    public int hashCode() {
        // return patients.hashCode();
        return patients.hashCode();
    }
}
