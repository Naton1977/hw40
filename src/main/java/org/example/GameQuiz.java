package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameQuiz implements Serializable {
    Scanner scanner = new Scanner(System.in);
    List<UserQuiz> listUserQuiz = new ArrayList<>();

    private String login;
    private String password;
    private String dateOfBirth;
    private String status;
    private int statusInt;
    private boolean exitTrue;
    UserQuiz userQuiz;


    public void game() throws IOException {

        Menu menu = new Menu("Главное меню");

        Menu menuP_1 = new Menu("Зарегистрировать нового пользователя", context -> {
            String fileName = "UserQuiz";
            File file = new File(fileName);
            if(file.exists()){
                readObjectUserQuiz(fileName);
            }
            do {
                System.out.println("Введите логин");
                System.out.println("exit - выход");
                login = scanner.nextLine();
                if("exit".equals(login)){
                    break;
                }
                System.out.println("Введите password");
                password = scanner.nextLine();
                System.out.println("Введите дату рождения");
                dateOfBirth = scanner.nextLine();
                userQuiz = new UserQuiz(login, password, dateOfBirth);
                if (isPresent(userQuiz)) {
                    listUserQuiz.add(userQuiz);
                    System.out.println("Пользователь с логином : " + login + " добавлен !");
                    break;
                }
            } while (true);
            saveObjectUserQuiz(listUserQuiz, "UserQuiz");
        });
        Menu menuP_2 = new Menu("Вход зарегистрированного пользователя");

        menu.addSubMenu(menuP_1);
        menu.addSubMenu(menuP_2);
        menu.print();
        menu.action(action());


    }


    public boolean isPresent(UserQuiz userQuiz){
        for (int i = 0; i < listUserQuiz.size(); i++) {
            if(listUserQuiz.get(i).getLogin().equals(userQuiz.getLogin())){
                System.out.println("Такой логин уже существует ");
                return false;
            }
        }
        return true;
    }


    private static List <UserQuiz> readObjectUserQuiz(String fileName) throws FileNotFoundException {
        List <UserQuiz> list = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            list = (List<UserQuiz>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void saveObjectUserQuiz(List<UserQuiz> userQuiz, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userQuiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int action(){
        do {
            System.out.println("exit -  выход");
            status = scanner.nextLine();
            if ("exit".equals(status)) {
                exitTrue = true;
                break;
            }
            try {
                statusInt = Integer.parseInt(status);
                break;
            } catch (Exception e) {
                System.out.println("Введите правильно число");
            }
        } while (true);
        return statusInt;
    }

}
