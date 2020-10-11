package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalAppointmentBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.testutil.PatientBuilder;

public class AppointmentBookTest {

    private final AppointmentBook appointmentBook = new AppointmentBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), appointmentBook.getPatientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAppointmentBook_replacesData() {
        AppointmentBook newData = getTypicalAppointmentBook();
        appointmentBook.resetData(newData);
        assertEquals(newData, appointmentBook);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        AppointmentBookStub newData = new AppointmentBookStub(newPatients);

        assertThrows(DuplicatePatientException.class, () -> appointmentBook.resetData(newData));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentBook.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInAddressBook_returnsFalse() {
        assertFalse(appointmentBook.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInAddressBook_returnsTrue() {
        appointmentBook.addPatient(ALICE);
        assertTrue(appointmentBook.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInAddressBook_returnsTrue() {
        appointmentBook.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(appointmentBook.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> appointmentBook.getPatientList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose patients list can violate interface constraints.
     */
    private static class AppointmentBookStub implements ReadOnlyAppointmentBook {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        AppointmentBookStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }
    }

}
