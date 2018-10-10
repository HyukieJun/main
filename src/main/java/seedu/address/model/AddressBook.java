package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Member;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setPersons(List<Member> members) {
        this.persons.setPersons(members);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    public boolean hasPerson(Member member) {
        requireNonNull(member);
        return persons.contains(member);
    }

    /**
     * Adds a member to the address book.
     * The member must not already exist in the address book.
     */
    public void addPerson(Member p) {
        persons.add(p);
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the address book.
     * The member identity of {@code editedMember} must not be the same as another existing member in the address book.
     */
    public void updatePerson(Member target, Member editedMember) {
        requireNonNull(editedMember);

        persons.setPerson(target, editedMember);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Member key) {
        persons.remove(key);
    }
    /**
     * Removes {@code tag} from {@code member} in this {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Member member) {

        Set<Tag> newTags = new HashSet<>(member.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Member newMember = new Member(member.getName(),
                member.getPhone(), member.getEmail(), member.getAddress(), newTags);

        updatePerson(member, newMember);
    }

    public void removeTag (Tag tag) {
        persons.forEach(person -> removeTagFromPerson(tag, person));
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Member> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
