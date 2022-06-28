package io.file;


import exception.DataExportException;
import exception.DataImportException;
import exception.InvalidDataException;
import model.*;

import java.io.*;
import java.util.*;

public class CsvFileManager implements FileManager {

    private static final String STAFF_REPOSITORY_FILE = "StaffRepository.csv";
    private static final String USERS_REPOSITORY_FILE = "LibraryUsersRepository.csv";
    private static final String LIBRARY_FILE = "Library.csv";
    private static final String USERS_REPO_BORROWED_FILE = "UsersRepoBorrowed.csv";
    private static final String USERS_REPO_HISTORY_FILE = "UsersRepoHistory.csv";

    @Override
    public void exportData(StaffRepository staffRepository, Library library) {
        exportPersons(staffRepository);
        exportLibraryUsers(staffRepository);
        exportLibrary(library);
        exportLibraryUserBooksHistory(staffRepository);
        exportLibraryUserBorrowedBooks(staffRepository);
    }

    @Override
    public StaffRepository importStaffRepoData() {
        StaffRepository staffRepository = new StaffRepository();
        importPersons(staffRepository);
        importLibraryUsers(staffRepository);
        importUserBorrowedBooks(staffRepository);
        importUserHistoryBooks(staffRepository);
        return staffRepository;
    }

    @Override
    public Library importLibraryData() {
        Library library = new Library();
        importBooks(library);
        return library;
    }

    private void exportLibraryUsers(StaffRepository staffRepository) {
        Collection<LibraryUser> libraryUsers = staffRepository.getLibraryUsers().values();
        exportToCsv(libraryUsers, USERS_REPOSITORY_FILE);
    }

    private void exportPersons(StaffRepository staffRepository) {
        Collection<Person> persons = staffRepository.getPersons().values();
        exportToCsv(persons, STAFF_REPOSITORY_FILE);
    }

    private void exportLibrary(Library library) {
        Collection<Book> books = library.getBooks().values();
        exportToCsv(books, LIBRARY_FILE);
    }

