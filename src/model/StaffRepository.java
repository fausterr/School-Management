package model;

import exception.PersonAlreadyExistException;
import exception.UserAlreadyExistException;

import java.io.Serializable;
import java.util.*;

public class StaffRepository implements Serializable {

    private Map<Long,Person> persons = new HashMap<>();
    private Map<Long, LibraryUser> libraryUsers = new HashMap<>();

    public Map<Long,Person> getPersons() {
        return persons;
    }

    public Map<Long, LibraryUser> getLibraryUsers() {
        return libraryUsers;
    }

    public void addPerson(Person person) {
        if(persons.containsValue(person.getPesel()))
            throw new PersonAlreadyExistException("person already exists");
        persons.put(person.getPesel(), person);
    }

    public boolean removePerson(Person person) {
        if(persons.containsValue(person)) {
            persons.remove(person.getPesel());
            return true;
        } else {
            return false;
        }
    }

    public void addLibraryUser(LibraryUser user) {
        if(libraryUsers.containsKey(user.getPesel()))
            throw new UserAlreadyExistException("user already exists");
        libraryUsers.put(user.getPesel(), user);
    }

    public boolean removeLibraryUser(LibraryUser user) {
        if(libraryUsers.containsValue(user)) {
            libraryUsers.remove(user.getPesel());
            return true;
        } else {
            return false;
        }
    }

    public Collection<Person> getAndSortPersons(Comparator<Person> comparator) {
        ArrayList<Person> list = new ArrayList<>(this.persons.values());
        list.sort(comparator);
        return list;
    }

    public Collection<LibraryUser> getAndSortLibraryUsers(Comparator<LibraryUser> comparator) {
        ArrayList<LibraryUser> list = new ArrayList<>(this.libraryUsers.values());
        list.sort(comparator);
        return list;
    }


}
