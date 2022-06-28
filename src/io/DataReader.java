package io;

import model.Book;
import model.Student;
import model.Teacher;

import java.util.Scanner;

public class DataReader {

    private Scanner sc = new Scanner(System.in);
    public void closeScanner() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }

    public Student createStudent() {
        System.out.println("pesel");
        long pesel = sc.nextLong();
        sc.nextLine();
        System.out.println("first name");
        String firstName = sc.nextLine();
        System.out.println("last name");
        String lastName = sc.nextLine();
        System.out.println("status");
        String status = sc.nextLine();
        System.out.println("class name");
        String className = sc.nextLine();
        return new Student(pesel, firstName, lastName, status,  className);
    }

    public Teacher createTeacher() {
        System.out.println("pesel");
        long pesel = sc.nextLong();
        sc.nextLine();
        System.out.println("first name");
        String firstName = sc.nextLine();
        System.out.println("last name");
        String lastName = sc.nextLine();
        System.out.println("status");
        String status = sc.nextLine();
        System.out.println("subject");
        String subject = sc.nextLine();
        return new Teacher(pesel, firstName, lastName, status, subject);
    }

    public Book createBook() {
        System.out.println("isbn");
        long isbn = sc.nextLong();
        System.out.println("title");
        String title = sc.nextLine();
        System.out.println("author");
        String author = sc.nextLine();
        System.out.println("quantity");
        int quantity = sc.nextInt();
        return new Book(isbn, title, author, quantity);
    }

    public Scanner getScanner() {
        return sc;
    }
}