    private void exportLibraryUserBorrowedBooks(StaffRepository staffRepository) {
        Collection<LibraryUser> libraryUsers = staffRepository.getLibraryUsers().values();
        try(FileWriter fileWriter = new FileWriter(USERS_REPO_BORROWED_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for(LibraryUser libraryUser: libraryUsers) {
                    List<Book> books = libraryUser.getBorrowedBooks();
                    for(Book book: books) {
                        bufferedWriter.write(libraryUser.getPesel() + ";" + book.toCsv());
                    }
                }
        } catch (IOException e) {
            throw new DataExportException("Failed to save the file " + USERS_REPO_BORROWED_FILE);
        }
    }

    private void exportLibraryUserBooksHistory(StaffRepository staffRepository) {
        Collection<LibraryUser> libraryUsers = staffRepository.getLibraryUsers().values();
        try(FileWriter fileWriter = new FileWriter(USERS_REPO_HISTORY_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for(LibraryUser libraryUser: libraryUsers) {
                    List<Book> books = libraryUser.getBookHistory();
                    for(Book book: books) {
                        bufferedWriter.write(libraryUser.getPesel() + ";" + book.toCsv());
                    }
                }
        } catch (IOException e) {
            throw new DataExportException("Failed to save the file " + USERS_REPO_HISTORY_FILE);
        }
    }

    private void importUserBorrowedBooks(StaffRepository staffRepository) {
        Collection<LibraryUser> libraryUsers = staffRepository.getLibraryUsers().values();
        try(Scanner fileReader = new Scanner(new File(USERS_REPO_BORROWED_FILE))) {
            while (fileReader.hasNextLine()) {
                String[] splitLine = fileReader.nextLine().split(";");
                Long userPesel = Long.valueOf(splitLine[0]);
                String[] bookSplitLine = new String[splitLine.length -1];
                System.arraycopy(splitLine, 1, bookSplitLine, 0, splitLine.length - 1);
                Book bookToAdd = createUserBookFromString(bookSplitLine);
                for(LibraryUser libraryUser: libraryUsers) {
                    if(userPesel.equals(libraryUser.getPesel())) {
                        libraryUser.getBorrowedBooks().add(bookToAdd);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + USERS_REPO_BORROWED_FILE);
        }
    }

    private void importUserHistoryBooks(StaffRepository staffRepository) {
        Collection<LibraryUser> libraryUsers = staffRepository.getLibraryUsers().values();
        try(Scanner fileReader = new Scanner(new File(USERS_REPO_HISTORY_FILE))) {
            while (fileReader.hasNextLine()) {
                String[] splitLine = fileReader.nextLine().split(";");
                Long userPesel = Long.valueOf(splitLine[0]);
                String[] bookSplitLine = new String[splitLine.length - 1];
                System.arraycopy(splitLine, 1, bookSplitLine, 0, splitLine.length - 1);
                Book bookToAdd = createUserBookFromString(bookSplitLine);
                for(LibraryUser libraryUser: libraryUsers) {
                    if(userPesel.equals(libraryUser.getPesel())) {
                        libraryUser.getBookHistory().add(bookToAdd);
                        System.out.println("Added book to user with pesel: " + userPesel);
                    } else {
                        System.out.println("wrong");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + USERS_REPO_HISTORY_FILE);
        }
    }

    private void importPersons(StaffRepository staffRepository) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(STAFF_REPOSITORY_FILE))) {
            bufferedReader.lines()
                    .map(this::createPersonFromString)
                    .forEach(staffRepository::addPerson);
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file" + STAFF_REPOSITORY_FILE);
        } catch (IOException e) {
            throw new DataImportException("Failed to save the file " + STAFF_REPOSITORY_FILE);
        }
    }

    private void importLibraryUsers(StaffRepository staffRepository) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_REPOSITORY_FILE))) {
            bufferedReader.lines()
                    .map(this::createLibraryUserFromString)
                    .forEach(staffRepository::addLibraryUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + USERS_REPOSITORY_FILE);
        } catch (IOException e) {
            throw new DataImportException("Failed to save the file " + USERS_REPOSITORY_FILE);
        }
    }

    private void importBooks(Library library) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(LIBRARY_FILE))) {
            bufferedReader.lines()
                    .map(this::createBookFromString)
                    .forEach(library::addBook);
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + LIBRARY_FILE);
        } catch (IOException e) {
            throw new DataImportException("Failed to save the file " + LIBRARY_FILE);
        }
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try(FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for(T t: collection) {
                    bufferedWriter.write(t.toCsv());
                    bufferedWriter.newLine();
                }
        } catch (IOException e) {
            throw new DataExportException("Failed to save the file " + fileName);
        }
    }

    public Person createPersonFromString(String csvText) {
        String [] split = csvText.split(";");
        String type = split[0];
        if(Student.TYPE.equals(type)) {
            return createStudentFromString(split);
        } else if (Teacher.TYPE.equals(type)) {
            return createTeacherFromString(split);
        }
        throw new InvalidDataException("unknown type person: " + type);
    }

    private LibraryUser createLibraryUserFromString(String csvText) {
        String [] split = csvText.split(";");
        long pesel =  Long.valueOf(split[0]);
        String login = split[1];
        String password = split[2];
        return new LibraryUser(pesel, login, password);  //zmiana
    }

    private Person createTeacherFromString(String[] split) {
        long pesel = Long.valueOf(split[1]);
        String firstName = split[2];
        String lastName = split[3];
        String status = split[4];
        String className = split[5];
        return new Student(pesel, firstName, lastName, status, className);
    }

    private Person createStudentFromString(String[] split) {
        long pesel = Long.valueOf(split[1]);
        String firstName = split[2];
        String lastName = split[3];
        String status = split[4];
        String subject = split[5];
        return new Teacher(pesel, firstName, lastName, status, subject);
    }

    private Book createBookFromString(String csvText) {
        String[] split = csvText.split(";");
        long isbn = Long.valueOf(split[0]);
        String title = split[1];
        String author = split[2];
        int pages = Integer.valueOf(split[3]);
        return new Book(isbn, title, author, pages);
    }

    private Book createUserBookFromString(String[] text) {
        long isbn = Long.valueOf(text[0]);
        String title = text[1];
        String author = text[2];
        int pages = Integer.valueOf(text[3]);
        return new Book(isbn, title, author, pages);
    }
}
