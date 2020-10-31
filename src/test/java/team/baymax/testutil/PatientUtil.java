package team.baymax.testutil;

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
