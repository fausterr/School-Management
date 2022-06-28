package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Serializable, Comparable<Person>, CsvConvertible {
    private long pesel;
    private String firstName;
    private String lastName;
    private String status;

    public Person(long pesel, String firstName, String lastName, String status) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public long getPesel() { return pesel; }

    public void setPesel(long pesel) { this.pesel = pesel; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pesel=" + pesel +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return pesel == person.pesel &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(status, person.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel, firstName, lastName, status);
    }

    @Override
    public int compareTo(Person p) {
        return lastName.compareToIgnoreCase(p.lastName);
    }
}
