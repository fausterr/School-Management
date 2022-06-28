package io.file;

import exception.DataExportException;
import exception.DataImportException;
import model.Library;
import model.StaffRepository;

import java.io.*;

public class SerializableFileManager implements FileManager{
    private static final String PERSON_REPOSITORY = "person_repository.lib";
    private static final String BOOK_REPOSITORY = "book_repository.lib";

    @Override
    public StaffRepository importStaffRepoData() {
        try (FileInputStream fis = new FileInputStream(PERSON_REPOSITORY);
             ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (StaffRepository) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("no file " + PERSON_REPOSITORY);
        } catch (IOException e) {
            throw new DataImportException("import data error");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("incompatible data type");
        }
    }

    @Override
    public Library importLibraryData() {
        try (FileInputStream fis = new FileInputStream(BOOK_REPOSITORY);
             ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (Library) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("no file " + BOOK_REPOSITORY);
        } catch (IOException e) {
            throw new DataImportException("import data error");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("incompatible data type");
        }
    }

    @Override
    public void exportData(StaffRepository staffRepository, Library library) {
        try (FileOutputStream fos = new FileOutputStream(PERSON_REPOSITORY);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(staffRepository);
        } catch (FileNotFoundException e) {
            throw new DataExportException("no file " + PERSON_REPOSITORY);
        } catch (IOException e) {
            throw new DataExportException("export data error");
        }
    }
}
