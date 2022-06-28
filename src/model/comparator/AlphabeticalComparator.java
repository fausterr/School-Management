package model.comparator;

import model.Person;

import java.util.Comparator;

public class AlphabeticalComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        if(p1 == null && p2 == null)
            return 0;
        else if(p1 == null)
            return 1;
        else if (p2 == null)
            return -1;
        return p1.getLastName().compareToIgnoreCase(p2.getLastName());
    }
}
