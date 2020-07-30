package org.example;

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
    public void doIt(Context context) {
        int count = 0;
        for (Map.Entry<Quiz, List<Question>> p : AddQuiz.quiz.entrySet()) {
            System.out.println(count + " - " + p.getKey().getName());
        }
    }
}

class AddQuizQuestion implements Action<Context> {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswerString;
    private int correctAnswer;
    private String status;
    private int count;

    @Override
    public void doIt(Context context) {
        System.out.println("Введите название викторины");
        name = scanner.nextLine();
        AddQuiz.quiz.put(new Quiz(name), new ArrayList<>());
        for (Map.Entry<Quiz, List<Question>> r : AddQuiz.quiz.entrySet()) {
            if(r.getKey().getName().equals(name)){
               count =  (r.getValue().size()) + 1 ;
            }
        }
        do {
            System.out.println("Введите вопрос" + " " + count);
            question = scanner.nextLine();
            System.out.println("Введите 1-й вариант ответа");
            answer1 = scanner.nextLine();
            System.out.println("Введите 2-й вариант ответа");
            answer2 = scanner.nextLine();
            System.out.println("Введите 3-й вариант ответа");
            answer3 = scanner.nextLine();
            System.out.println("Введите 4-й вариант ответа");
            answer4 = scanner.nextLine();
            do {
                System.out.println("Введите номер правильного ответа");
                correctAnswerString = scanner.nextLine();
                try {
                    correctAnswer = Integer.parseInt(correctAnswerString);
                    if (correctAnswer >= 1 && correctAnswer <= 4) {
                        break;
                    } else {
                        System.out.println("Номер ответа должен быть от 1 до 4");
                    }

                } catch (Exception e) {
                    System.out.println("Введите правильно номер");
                }
            } while (true);
            System.out.println("exit - выход");
            status = scanner.nextLine();

            for (Map.Entry<Quiz, List<Question>> r : AddQuiz.quiz.entrySet()) {
                if (r.getKey().getName().equals(name)) {
                    if (r.getValue().size() <= 20) {
                        r.getValue().add(new Question(question, answer1, answer2, answer3, answer4, correctAnswer));
                    } else {
                        System.out.println(" В викторине может быть не более 20 вопросов");
                    }
                }
            }
        } while (!"exit".equals(status));
    }

}
