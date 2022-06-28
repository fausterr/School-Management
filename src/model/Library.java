package model;

import exception.BookAlreadyExistException;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Library implements Serializable {

    private Map<Long, Book> books = new HashMap<>();
    private Map<Long, Book> bookHistory  = new HashMap<>();
    private Map<Long, Book> borrowedBooks = new HashMap<>();

    public Map<Long, Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        if(books.containsKey(book.getIsbn()))
            throw new BookAlreadyExistException("there is already such a book");
        books.put(book.getIsbn(), book);
    }

    public boolean removeBook(Book book) {
        if(books.containsValue(book)) {
            books.remove(book.getIsbn());
            return true;
        } else {
            return false;
        }

    } public boolean updateBook(Book book) {
        Book newBook = book;
        if(books.containsValue(book)) {
            books.replace(book.getIsbn(), newBook);
            return true;
        } else {
            return false;
        }
    }
}
