package team.baymax.logic.parser.patient;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import team.baymax.logic.commands.patient.FindPatientCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.patient.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindPatientCommandParser implements Parser<FindPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPatientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPatientCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
