package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddQuiz {
    public boolean stepMenu1_2;
    public static boolean exitTrue;
    private String login;
    private String password;
    private String status;
    private int statusInt;


    private List<UserUtil> userUtils = new ArrayList<>();
    Menu menuLevel1_2;
    Scanner scanner = new Scanner(System.in);


    public void addQuiz() throws IOException {
        String fileName1 = "UserUtil.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            userUtils = readObjectUserUtil(fileName1);
        }

        do {

            Menu menu = new Menu("Главное меню");

            Menu menuLevel1_1;
            if (userUtils.size() == 0) {
                menuLevel1_1 = new Menu("Зарегистрировать пользователя", context -> {
                    UserUtil userUtil = UserUtil.getInstance();
                    System.out.println("Введите login");
                    login = scanner.nextLine();
                    System.out.println("Введите password");
                    password = scanner.nextLine();
                    userUtil.setLogin(login);
                    userUtil.setPassword(password);
                    userUtils.add(userUtil);
                });
                menu.addSubMenu(menuLevel1_1);
            }

            if (userUtils.size() == 1) {
                menuLevel1_2 = new Menu("Вход пользователя", context -> {
                    if (!AddQuiz.exitTrue) {
                        System.out.println("Введите login");
                        login = scanner.nextLine();
                        System.out.println("Введите password");
                        password = scanner.nextLine();
                        if (userUtils.get(0).getLogin().equals(login) && userUtils.get(0).getPassword().equals(password)) {
                            stepMenu1_2 = true;
                        } else {
                            System.out.println("Введите правильно login или password");
                        }
                    }
                });
                menu.addSubMenu(menuLevel1_2);
            }

            if (userUtils.size() > 0) {
                if (userUtils.get(0).getLogin().equals("Admin") && userUtils.get(0).getPassword().equals("Admin")) {
                    Menu menuLevel1_3 = new Menu("Изменить login и password", context -> {
                        System.out.println("Введите новый login");
                        login = scanner.nextLine();
                        System.out.println("Введите новый password");
                        password = scanner.nextLine();
                        userUtils.get(0).setLogin(login);
                        userUtils.get(0).setPassword(password);
                        System.out.println("Login и password успешно изменены");
                    });
                    menu.addSubMenu(menuLevel1_3);
                }
            }

            menu.print();

            menu.action(quizAction());
            if (exitTrue) {
                break;
            }

            if (stepMenu1_2) {

                Menu menu1_2Level2_1 = new Menu("Посмотреть список всех викторин", context -> {
                    QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
                    quizMapAdd.printListQuiz();
                });
                menuLevel1_2.addSubMenu(menu1_2Level2_1);


                Menu menu1_2Level2_2 = new Menu("Создать викторину", context -> {
                    QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
                    quizMapAdd.addQuiz();
                });


                Menu menu1_2Level2_3 = new Menu("Редактировать викторину", context -> {
                    QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
                    quizMapAdd.editQuiz();
                });

                Menu menu1_2Level2_4 = new Menu("Удалить викторину", context -> {
                    QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
                    quizMapAdd.deleteQuiz();
                });

                menuLevel1_2.addSubMenu(menu1_2Level2_2);
                menuLevel1_2.addSubMenu(menu1_2Level2_3);
                menuLevel1_2.addSubMenu(menu1_2Level2_4);
                do {
                    menuLevel1_2.print();
                    menuLevel1_2.action(quizAction());
                } while (!"exit".equals(status));
            }


        } while (true);


        saveObjectUserUtil(userUtils, "UserUtil.dat");
    }


    private static void saveObjectUserUtil(List<UserUtil> userUtils, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<UserUtil> readObjectUserUtil(String fileName) throws FileNotFoundException {
        List<UserUtil> userUtils = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            userUtils = (List<UserUtil>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userUtils;
    }


    public int quizAction() {
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
