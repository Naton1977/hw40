package org.example;

import java.io.Serializable;
import java.util.Scanner;

public class UserUtil implements Serializable {
    private static UserUtil instance;
    private  String login;
    private  String password;

    private UserUtil() {
    }


    public static UserUtil getInstance() {
        if (instance == null) {
            instance = new UserUtil();
        }
        return instance;
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
    public String toString() {
        return "UserUtil{" +
                "user='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
