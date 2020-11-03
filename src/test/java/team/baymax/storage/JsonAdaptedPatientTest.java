package team.baymax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.TypicalPatients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import team.baymax.commons.exceptions.IllegalValueException;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Phone;
import team.baymax.storage.patient.JsonAdaptedPatient;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NRIC = "Y0123456A";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GENDER = "gender is a social construct";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(INVALID_NRIC, VALID_NAME, VALID_PHONE, VALID_GENDER, VALID_TAGS, VALID_REMARK);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(null, VALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_TAGS, VALID_REMARK);
        String expectedMessage = String.format(JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
                Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NRIC, INVALID_NAME, VALID_PHONE, VALID_GENDER, VALID_TAGS, VALID_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NRIC, null, VALID_PHONE, VALID_GENDER,
                VALID_TAGS, VALID_REMARK);
        String expectedMessage = String.format(JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NRIC, VALID_NAME, INVALID_PHONE, VALID_GENDER, VALID_TAGS, VALID_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NRIC, VALID_NAME, null, VALID_GENDER,
                VALID_TAGS, VALID_REMARK);
        String expectedMessage = String.format(JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NRIC, VALID_NAME, VALID_PHONE, INVALID_GENDER, VALID_TAGS, VALID_REMARK);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NRIC, VALID_NAME, VALID_PHONE, null,
                VALID_TAGS, VALID_REMARK);
        String expectedMessage = String.format(JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
                Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_GENDER, invalidTags, VALID_REMARK);
        assertThrows(IllegalValueException.class, patient::toModelType);
    }

}
