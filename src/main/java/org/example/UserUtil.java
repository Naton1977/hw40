package org.example;

import java.util.Scanner;

public class UserUtil {
    private static UserUtil instance;
    private static String login;
    private static String password;

    private UserUtil() {
    }


    public static UserUtil getInstance() {
        Scanner scanner = new Scanner(System.in);
        if (instance == null) {
            instance = new UserUtil();
            System.out.println("Введите login");
            login = scanner.nextLine();
            System.out.println("Введите password");
            password = scanner.nextLine();
        }
        return instance;
    }


    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        UserUtil.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserUtil.password = password;
    }

    @Override
    public String toString() {
        return "UserUtil{" +
                "user='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
