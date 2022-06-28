package app;

import exception.NoSuchOptionException;

public enum Option {

    EXIT(0,"exit"),
    ADD_STUDENT(1,"add student"),
    ADD_TEACHER(2,"add teacher"),
    ADD_BOOK(3, "add book"),
    PRINT_STUDENTS(4,"print all students"),
    PRINT_USERS(5, "print users"),
    PRINT_TEACHERS(6, "print all teachers"),
    PRINT_BOOKS(7, "print all boks"),
    DELETE_STUDENT(8, "delete student"),
    DELETE_TEACHER(9, "delete teacher"),
    DELETE_BOOK(10, "delete book"),
    GO_TO_LIBRARY(11, "for students: go to library"),
    GO_TO_ADMIN_PANEL(12, "admin panel, required permission"),
    LEND_BOOK(13, "lend book"),
    PRINT_MY_BOOKS(14, "print my books"),
    RETURN_BOOK(15, "return book"),
    PRINT_MY_HISTORY(16, "print my history");


    private int value;
    private String description;

    Option(int value, String description) {
        this.value = value;
        this.description = description;
    }

    static Option createFromInt(int option) throws NoSuchOptionException{
        try {
            return Option.values()[option];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("\n" + "there is no such option");
        }
    }

    @Override
    public String toString() {
        return "Option{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
