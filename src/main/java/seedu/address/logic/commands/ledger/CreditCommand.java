package seedu.address.logic.commands.ledger;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Increases the value of balance in the ledger
 */

public class CreditCommand extends Command {

    public static final String COMMAND_WORD = "credit";

    public static final String MESSAGE_CREDIT_ACCOUNT_SUCCESS = "New amount: %1$s";

    private final DateLedger dateLedger;

    private final Double toAdd;

    public CreditCommand (DateLedger date, Double amount) {
        requireNonNull(date);
        requireNonNull(amount);
        toAdd = amount;
        dateLedger = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Ledger> lastShownList = model.getFilteredLedgerList();

        Ledger ledgerToEdit = new Ledger(dateLedger, new Account(toAdd));
        Ledger editedLedger;

        for (int i = 0; i < lastShownList.size(); i++) {
            Ledger compare = lastShownList.get(i);
            if (compare.getDateLedger().getDate().equals(dateLedger.getDate())){
                ledgerToEdit = compare;
            } else {
                throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_DATE);
            }
        }

        ledgerToEdit.getAccount().credit(toAdd);

        editedLedger = new Ledger(dateLedger, ledgerToEdit.getAccount());

        model.updateLedger(ledgerToEdit, editedLedger);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_CREDIT_ACCOUNT_SUCCESS, ledgerToEdit.getAccount()));
    }
}
