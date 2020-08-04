package org.example;

import java.io.Serializable;

public class UserQuiz implements Serializable,Comparable<UserQuiz> {
    private String login;
    private String password;
    private String dateOfBirth;

    public UserQuiz(String login, String password, String dateOfBirth) {
        this.login = login;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserQuiz{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }

    @Override
    public int compareTo(UserQuiz o) {
        return login.compareTo(o.login);
    }
}
