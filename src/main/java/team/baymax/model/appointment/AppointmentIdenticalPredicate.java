package team.baymax.model.appointment;

import java.util.function.Predicate;

public class AppointmentIdenticalPredicate implements Predicate<Appointment> {

    private final Appointment appointment;

    public AppointmentIdenticalPredicate(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.equals(this.appointment);
    }
}
