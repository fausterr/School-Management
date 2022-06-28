package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable, CsvConvertible, Comparable<User> {

    private long pesel;
    private String login;
    private String password;

    public User(long pesel, String login, String password) {
        this.pesel = pesel;
        this.login = login;
        this.password = password;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pesel == user.pesel && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel, login, password);
    }
    @Override
    public int compareTo(User o) {
        return login.compareToIgnoreCase(o.getLogin());
    }

    @Override
    public String toString() {
        return "User{" +
                "pesel=" + pesel +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
