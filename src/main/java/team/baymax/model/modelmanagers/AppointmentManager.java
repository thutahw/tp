package team.baymax.model.modelmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.util.uniquelist.UniqueList;


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

    // util methods

    @Override
    public String toString() {
        return "AppointmentManager:\n"
                + appointments.stream().map(Appointment::toString).collect(Collectors.joining("\n"))
                + "\nTotal number of appointments: " + appointments.size();
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
