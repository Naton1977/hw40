package org.example;

import java.io.*;
import java.util.*;

public class GameQuiz implements Serializable {
    Scanner scanner = new Scanner(System.in);
    List<UserQuiz> listUserQuiz = new ArrayList<>();
    Map<UserQuiz, List<ResultUserQuiz>> userQuizResult = new TreeMap<>();
    Map<Quiz, List<Question>> quiz = new TreeMap<>();
    private String login;
    private String password;
    private String dateOfBirth;
    private String status;
    private int statusInt;
    private boolean loginUser;
    private boolean tapMenuP_2;
    private boolean exitTrue;
    private boolean tapMenuP1_2Lev1P5;
    UserQuiz userQuiz;
    public static String staticLoginUser;

    public void game() throws IOException {

        Menu menu = new Menu("Главное меню");

        Menu menuP_1 = new Menu("Зарегистрировать нового пользователя", context -> {
            String fileName = "UserQuiz";
            File file = new File(fileName);
            if (file.exists()) {
                listUserQuiz = readObjectUserQuiz(fileName);
            }
            String fileName1 = "UserQuizResult.dat";
            File file1 = new File(fileName1);
            if (file1.exists()) {
                userQuizResult = readObjectUserQuizResult(fileName1);
            }
            do {
                System.out.println("Введите логин");
                System.out.println("exit - выход");
                login = scanner.nextLine();
                if ("exit".equals(login)) {
                    break;
                }
                System.out.println("Введите password");
                password = scanner.nextLine();
                System.out.println("Введите дату рождения");
                dateOfBirth = scanner.nextLine();
                userQuiz = new UserQuiz(login, password, dateOfBirth);
                if (isPresent(userQuiz)) {
                    listUserQuiz.add(userQuiz);
                    userQuizResult.put(new UserQuiz(login, password, dateOfBirth), new ArrayList<>());
                    System.out.println("Пользователь с логином : " + login + " добавлен !");
                    break;
                }
            } while (true);
            saveObjectUserQuiz(listUserQuiz, "UserQuiz");
            saveObjectUserQuizResult(userQuizResult, "UserQuizResult.dat");
        });
        Menu menuP_2 = new Menu("Вход зарегистрированного пользователя", context -> {
            loginUser();
            if (loginUser) {
                tapMenuP_2 = true;
            }
        });

        menu.addSubMenu(menuP_1);
        menu.addSubMenu(menuP_2);
        do {
            menu.print();
            menu.action(action());
        } while (!tapMenuP_2);

        if (tapMenuP_2) {
            Menu menuP1_2Lev1P1 = new Menu("Стартовать викторину по теме", new GameUser());


            Menu menuP1_2Lev1P2 = new Menu("Стартовать смешанную викторину", new GameMixedQuiz());


            Menu menuP1_2Lev1P3 = new Menu("Посмотреть результаты своих прошлых викторин", context -> {
                String fileName3 = "UserQuizResult.dat";
                File file3 = new File(fileName3);
                if (file3.exists()) {
                    userQuizResult = readObjectUserQuizResult(fileName3);
                }

                for (Map.Entry<UserQuiz, List<ResultUserQuiz>> rr : userQuizResult.entrySet()) {
                    if (rr.getKey().getLogin().equals(staticLoginUser)) {
                        List<ResultUserQuiz> lr = rr.getValue();
                        for (int i = 0; i < lr.size(); i++) {
                            System.out.println("Викторина : " + lr.get(i).getQuizName());
                            System.out.println("Количество правильных ответов : " + lr.get(i).getRightAnswer());
                        }
                    }
                }
            });

            Menu menuP1_2Lev1P4 = new Menu("Посмотреть Топ - 20 по викторинам", new ViewTop());

            Menu menuP1_2Lev1P5 = new Menu("Изменить настройки", context -> {
                tapMenuP1_2Lev1P5 = true;
            });

            if (tapMenuP1_2Lev1P5) {

                Menu menuP1_2Lev1P5_L1P1 = new Menu("Изменить пароль", context -> {
                    System.out.println("Введите новый пароль");
                    password = scanner.nextLine();
                    for (int i = 0; i < listUserQuiz.size(); i++) {
                        if (listUserQuiz.get(i).getLogin().equals(staticLoginUser)) {
                            listUserQuiz.get(i).setPassword(password);
                            System.out.println("Пароль успешно изменен !");
                        }
                    }
                });

                Menu menuP1_2Lev1P5_L1P2 = new Menu("Изменить дату рождения", context -> {
                    System.out.println("Введите новую дату рождения");
                    dateOfBirth = scanner.nextLine();
                    for (int i = 0; i < listUserQuiz.size(); i++) {
                        if (listUserQuiz.get(i).getLogin().equals(staticLoginUser)) {
                            listUserQuiz.get(i).setDateOfBirth(dateOfBirth);
                            System.out.println("Дата рождения успешно изменена !");
                        }
                    }
                });

                menuP1_2Lev1P5.addSubMenu(menuP1_2Lev1P5_L1P1);
                menuP1_2Lev1P5.addSubMenu(menuP1_2Lev1P5_L1P2);
                do {
                    menuP1_2Lev1P5.print();
                    menuP1_2Lev1P5.action(action());
                } while (!exitTrue);

            }

            menuP_2.addSubMenu(menuP1_2Lev1P1);
            String fileName1 = "Quiz.dat";
            File file1 = new File(fileName1);
            if (file1.exists()) {
                quiz = QuizMapAdd.readObjectQuiz(fileName1);
            }

            int count = 0;
            for (Map.Entry<Quiz, List<Question>> rr: quiz.entrySet()){
                    List<Question> ql = rr.getValue();
                    for (int i = 0; i < ql.size(); i++) {
                        count++;
                }
            }
            if (count > 20) {
                menuP_2.addSubMenu(menuP1_2Lev1P2);
            }
            menuP_2.addSubMenu(menuP1_2Lev1P3);
            menuP_2.addSubMenu(menuP1_2Lev1P4);
            do {
                menuP_2.print();
                int action = action();
                if (exitTrue) {
                    break;
                }
                menuP_2.action(action);
            } while (!exitTrue);
        }


    }


