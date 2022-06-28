package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryUser extends User {

    private List<Book> bookHistory = new ArrayList<>();
    private List<Book> borrowedBooks = new ArrayList<>();

    public LibraryUser(long pesel, String login, String password) {
        super(pesel, login, password);
    }

    public List<Book> getBookHistory() {
        return bookHistory;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBookToHistory(Book book) {
        bookHistory.add(book);
    }

    public void addBookToBorrowed(Book book) {
        borrowedBooks.add(book);
    }

    @Override
    public String toCsv() {
        return getPesel() + ";" + getLogin() + ";" + getPassword();
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
