package team.baymax.logic.parser.general;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.general.SwitchCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;

public class SwitchCommandParser implements Parser<SwitchCommand> {

    @Override
    public SwitchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SwitchCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchCommand.MESSAGE_INVALID_TAB_NUMBER), pe);
        }
    }
}
