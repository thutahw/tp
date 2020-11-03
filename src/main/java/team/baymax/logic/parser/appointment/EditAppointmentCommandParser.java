package team.baymax.logic.parser.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.ParserUtil.parseTagsForEdit;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.EditAppointmentCommand;
import team.baymax.logic.commands.appointment.EditAppointmentDescriptor;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;

public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointmentCommand
     * and returns an EditAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_TAG, PREFIX_DURATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editAppointmentDescriptor.setDateTime(ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_DATETIME).get()));
        }

        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editAppointmentDescriptor.setDuration(ParserUtil.parseDuration(
                    argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editAppointmentDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAppointmentDescriptor::setTags);

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(index, editAppointmentDescriptor);
    }
}
