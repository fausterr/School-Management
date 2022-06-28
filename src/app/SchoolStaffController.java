package app;

import exception.DataExportException;
import exception.DataImportException;
import exception.InvalidDataException;
import exception.NoSuchOptionException;
import io.ConsolePrinter;
import io.DataReader;
import io.file.FileManager;
import io.file.FileManagerBuilder;
import model.*;

import java.util.*;

public class SchoolStaffController {

    private DataReader dataReader = new DataReader();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private FileManager fileManager;
    private StaffRepository staffRepository;
    private Library library;
    private String currentUser;
    private long currentUserPesel;

    SchoolStaffController() {
        fileManager = new FileManagerBuilder(dataReader).build();
        try {
            staffRepository = fileManager.importStaffRepoData();
            library = fileManager.importLibraryData();
            System.out.println("data from a file has been imported");
        } catch (DataImportException | InvalidDataException e) {
            System.out.println(e.getMessage());
            System.out.println("new base initiated");
            staffRepository = new StaffRepository();
            library = new Library();
        }
    }

    public void entryLoop() {
        EntryOption option;
        do {
            printEntryOptions();
            option = getEntryOption();
            switch (option) {
                case GO_TO_ADMIN_PANEL:
                    adminLogin();
                    break;
                case GO_TO_LIBRARY:
                    userLogin();
                    break;
                case EXIT:
                    exit();
                    break;
            }
        } while (option != EntryOption.EXIT);
    }

    private void userLogin() {
        System.out.println("login: ");
        String login = dataReader.getScanner().nextLine();
        System.out.println("password: ");
        String password = dataReader.getScanner().nextLine();
        for(LibraryUser libraryUser: staffRepository.getLibraryUsers().values()) {
            if(libraryUser.getLogin().equals(login)) {
                if(libraryUser.getPassword().equals(password)) {
                    System.out.println("ok thats you!");
                    //currentUser = login.split("\\.");
                    currentUser = login;
                    currentUserPesel = libraryUser.getPesel();
                    userControlLoop();
                } else {
                    System.out.println("wrong password");
                }
            } else {
                System.out.println("wrong login");
            }
        }
    }

    private void adminLogin() {
        System.out.println("login:");
        String login = dataReader.getScanner().nextLine();
        System.out.println("password:");
        String password = dataReader.getScanner().nextLine();
        if(login.equals("admin") && password.equals("admin")) {
            System.out.println("ok");
            adminControlLoop();
        } else {
            System.out.println("wrong");
        }
    }
//-------------------------------------------------------------------

