package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT2;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DATETIME_2;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_2;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_TAG_DRGOH;

import org.junit.jupiter.api.Test;

import team.baymax.testutil.appointment.EditAppointmentDescriptorBuilder;

public class EditAppointmentDescriptorTest {

    @Test
    public void equals() {
        EditAppointmentDescriptor descriptorWithSameValues =
                new EditAppointmentDescriptor(DESC_APPT1);
        assertTrue(DESC_APPT1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_APPT1.equals(DESC_APPT1));

        // null -> returns false
        assertFalse(DESC_APPT1.equals(null));

        // different types -> returns false
        assertFalse(DESC_APPT1.equals(5));

        // different values -> returns false
        assertFalse(DESC_APPT1.equals(DESC_APPT2));

        // different description -> returns false
        EditAppointmentDescriptor editedDescriptor = new EditAppointmentDescriptorBuilder(DESC_APPT1)
                .withDescription(VALID_DESCRIPTION_2).build();
        assertFalse(DESC_APPT1.equals(editedDescriptor));

        // different datetime -> returns false
        editedDescriptor = new EditAppointmentDescriptorBuilder(DESC_APPT1).withDateTime(VALID_DATETIME_2).build();
        assertFalse(DESC_APPT1.equals(editedDescriptor));

        // different tags -> returns false
        editedDescriptor = new EditAppointmentDescriptorBuilder(DESC_APPT1).withTags(VALID_TAG_DRGOH).build();
        assertFalse(DESC_APPT1.equals(editedDescriptor));
    }
}
