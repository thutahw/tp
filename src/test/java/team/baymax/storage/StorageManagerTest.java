package team.baymax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.baymax.commons.core.GuiSettings;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.storage.appointment.JsonAppointmentManagerStorage;
import team.baymax.storage.patient.JsonPatientManagerStorage;
import team.baymax.storage.userprefs.JsonUserPrefsStorage;
import team.baymax.testutil.patient.TypicalPatients;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonPatientManagerStorage patientManagerStorage = new JsonPatientManagerStorage(getTempFilePath("pm"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonAppointmentManagerStorage appointmentManagerStorage =
                new JsonAppointmentManagerStorage(getTempFilePath("am"));
        storageManager = new StorageManager(patientManagerStorage, appointmentManagerStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void patientManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPatientManagerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPatientManagerStorageTest} class.
         */
        PatientManager original = TypicalPatients.getTypicalPatientManager();
        storageManager.savePatients(original);
        ReadOnlyListManager<Patient> retrieved = storageManager.readPatients().get();
        assertEquals(original, new PatientManager(retrieved));
    }

    @Test
    public void getPatientManagerStorageFilePath() {
        assertNotNull(storageManager.getPatientManagerStorageFilePath());
    }

}
