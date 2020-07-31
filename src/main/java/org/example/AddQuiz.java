package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

public class AddQuiz {
    public static boolean stepMenu1_2;
    public static boolean exitTrue;
    private String status;
    private int statusInt;
    public static List<UserUtil> userUtils = new ArrayList<>();
    public static Map<Quiz, List<Question>> quiz = new TreeMap<>();
    Menu menuLevel1_2;
    Scanner scanner = new Scanner(System.in);


    public void addQuiz() throws FileNotFoundException {
        String fileName1 = "UserUtil.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            userUtils = readObjectUserUtil(fileName1);
        }

        do {

            Menu menu = new Menu("Главное меню");

            Menu menuLevel1_1;
            if (userUtils.size() == 0) {
                CreateUser createUser = new CreateUser();
                menuLevel1_1 = new Menu("Зарегистрировать пользователя", createUser);
                menu.addSubMenu(menuLevel1_1);
            }

            if (userUtils.size() == 1) {
                UserAuthorization userAuthorization = new UserAuthorization();
                menuLevel1_2 = new Menu("Вход пользователя",userAuthorization);
                menu.addSubMenu(menuLevel1_2);
            }

            if (userUtils.size() > 0) {
                if (userUtils.get(0).getLogin().equals("Admin") && userUtils.get(0).getPassword().equals("Admin")) {
                    ChangeLoginPassword changeLoginPassword = new ChangeLoginPassword();
                    Menu menuLevel1_3 = new Menu("Изменить login и password", changeLoginPassword);
                    menu.addSubMenu(menuLevel1_3);
                }
            }

            menu.print();

            menu.action(quizAction());
            if(exitTrue){
                break;
            }

            if (stepMenu1_2){
                PrintListOfQuizzes printListOfQuizzes = new PrintListOfQuizzes();
                Menu menu1_2Level2_1 = new Menu("Посмотреть список всех викторин", printListOfQuizzes);
                menuLevel1_2.addSubMenu(menu1_2Level2_1);


                CreateQuiz createQuiz = new CreateQuiz();
                Menu menu1_2Level2_2 = new Menu("Создать викторину",createQuiz);

                Menu menu1_2Level2_3 = new Menu("Редактировать викторину");
                Menu menu1_2Level2_4 = new Menu("Удалить викторину");

                menuLevel1_2.addSubMenu(menu1_2Level2_2);
                menuLevel1_2.addSubMenu(menu1_2Level2_3);
                menuLevel1_2.addSubMenu(menu1_2Level2_4);
                menuLevel1_2.print();
                menuLevel1_2.action(quizAction());
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




    public int quizAction(){
        do {
            System.out.println("exit -  выход");
            status = scanner.nextLine();
            if("exit".equals(status)){
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
