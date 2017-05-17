package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SelectCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        try {
            int index = ParserUtil.parseIndex(args);
            return new SelectCommand(index);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }

}