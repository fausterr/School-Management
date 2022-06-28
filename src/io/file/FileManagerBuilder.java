package io.file;

import exception.NoSuchFileTypeException;
import io.DataReader;

public class FileManagerBuilder {
    private DataReader reader;
    public FileManagerBuilder(DataReader reader) {
        this.reader = reader;
    }
    public FileManager build() {
        System.out.println("Select the data format:");
        FileType fileType = getFileType();
        switch (fileType) {
            case CSV:
                return new CsvFileManager();
            case SERIAL:
                return new SerializableFileManager();
            default:
                throw new NoSuchFileTypeException("Unsupported data type");
        }
    }
    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Unsupported data type");
            }
        } while (!typeOk);

        return result;
    }
    private void printTypes() {
        for (FileType value : FileType.values()) {
            System.out.println(value);
        }
    }
}