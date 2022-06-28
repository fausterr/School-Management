package app;

import exception.NoSuchOptionException;

public enum EntryOption {

    EXIT(0, "exit"),
    GO_TO_LIBRARY(1, "for students: go to library"),
    GO_TO_ADMIN_PANEL(2, "admin panel, required permission");

    private int value;
    private String description;

    EntryOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    static EntryOption createFromInt(int option) throws NoSuchOptionException {
        try {
            return EntryOption.values()[option];
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
