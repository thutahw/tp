package team.baymax.model.appointment;

import team.baymax.model.patient.PatientSortByNameComparator;

import java.util.Comparator;

public class AppointmentSortByDateAndNameComparator implements Comparator<Appointment> {

    @Override
    public int compare(Appointment a1, Appointment a2) {
        if (!(a1.getDateTime().equals(a2.getDateTime()))) {
            return a1.getDateTime().compareTo(a2.getDateTime());
        }

        return new PatientSortByNameComparator().compare(a1.getPatient(), a2.getPatient());
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof AppointmentSortByDateAndNameComparator;
    }
}
