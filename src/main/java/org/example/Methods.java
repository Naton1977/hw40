package org.example;

import java.io.*;
import java.util.*;

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

class CreateQuiz implements Action<Context> {

    @Override
    public void doIt(Context context) throws IOException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.addQuiz();
    }
}

class EditQuiz implements Action<Context> {

    @Override
    public void doIt(Context context) throws IOException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.editQuiz();

    }
}

class DeleteQuiz implements Action<Context> {


    @Override
    public void doIt(Context context) throws IOException {
        QuizMapAdd quizMapAdd = QuizMapAdd.getInstance();
        quizMapAdd.deleteQuiz();
    }
}

class GameUser implements Action<Context> {
    Scanner scanner = new Scanner(System.in);
    private int count = 1;
    private String status;
    private int quizNumber;
    private boolean exit;
    private String corrAnsw;
    private int corrAnswInt;
    private boolean corrAnswBool;
    private int countCorrectAnswer;
    private int countWrongAnswer;
    private String endStr;
    private int resultAnswer;
    private int quizCorrectAnswer;
    private String quizName;


    Map<Quiz, List<Question>> quiz = new TreeMap<>();
    Map<Quiz, List<UserResult>> resultQuizUser = new TreeMap<>();

    GameUser() {
    }

    @Override
    public void doIt(Context context) throws IOException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }

        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = readObjectResultUserQuiz(fileName2);
        }
        if (!"exit".equals(status)) {
            for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
                System.out.println(count + " - " + r.getKey().getName());
                count++;
            }
            count = 1;
            System.out.println("Выберите викторину из списка");
            System.out.println("exit -  выход");
            do {
                status = scanner.nextLine();
                if ("exit".equals(status)) {
                    exit = true;
                    break;
                }
                try {
                    quizNumber = Integer.parseInt(status);
                    break;
                } catch (Exception e) {
                    System.out.println("Введите правильно число");
                }
            } while (true);
        }


        if (!exit) {
            for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
                if (quizNumber == count) {
                    List<Question> qw = r.getValue();
                    int countLok = 1;
                    System.out.println("Викторина : " + r.getKey().getName());
                    System.out.println("Правильных ответов может быть несколько ...");
                    for (int i = 0; i < qw.size(); i++) {
                        System.out.println("exit - выход");
                        System.out.println("Вопрос " + "№ " + countLok + " " + qw.get(i).getQuestion());
                        qw.get(i).printAnswer();
                        do {
                            do {
                                System.out.println("Введите номер правильного ответа");
                                System.out.println("Введите - end для перехода к следующему вопросу");
                                corrAnsw = scanner.nextLine();
                                if ("exit".equals(corrAnsw)) {
                                    exit = true;
                                    break;
                                }
                                if ("end".equals(corrAnsw)) {
                                    break;
                                }
                                try {
                                    corrAnswInt = Integer.parseInt(corrAnsw);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Введите правильно число");
                                }
                            } while (true);
                            if ("end".equals(corrAnsw)) {
                                resultAnswer = qw.get(i).countCorrAnswer();
                                if (resultAnswer == countCorrectAnswer) {
                                    System.out.println("Ответ правильный");
                                    quizCorrectAnswer++;
                                    countCorrectAnswer = 0;
                                    countWrongAnswer = 0;
                                }
                                break;
                            }
                            if (!exit) {
                                corrAnswBool = qw.get(i).corrAnswer(corrAnswInt);
                                if (corrAnswBool) {
                                    countCorrectAnswer++;
                                } else {
                                    countWrongAnswer++;
                                }

                            }
                            if ("exit".equals(corrAnsw)) {
                                break;
                            }
                        } while (true);
                        if ("exit".equals(corrAnsw)) {
                            break;
                        }

                        countLok++;
                    }
                    System.out.println("Число правильных ответов" + " - " + quizCorrectAnswer);
                    for (Map.Entry<Quiz, List<UserResult>> resUs : resultQuizUser.entrySet()) {
                        if (resUs.getKey().getName().equals(r.getKey().getName())) {
                            List<UserResult> set1 = resUs.getValue();
                            for (int i = 0; i < set1.size(); i++) {
                                if(set1.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)){
                                    set1.remove(i);
                                }
                            }
                            resUs.getValue().add(new UserResult(GameQuiz.staticLoginUser, quizCorrectAnswer));
                            System.out.println("Викторина : " + resUs.getKey().getName());
                            List<UserResult> set = resUs.getValue();
                            Collections.sort(set);
                            int count = 1;
                            for (int i = 0; i < set.size(); i++) {
                                if (set.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)) {
                                    System.out.println("Ваше место : " + (i + 1));
                                }
                            }
                        }
                    }


                }
                count++;
            }
        }

    }

    private static Map<Quiz, List<Question>> readObjectQuiz(String fileName) throws FileNotFoundException {
        Map<Quiz, List<Question>> quiz = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            quiz = (Map<Quiz, List<Question>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    private static Map<Quiz, List<UserResult>> readObjectResultUserQuiz(String fileName) throws FileNotFoundException {
        Map<Quiz, List<UserResult>> resultQuizUser = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            resultQuizUser = (Map<Quiz, List<UserResult>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultQuizUser;
    }

    private static void saveObjectResultUserQuiz(Map<Quiz, TreeSet<UserResult>> resultQuizUser, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(resultQuizUser);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}