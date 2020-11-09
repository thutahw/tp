package team.baymax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_DIABETIC;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.uniquelist.exceptions.DuplicateElementException;
import team.baymax.testutil.patient.PatientBuilder;

public class PatientManagerTest {

    private final PatientManager patientManager = new PatientManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), patientManager.getReadOnlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> patientManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyListManager_replacesData() {
        PatientManager newData = getTypicalPatientManager();
        patientManager.resetData(newData);
        assertEquals(newData, patientManager);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicateElementException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_DIABETIC)
                .build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        PatientManagerStub newData = new PatientManagerStub(newPatients);

        assertThrows(DuplicateElementException.class, () -> patientManager.resetData(newData));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> patientManager.hasPatient((Patient) null));
    }

    @Test
    public void hasPatient_patientNotInAppointmentBook_returnsFalse() {
        assertFalse(patientManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInAppointmentBook_returnsTrue() {
        patientManager.addPatient(ALICE);
        assertTrue(patientManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInAppointmentBook_returnsTrue() {
        patientManager.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_DIABETIC)
                .build();
        assertTrue(patientManager.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> patientManager.getReadOnlyList().remove(0));
    }

    /**
     * A stub ReadOnlyListManager whose patients list can violate interface constraints.
     */
    private static class PatientManagerStub implements ReadOnlyListManager<Patient> {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        PatientManagerStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Patient> getReadOnlyList() {
            return patients;
        }
    }

}
