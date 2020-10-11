package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalAppointmentBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;

public class JsonAppointmentBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAppointmentBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAppointmentBook(null));
    }

    private java.util.Optional<ReadOnlyAppointmentBook> readAppointmentBook(String filePath) throws Exception {
        return new JsonAppointmentBookStorage(Paths.get(filePath)).readAppointmentBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAppointmentBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAppointmentBook("notJsonFormat.json"));
    }

    @Test
    public void readAppointmentBook_invalidPatientAppointmentBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAppointmentBook("invalidPatientStorage.json"));
    }

    @Test
    public void readAppointmentBook_invalidAndValidPatientAppointmentBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAppointmentBook("invalidAndValidPatient.json"));
    }

    @Test
    public void readAndSaveAppointmentBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AppointmentBook original = getTypicalAppointmentBook();
        JsonAppointmentBookStorage jsonAppointmentBookStorage = new JsonAppointmentBookStorage(filePath);

        // Save in new file and read back
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        ReadOnlyAppointmentBook readBack = jsonAppointmentBookStorage.readAppointmentBook(filePath).get();
        assertEquals(original, new AppointmentBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        readBack = jsonAppointmentBookStorage.readAppointmentBook(filePath).get();
        assertEquals(original, new AppointmentBook(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonAppointmentBookStorage.saveAppointmentBook(original); // file path not specified
        readBack = jsonAppointmentBookStorage.readAppointmentBook().get(); // file path not specified
        assertEquals(original, new AppointmentBook(readBack));

    }

    @Test
    public void saveAppointmentBook_nullAppointmentBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code appointmentBook} at the specified {@code filePath}.
     */
    private void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, String filePath) {
        try {
            new JsonAppointmentBookStorage(Paths.get(filePath))
                    .saveAppointmentBook(appointmentBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(new AppointmentBook(), null));
    }
}
