package team.baymax.model.modelmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.NameMatchPredicate;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientSortByNameComparator;
import team.baymax.model.util.uniquelist.UniqueList;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;

/**
 * Wraps all data at the PatientManager level
 * Duplicates are not allowed (by.equals comparison)
 */
public class PatientManager implements ReadOnlyListManager<Patient> {
    private final UniqueList<Patient> patients;

    {
        patients = new UniqueList<>();
    }

    public PatientManager() {}

    /**
     * Creates a PatientManager using the Patients in {@code toBeCopied}
     */
    public PatientManager(ReadOnlyListManager<Patient> toBeCopied) {
        this();
        resetData(toBeCopied);
        sortPatientList(new PatientSortByNameComparator());
    }

    // list overwrite operations

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setElements(patients);
        sortPatientList(new PatientSortByNameComparator());
    }

    /**
     * Resets the existing data of this {@code PatientManager} with {@code newData}.
     */
    public void resetData(ReadOnlyListManager<Patient> newData) {
        requireNonNull(newData);
        setPatients(newData.getReadOnlyList());
        sortPatientList(new PatientSortByNameComparator());
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
     * Returns true if a patient with the same nric as {@code nric} exists in the patient list.
     */
    public boolean hasPatient(Nric nric) {
        requireNonNull(nric);
        return patients.contains((patient) -> patient.getNric().equals(nric));
    }

    /**
     * Adds a patient to the PatientManager.
     * The patient must not already exist in the PatientManager.
     */
    public void addPatient(Patient p) {
        patients.add(p);
        sortPatientList(new PatientSortByNameComparator());
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
        sortPatientList(new PatientSortByNameComparator());
    }

    /**
     * Removes {@code key} from this {@code PatientManager}.
     * {@code key} must exist in the patient manager.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
    }

    /**
     * Returns a patient matching the given nric, if it exists, otherwise throws an {@code ElementNotFoundException}
     */
    public Patient getPatient(Nric nric) throws ElementNotFoundException {
        return patients.getByPredicate((patient) -> patient.getNric().equals(nric));
    }

    /**
     * Returns the first patient matching the given name, if it exists,
     * otherwise throws an {@code ElementNotFoundException}
     */
    public Patient getPatient(Name name) throws ElementNotFoundException {
        return patients.getByPredicate(new NameMatchPredicate(name));
    }

    // util methods
    @Override
    public String toString() {
        return "PatientManager:\n"
                + patients.stream().map(Patient::toString).collect(Collectors.joining("\n"))
                + "\nTotal number of patients: " + patients.size();
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
