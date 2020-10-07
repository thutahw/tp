package seedu.address.model.appointment;

import seedu.address.model.patient.Remark;

import java.util.ArrayList;

/**
 * Contains a list for adding, deleting, updating,
 * appointments in the list.
 */

public class AppointmentList {
    // List of appointments
    private final ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

    /**
     * Adds a given <code>appointmentList</code> to the <code>appointmentList</code>.
     *
     * @param t task to be added
     */
        public void addAppointment(Appointment t) {
            appointmentList.add(t);
        }

    /**
     * Deletes a specified <code>appointment</code> from the <code>appointmentList</code>.
     *
     * @param appointmentNumber index of the task to be deleted in <code>appointmentList</code>
     */
        public void deleteAppointment(int appointmentNumber) {
            appointmentList.remove(appointmentNumber);
        }

    /**
     * Updates the description of a specified appointment.
     *
     * @param appointmentNumber  index of the appointment to be updated in <code>appointmentList</code>
     * @param newAppointmentRemark new description to update the appointment to
     * @return            the edited appointment
     */
        public void updateAppointmentRemark(int appointmentNumber, Remark newAppointmentRemark) {
            Appointment appointmentToBeEdited = appointmentList.get(appointmentNumber);
            appointmentToBeEdited = appointmentToBeEdited.editDescription(newAppointmentRemark);
        }

    /**
     * Marks a specified task as done by calling the <code>Task</code> method <code>markAsDone</code>.
     *
     * @param appointmentNumber  index of the appointment to be updated in <code>appointmentList</code>
     */
        public void markAsDone(int appointmentNumber, AppointmentStatus status) {
            Appointment appointmentToBeEdited = appointmentList.get(appointmentNumber);
            appointmentToBeEdited = appointmentToBeEdited.editAppointmentStatus(status);
        }

//    /**
//     * Searches for all tasks whose description contains a specified keyword,
//     * and returns a <code>TaskList</code> of all those tasks.
//     *
//     * @param keyword  the String used to search for tasks
//     * @return         a list of appointments with descriptions containing <code>keyword</code>
//     */
//        public AppointmentList findByName(String keyword) {
//            AppointmentList foundAppointments = new AppointmentList(); // to be returned
//
//            // process the keyword to make it easier to match
//            String searchTerm = keyword.toLowerCase();
//
//            for (Appointment t : appointmentList) {
//                // process the appointment information to make it easier to match
//                String processedPatientName = t.getPatient().getName().fullName.toLowerCase();
//
//                if (processedPatientName.contains(searchTerm)) {
//                    foundAppointments.addAppointment(t);
//                }
//            }
//
//            return foundAppointments;
//        }

//    /**
//     * Searches for all tasks whose description contains a specified keyword,
//     * and returns a <code>TaskList</code> of all those tasks.
//     *
//     * @param keyword  the String used to search for tasks
//     * @return         a list of tasks with descriptions containing <code>keyword</code>
//     */
//    public AppointmentList findByDescription(String keyword) {
//        AppointmentList foundAppointments = new AppointmentList(); // to be returned
//
//        // process the keyword to make it easier to match
//        String searchTerm = keyword.toLowerCase();
//
//        for (Appointment t : appointmentList) {
//            // process the appointment information to make it easier to match
//            String processedDescription = t.getRemark().toLowerCase();
//
//            if (processedPatientName.contains(searchTerm)) {
//                foundAppointments.addAppointment(t);
//            }
//        }
//
//        return foundAppointments;
//    }

        public int getSize() {
            return appointmentList.size();
        }

        public Appointment getAppointment(int i) {
            return appointmentList.get(i);
        }
}
