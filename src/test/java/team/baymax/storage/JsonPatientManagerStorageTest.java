package team.baymax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.HOON;
import static team.baymax.testutil.patient.TypicalPatients.IDA;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.storage.patient.JsonPatientManagerStorage;

public class JsonPatientManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonPatientManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPatientManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPatientManager(null));
    }

    private java.util.Optional<ReadOnlyListManager<Patient>> readPatientManager(String filePath) throws Exception {
        return new JsonPatientManagerStorage(Paths.get(filePath))
                .readPatients(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPatientManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPatientManager("notJsonFormat.json"));
    }

    @Test
    public void readPatientManager_invalidPatientManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPatientManager("invalidPatientStorage.json"));
    }

    @Test
    public void readPatientManager_invalidAndValidPatientManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPatientManager("invalidAndValidPatient.json"));
    }

    @Test
    public void readAndSavePatientManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAppointmentBook.json");
        PatientManager original = getTypicalPatientManager();
        JsonPatientManagerStorage jsonPatientManagerStorage = new JsonPatientManagerStorage(filePath);

        // Save in new file and read back
        jsonPatientManagerStorage.savePatients(original, filePath);
        ReadOnlyListManager<Patient> readBack = jsonPatientManagerStorage.readPatients(filePath).get();
        assertEquals(original, new PatientManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonPatientManagerStorage.savePatients(original, filePath);
        readBack = jsonPatientManagerStorage.readPatients(filePath).get();
        assertEquals(original, new PatientManager(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonPatientManagerStorage.savePatients(original); // file path not specified
        readBack = jsonPatientManagerStorage.readPatients().get(); // file path not specified
        assertEquals(original, new PatientManager(readBack));

    }

    @Test
    public void saveAPatientmanager_nullPatientManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePatientManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code patientManager} at the specified {@code filePath}.
     */
    private void savePatientManager(ReadOnlyListManager<Patient> patientManager, String filePath) {
        try {
            new JsonPatientManagerStorage(Paths.get(filePath))
                    .savePatients(patientManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePatientManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePatientManager(new PatientManager(), null));
    }
}
