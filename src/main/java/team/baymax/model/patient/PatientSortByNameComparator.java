package team.baymax.model.patient;

import java.util.Comparator;

public class PatientSortByNameComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient p1, Patient p2) {
        return p1.getName().getFullName().compareTo(p2.getName().getFullName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof PatientSortByNameComparator;
    }
}
