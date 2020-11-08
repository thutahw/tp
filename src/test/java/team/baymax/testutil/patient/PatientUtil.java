package team.baymax.testutil.patient;

import static team.baymax.logic.parser.CliSyntax.PREFIX_GENDER;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_PHONE;
import static team.baymax.logic.parser.CliSyntax.PREFIX_REMARK;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import team.baymax.logic.commands.patient.AddPatientCommand;
import team.baymax.logic.commands.patient.EditPatientCommand;
import team.baymax.logic.parser.CliSyntax;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;

/**
 * A utility class for Patient.
 */
public class PatientUtil {

    public static final String VALID_NRIC_AMY = "T1234567A";
    public static final String VALID_NRIC_BOB = "S3322115E";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "98763522";
    public static final String VALID_PHONE_BOB = "81763222";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_TAG_DIABETIC = "Diabetic";
    public static final String VALID_TAG_LTP = "LTP";
    public static final String VALID_REMARK_AMY = "Allergic to ibuprofen.";
    public static final String VALID_REMARK_BOB = "Only free on weekends.";

    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String TAG_DESC_LTP = " " + PREFIX_TAG + VALID_TAG_LTP;
    public static final String TAG_DESC_DIABETIC = " " + PREFIX_TAG + VALID_TAG_DIABETIC;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "F!"; // neither 'F' nor 'M'
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Diabetic*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPatientCommand.EditPatientDescriptor DESC_AMY;
    public static final EditPatientCommand.EditPatientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPatientDescriptorBuilder()
                .withNric(VALID_NRIC_AMY)
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withGender(VALID_GENDER_AMY)
                .withTags(VALID_TAG_LTP)
                .withRemark(VALID_REMARK_AMY)
                .build();
        DESC_BOB = new EditPatientDescriptorBuilder()
                .withNric(VALID_NRIC_BOB)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_DIABETIC, VALID_TAG_LTP)
                .withRemark(VALID_REMARK_BOB)
                .build();
    }

    /**
     * Returns an add command string for adding the {@code patient}.
     */
    public static String getAddCommand(Patient patient) {
        return AddPatientCommand.COMMAND_WORD + " " + getPatientDetails(patient);
    }

    /**
     * Returns the part of command string for the given {@code patient}'s details.
     */
    public static String getPatientDetails(Patient patient) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NRIC + patient.getNric().getValue() + " ");
        sb.append(CliSyntax.PREFIX_NAME + patient.getName().getFullName() + " ");
        sb.append(CliSyntax.PREFIX_PHONE + patient.getPhone().getValue() + " ");
        sb.append(CliSyntax.PREFIX_GENDER + patient.getGender().getValue() + " ");
        sb.append(CliSyntax.PREFIX_REMARK + patient.getRemark().getValue() + " ");
        patient.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPatientDescriptor}'s details.
     */
    public static String getEditPatientDescriptorDetails(EditPatientCommand.EditPatientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getNric().ifPresent(nric -> sb.append(CliSyntax.PREFIX_NRIC)
                .append(nric.getValue()).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME)
                .append(name.getFullName()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE)
                .append(phone.getValue()).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(CliSyntax.PREFIX_GENDER)
                .append(gender.getValue())
                .append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(CliSyntax.PREFIX_REMARK)
                .append(remark.getValue()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