    public void userControlLoop() {

        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case LEND_BOOK:
                    lendBook();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MY_BOOKS:
                    printMyBooks();
                    break;
                case RETURN_BOOK:
                    returnBook();
                    break;
                case PRINT_MY_HISTORY:
                    printMyHistory();
                    break;
//                case EXIT:
//                    exit();
//                    break;
            }

        } while (option != Option.EXIT);
    }

    private void lendBook() {
        System.out.println("ISBN");
        long isbn = dataReader.getScanner().nextLong();
        LibraryUser user = staffRepository.getLibraryUsers().get(currentUserPesel);
        Book bookToUpdate = library.getBooks().get(isbn);
        bookToUpdate.setQuantity(bookToUpdate.getQuantity() - 1);
        Book bookToLend = new Book(bookToUpdate.getIsbn(), bookToUpdate.getAuthor(), bookToUpdate.getTitle(), 1);
        user.getBorrowedBooks().add(bookToLend);
    }

    private void returnBook() {
        System.out.println("ISBN");
        Long isbn = dataReader.getScanner().nextLong();
        LibraryUser user = staffRepository.getLibraryUsers().get(currentUserPesel);
        Book bookToUpdate = library.getBooks().get(isbn);
        bookToUpdate.setQuantity(bookToUpdate.getQuantity() + 1);

//        Book bookToReturn = user.getBorrowedBooks().get(isbn);
//        user.getBookHistory().put(user.getPesel(), bookToReturn);
//        user.getBorrowedBooks().remove(user.getPesel(), bookToReturn);
        Book bookToReturn = user.getBorrowedBooks().stream()
                .filter(book -> isbn.equals(book.getIsbn()))
                .findAny()
                .orElse(null);
        user.addBookToHistory(bookToReturn);
        user.getBorrowedBooks().remove(bookToReturn);
    }

    public void printMyBooks() {
        LibraryUser user = staffRepository.getLibraryUsers().get(currentUserPesel);
        consolePrinter.printBorrowedBooks(user);
    }

    private void printMyHistory() {
        LibraryUser user = staffRepository.getLibraryUsers().get(currentUserPesel);
        consolePrinter.printBookHistory(user);
    }

    //-------------------------------------------------------------------
    public void adminControlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_STUDENT:
                    addStudent();
                    break;
                case ADD_TEACHER:
                    addTeacher();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_STUDENTS:
                    printStudents();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case PRINT_TEACHERS:
                    printTeachers();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case DELETE_STUDENT:
                    deleteStudent();
                    break;
                case DELETE_TEACHER:
                    deleteTeacher();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
              case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("try again");
            }
        } while (option != Option.EXIT);
    }

    private void printEntryOptions() {
        System.out.println("select option:");
        for(EntryOption option: EntryOption.values())
            System.out.println(option.toString());
    }
    private void printOptions() {
        System.out.println("select option:");
        for(Option option: Option.values())
            System.out.println(option.toString());
    }

    private EntryOption getEntryOption() {
        boolean ok = false;
        EntryOption option = null;
        while (!ok) {
            try {
                option = EntryOption.createFromInt(dataReader.getInt());
                ok = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage() + ", try again");
            } catch (InputMismatchException e) {
                System.out.println("it is not a number!");
            }
        }
        return option;
    }

    private Option getOption() {
        boolean ok = false;
        Option option = null;
        while (!ok) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                ok = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage() + ", try again");
            } catch (InputMismatchException e) {
                System.out.println("it is not a number!");
            }
        }
        return option;
    }

    private void addStudent() {
        try {
            Student student = dataReader.createStudent();
            long pesel = student.getPesel();
            String userLogin = generateLogin(student);
            String userPassword = generatePassword();
            LibraryUser user = new LibraryUser(pesel, userLogin, userPassword); //zmiana
            staffRepository.addPerson(student);
            staffRepository.addLibraryUser(user);
            System.out.println("user login: " + userLogin + ", user pass: " + userPassword);
        } catch (InputMismatchException e) {
            System.out.println("incorrect data");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("exceeded the limit");
        }
    }

    private String generateLogin(Student student) {
        String login = student.getFirstName() + "." + student.getLastName();
        return login;
    }

    private String generatePassword() {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int passLength = 8;
        for(int i = 0; i < passLength; i ++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }
        String password = sb.toString();
        return password;
    }

    private void addTeacher() {
        try {
            Teacher teacher = dataReader.createTeacher();
            staffRepository.addPerson(teacher);
        } catch (InputMismatchException e) {
            System.out.println("incorrect data");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("exceeded the limit");
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.createBook();
            library.addBook(book);
        } catch (InputMismatchException e) {
            System.out.println("wrong data");
        }
    }

    private void printStudents() {
        consolePrinter.printStudents(staffRepository.getAndSortPersons(
                (p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName())
        ));
    }

    private void printUsers() {
        consolePrinter.printUsers(staffRepository.getAndSortLibraryUsers(
                (p1, p2) -> p1.getLogin().compareToIgnoreCase(p2.getLogin())
        ));
    }

    public void printTeachers() {
        consolePrinter.printTeachers(staffRepository.getPersons().values());
    }

    public void printBooks() {
        consolePrinter.printBooks(library.getBooks().values());
    }

    public void deleteStudent() {
        try {
            Student student = dataReader.createStudent();
            if(staffRepository.removePerson(student)) {
                System.out.println("student has been removed");
            } else {
                System.out.println("there is no such student");
            }
        } catch (InputMismatchException e) {
            System.out.println("wrong data");
        }
    }

    public void deleteTeacher() {
        try {
            Teacher teacher = dataReader.createTeacher();
            if(staffRepository.removePerson(teacher)) {
                System.out.println("teacher has been removed");
            } else {
                System.out.println("there is no such teacher");
            }
        } catch (InputMismatchException e) {
            System.out.println("wrong data");
        }
    }

    public void deleteBook() {
        try {
            Book book = dataReader.createBook();
            if(library.removeBook(book)) {
                System.out.println("book has been removed");
            } else {
                System.out.println("there is no such book");
            }
        } catch (InputMismatchException e) {
            System.out.println("wrong data");
        }
    }

    public void exit() {
        try {
            fileManager.exportData(staffRepository, library);
            System.out.println("successful data export");
        } catch (DataExportException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("end of program");
        dataReader.closeScanner();
    }
}
