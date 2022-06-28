package model;

import java.util.Objects;

public class Student extends Person{
    public static final String TYPE = "STUDENT";
    private String className;

    public Student(long pesel, String firstName, String lastName, String status,
                    String className) {
        super(pesel, firstName, lastName, status);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toCsv() {
        return  TYPE + ";" +
                getPesel() + ";" +
                getFirstName() + ";" +
                getLastName() + ";" +
                getStatus() + ";" +
                getClassName();
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                ", className='" + className + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(className, student.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), className);
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
