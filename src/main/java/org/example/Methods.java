package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Methods {


}

class ChangeLoginPassword implements Action<Context> {
    Scanner scanner = new Scanner(System.in);
    private String login;
    private String password;

    @Override
    public void doIt(Context context) {
        System.out.println("Введите новый login");
        login = scanner.nextLine();
        System.out.println("Введите новый password");
        password = scanner.nextLine();
        AddQuiz.userUtils.get(0).setLogin(login);
        AddQuiz.userUtils.get(0).setPassword(password);
        System.out.println("Login и password успешно изменены");
    }
}

class CreateUser implements Action<Context> {
    Scanner scanner = new Scanner(System.in);
    private String login;
    private String password;

    @Override
    public void doIt(Context context) {
        UserUtil userUtil = UserUtil.getInstance();
        System.out.println("Введите login");
        login = scanner.nextLine();
        System.out.println("Введите password");
        password = scanner.nextLine();
        userUtil.setLogin(login);
        userUtil.setPassword(password);
        AddQuiz.userUtils.add(userUtil);
    }
}

class UserAuthorization implements Action<Context> {
    Scanner scanner = new Scanner(System.in);
    private String login;
    private String password;

    @Override
    public void doIt(Context context) {
        if (!AddQuiz.exitTrue) {
            System.out.println("Введите login");
            login = scanner.nextLine();
            System.out.println("Введите password");
            password = scanner.nextLine();
            if (AddQuiz.userUtils.get(0).getLogin().equals(login) && AddQuiz.userUtils.get(0).getPassword().equals(password)) {
                AddQuiz.stepMenu1_2 = true;
            } else {
                System.out.println("Введите правильно login или password");
            }
        }
    }
}

class PrintListOfQuizzes implements Action<Context> {

    @Override
    public void doIt(Context context) throws FileNotFoundException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.printListQuiz();
    }
}

class CreateQuiz implements Action<Context>{

    @Override
    public void doIt(Context context) throws FileNotFoundException {
        QuizMapAdd quizMapAdd =  QuizMapAdd.getInstance();
        quizMapAdd.addQuiz();
    }
}

class EditQuiz implements Action<Context>{

    @Override
    public void doIt(Context context) throws FileNotFoundException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.editQuiz();

    }
}

class DeleteQuiz implements Action<Context>{


    @Override
    public void doIt(Context context) throws FileNotFoundException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.deleteQuiz();
    }
}