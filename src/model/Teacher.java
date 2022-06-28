package model;

import java.util.Objects;

public class Teacher extends Person{
    public static final String TYPE = "TEACHER";
    private String subject;

    public Teacher(long pesel, String firstName, String lastName, String status,
                   String subject) {
        super(pesel, firstName, lastName, status);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toCsv() {
        return  TYPE + ";" +
                getPesel() + ";" +
                getFirstName() + ";" +
                getLastName() + ";" +
                getStatus() + ";" +
                getSubject();
    }
    @Override

    public String toString() {
        return "Teacher{" +
                super.toString() +
                "subject='" + subject + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(subject, teacher.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject);
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
