package team.baymax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.baymax.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.baymax.commons.exceptions.IllegalValueException;
import team.baymax.commons.util.JsonUtil;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.storage.patient.JsonSerializablePatientManager;
import team.baymax.testutil.patient.TypicalPatients;

public class JsonSerializablePatientManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableBaymaxTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatients.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatient.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatients.json");

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializablePatientManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializablePatientManager.class).get();
        PatientManager patientManagerFromFile = dataFromFile.toModelType();
        PatientManager typicalPatientsPatientManager = TypicalPatients.getTypicalPatientManager();
        assertEquals(patientManagerFromFile, typicalPatientsPatientManager);
    }

    @Test
    public void toModelType_invalidPatientsFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientManager dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializablePatientManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializablePatientManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePatientManager.MESSAGE_DUPLICATE_PATIENT,
                dataFromFile::toModelType);
    }

}
