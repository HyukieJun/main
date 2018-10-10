package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Member;

/**
 * Represents a selection change in the Member List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Member newSelection;

    public PersonPanelSelectionChangedEvent(Member newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Member getNewSelection() {
        return newSelection;
    }
}
