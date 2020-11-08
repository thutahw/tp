package team.baymax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.GuiSettings;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.patient.NameContainsKeywordsPredicate;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.patient.PatientManagerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PatientManager(), new PatientManager(modelManager.getPatientManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPatientStorageFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPatientStorageFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setPatientStorageFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPatientStorageFilePath(null));
    }

    @Test
    public void setPatientStorageFilePath_validPath_setsPatientStorageFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPatientStorageFilePath(path);
        assertEquals(path, modelManager.getPatientStorageFilePath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInAppointmentBook_returnsFalse() {
        assertFalse(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInAppointmentBook_returnsTrue() {
        modelManager.addPatient(ALICE);
        assertTrue(modelManager.hasPatient(ALICE));
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPatientList().remove(0));
    }

    @Test
    public void equals() {
        PatientManager patientManager = new PatientManagerBuilder().withPatient(ALICE).withPatient(BENSON).build();
        PatientManager differentPatientManager = new PatientManager();
        AppointmentManager appointmentManager = new AppointmentManager();
        CalendarManager calendarManager = new CalendarManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(patientManager, appointmentManager, userPrefs, new CalendarManager());
        ModelManager modelManagerCopy = new ModelManager(patientManager, appointmentManager, userPrefs,
                new CalendarManager());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different patientManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPatientManager, appointmentManager, userPrefs,
                calendarManager)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().getFullName().split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(patientManager, appointmentManager, userPrefs,
                calendarManager)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientStorageFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(patientManager, appointmentManager, differentUserPrefs,
                calendarManager)));
    }
}