    public boolean isPresent(UserQuiz userQuiz) throws FileNotFoundException {
        String fileName = "UserQuiz";
        File file = new File(fileName);
        if (file.exists()) {
            listUserQuiz = readObjectUserQuiz(fileName);
        }
        for (int i = 0; i < listUserQuiz.size(); i++) {
            if (listUserQuiz.get(i).getLogin().equals(userQuiz.getLogin())) {
                System.out.println("Такой логин уже существует ");
                return false;
            }
        }
        return true;
    }


    private static List<UserQuiz> readObjectUserQuiz(String fileName) throws FileNotFoundException {
        List<UserQuiz> list = null;
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

    public int action() {
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

    public void loginUser() throws FileNotFoundException {

        String fileName = "UserQuiz";
        File file = new File(fileName);
        if (file.exists()) {
            listUserQuiz = readObjectUserQuiz(fileName);
        }

        System.out.println("Введите login");
        login = scanner.nextLine();
        System.out.println("Введите password");
        password = scanner.nextLine();
        for (int i = 0; i < listUserQuiz.size(); i++) {
            if (listUserQuiz.get(i).getLogin().equals(login)) {
                if (listUserQuiz.get(i).getPassword().equals(password)) {
                    loginUser = true;
                    staticLoginUser = login;
                    break;
                }
            }
        }
        if (!loginUser) {
            System.out.println("Вы ввели не верный login или password");
        }
    }

    private static Map<UserQuiz, List<ResultUserQuiz>> readObjectUserQuizResult(String fileName) throws FileNotFoundException {
        Map<UserQuiz, List<ResultUserQuiz>> userQuizResult = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            userQuizResult = (Map<UserQuiz, List<ResultUserQuiz>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userQuizResult;
    }

    private static void saveObjectUserQuizResult(Map<UserQuiz, List<ResultUserQuiz>> userQuizResult, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userQuizResult);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
