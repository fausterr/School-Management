package io;

import model.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ConsolePrinter {

    public void printStudents(Collection<Person> persons)  {
        long count = persons.stream()
                .filter(p -> p instanceof Student)
                .map(Person::toString)
                .peek(this::printLine)
                .count();
        if(count == 0)
            System.out.println("no students");
    }

    public void printTeachers(Collection<Person> persons) {
        long count = persons.stream()
                .filter(p -> p instanceof  Teacher)
                .map(Person::toString)
                .peek(this::printLine)
                .count();
        if(count == 0)
            System.out.println("no teachers");
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(LibraryUser::toString)
                .forEach(this::printLine);
    }

    public void printBooks(Collection<Book> books) {
        books.stream()
                .map(Book::toString)
                .forEach(this::printLine);
    }

    public void printBorrowedBooks(LibraryUser user) {
       List<Book> books = user.getBorrowedBooks();
       books.stream()
               .map(Book::toString)
               .forEach(this::printLine);
    }

    public void printBookHistory(LibraryUser user) {
        List<Book> books = user.getBookHistory();
        books.stream()
                .map(Book::toString)
                .forEach(this::printLine);
    }

    private void printLine(String s) {
        System.out.println(s);
    }
}
