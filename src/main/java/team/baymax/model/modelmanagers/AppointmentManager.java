package team.baymax.model.modelmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentClashPredicate;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.uniquelist.UniqueList;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;


/**
 * Wraps all data at the AppointmentManager level
 * Duplicates are not allowed (by.equals comparison)
 */
public class AppointmentManager implements ReadOnlyListManager<Appointment> {
    private final UniqueList<Appointment> appointments;

    {
        appointments = new UniqueList<Appointment>();
    }

    public AppointmentManager() {}

    /**
     * Creates an AppointmentManager using the Appointments in {@code toBeCopied}
     */
    public AppointmentManager(ReadOnlyListManager<Appointment> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the appointments list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setElements(appointments);
    }

    /**
     * Resets the existing data of this {@code AppointmentManager} with {@code newData}.
     */
    public void resetData(ReadOnlyListManager<Appointment> newData) {
        requireNonNull(newData);
        setAppointments(newData.getReadOnlyList());
    }

    /**
     * Sorts the appointments list with the specified comparator.
     */
    public void sortAppointmentList(Comparator<Appointment> cmp) {
        appointments.sort(cmp);
    }

    // appointment-level operations

    /**
     * Returns true if an appointment with the same identity as {@code appointment}
     * exists in the appointments list.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Returns true if there is an appointment existing in the manager that clashes with
     * {@code appointment} in time.
     */
    public boolean doesAppointmentClash(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(new AppointmentClashPredicate(appointment));
    }

    /**
     * Adds a appointment to the AppointmentManager.
     * The appointment must not already exist in the AppointmentManager.
     */
    public void addAppointment(Appointment p) {
        appointments.add(p);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the AppointmentManager.
     * The appointment identity of {@code editedAppointment} must not be the same as another
     * existing appointment in the appointment manager.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);
        appointments.setElement(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AppointmentManager}.
     * {@code key} must exist in the appointment manager.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    /**
     * Returns the Appointment matching the given predicate. If no Appointment matches the
     * predicate given, an {@code ElementNotFoundException} is thrown.
     * @return
     */
    public Appointment getApptByPred(Predicate<Appointment> pred) throws ElementNotFoundException {
        return appointments.getByPredicate(pred);
    }

    // util methods

    @Override
    public String toString() {
        return "AppointmentManager:\n"
                + appointments.stream().map(Appointment::toString).collect(Collectors.joining("\n"))
                + "\nTotal number of appointments: " + appointments.size();
    }

    /**
     * Modifies appointments list to mark all appointments that have passed as DONE if they
     * are not explicitly marked as MISSING
     */
    private void markAllPastAppointmentsAsDone() {
        Predicate<Appointment> apptPastButMarkedAsUpcoming = new Predicate<Appointment>() {
            @Override
            public boolean test(Appointment appointment) {
                return appointment.getDateTime().compareTo(DateTime.current()) < 0
                        && appointment.getStatus() == AppointmentStatus.UPCOMING;
            }
        };

        while (appointments.contains(apptPastButMarkedAsUpcoming)) {
            Appointment pastAppt = appointments.getByPredicate(apptPastButMarkedAsUpcoming);
            Appointment markedAsDoneAppt = new Appointment(pastAppt.getPatient(),
                    pastAppt.getDateTime(), pastAppt.getDuration(), pastAppt.getDescription(),
                    pastAppt.getTags(), AppointmentStatus.DONE);
            setAppointment(pastAppt, markedAsDoneAppt);
        }
    }

    @Override
    public ObservableList<Appointment> getReadOnlyList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentManager // instanceof handles nulls
                && appointments.equals(((AppointmentManager) other).appointments));
    }

    @Override
    public int hashCode() {
        return appointments.hashCode();
    }
}
