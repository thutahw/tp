package team.baymax.logic.parser.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_GENDER;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_PHONE;
import static team.baymax.logic.parser.CliSyntax.PREFIX_REMARK;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.ParserUtil.parseTagsForEdit;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.patient.EditPatientCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditPatientCommandParser implements Parser<EditPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPatientCommand
     * and returns an EditPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPatientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER, PREFIX_TAG,
                        PREFIX_REMARK, PREFIX_NRIC);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPatientCommand.MESSAGE_USAGE), pe);
        }

        EditPatientCommand.EditPatientDescriptor editPatientDescriptor = new EditPatientCommand.EditPatientDescriptor();

        // edit nric
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPatientDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }

        // edit name
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        // edit phone number
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        // edit gender
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPatientDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }

        // edit remark
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editPatientDescriptor.setRemark(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        // edit tags
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPatientDescriptor::setTags);

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPatientCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPatientCommand(index, editPatientDescriptor);
    }

}
