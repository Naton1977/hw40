package org.example;

import java.io.*;
import java.util.*;
import java.util.Random;

public class Methods {


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
    private int resultAnswer;
    private int quizCorrectAnswer;
    private String quizName;


    Map<Quiz, List<Question>> quiz = new TreeMap<>();
    Map<Quiz, List<UserResult>> resultQuizUser = new TreeMap<>();
    Map<UserQuiz, List<ResultUserQuiz>> userQuizResult = new TreeMap<>();

    GameUser() {
    }

    @Override
    public void doIt(Context context) throws IOException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = QuizMapAdd.readObjectQuiz(fileName1);
        }

        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = QuizMapAdd.readObjectResultUserQuiz(fileName2);
        }

        String fileName3 = "UserQuizResult.dat";
        File file3 = new File(fileName3);
        if (file3.exists()) {
            userQuizResult = readObjectUserQuizResult(fileName3);
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
                        quizName = r.getKey().getName();
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
                                    if (resultAnswer == countCorrectAnswer && countWrongAnswer == 0) {
                                        System.out.println("Ответ правильный");
                                        quizCorrectAnswer++;
                                        countCorrectAnswer = 0;
                                        countWrongAnswer = 0;
                                        break;
                                    } else {
                                        countCorrectAnswer = 0;
                                        countWrongAnswer = 0;
                                        break;
                                    }

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
                                    if (set1.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)) {
                                        set1.remove(i);
                                    }
                                }
                                set1.add(new UserResult(GameQuiz.staticLoginUser, quizCorrectAnswer));
                                System.out.println("Викторина : " + resUs.getKey().getName());
                                Collections.sort(set1);
                                for (int i = 0; i < set1.size(); i++) {
                                    if (set1.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)) {
                                        System.out.println("Ваше место : " + (i + 1));
                                    }
                                }
                            }
                        }
                        for (Map.Entry<UserQuiz, List<ResultUserQuiz>> rr : userQuizResult.entrySet()) {
                            if (rr.getKey().getLogin().equals(GameQuiz.staticLoginUser)) {
                                List<ResultUserQuiz> lr = rr.getValue();
                                lr.add(new ResultUserQuiz(quizName, quizCorrectAnswer));
                            }
                        }
                    }
                    count++;

            }
            count = 1;
            quizCorrectAnswer = 0;
        }
        QuizMapAdd.saveObjectResultUserQuiz(resultQuizUser, "ResultQuizUser.dat");
        saveObjectUserQuizResult(userQuizResult, "UserQuizResult.dat");

    }


    static Map<UserQuiz, List<ResultUserQuiz>> readObjectUserQuizResult(String fileName) throws FileNotFoundException {
        Map<UserQuiz, List<ResultUserQuiz>> userQuizResult = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            userQuizResult = (Map<UserQuiz, List<ResultUserQuiz>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userQuizResult;
    }

    static void saveObjectUserQuizResult(Map<UserQuiz, List<ResultUserQuiz>> userQuizResult, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userQuizResult);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

class GameMixedQuiz implements Action<Context> {
    private int countQuizRandom;
    private String quizName = "Викторина смешанных вопросов";
    private String corrAnsw;
    private boolean exit;
    private int corrAnswInt;
    private int resultAnswer;
    private int countCorrectAnswer;
    private int quizCorrectAnswer;
    private int countWrongAnswer;
    private boolean corrAnswBool;
    private int count2 = 0;


    Map<Quiz, List<UserResult>> resultQuizUser = new TreeMap<>();
    Map<UserQuiz, List<ResultUserQuiz>> userQuizResult = new TreeMap<>();
    Map<Quiz, List<Question>> quiz = new TreeMap<>();


    Random random1 = new Random();
    Random random2 = new Random();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void doIt(Context context) throws IOException {

        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = QuizMapAdd.readObjectResultUserQuiz(fileName2);
        }

        String fileName3 = "UserQuizResult.dat";
        File file3 = new File(fileName3);
        if (file3.exists()) {
            userQuizResult = GameUser.readObjectUserQuizResult(fileName3);
        }


        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = QuizMapAdd.readObjectQuiz(fileName1);
        }


        System.out.println(quizName);
        int countQuiz = 0;
        System.out.println("Правильных ответов может быть несколько");
        for (Map.Entry<Quiz, List<Question>> ql : quiz.entrySet()) {
            countQuiz++;
        }

        do {
            countQuizRandom = random1.nextInt(countQuiz);
            int count1 = 0;
            for (Map.Entry<Quiz, List<Question>> ql : quiz.entrySet()) {
                if (count1 == countQuizRandom) {
                    List<Question> q = ql.getValue();
                    for (int j = 0; j < q.size(); ) {
                        int rnd = q.size();
                        int numQuestion = random2.nextInt(rnd);
                        System.out.println("exit - выход");
                        System.out.println("Вопрос " + "№ " + (count2 + 1) + " " + q.get(numQuestion).getQuestion());
                        count2++;
                        q.get(numQuestion).printAnswer();
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
                                resultAnswer = q.get(numQuestion).countCorrAnswer();
                                if (resultAnswer == countCorrectAnswer && countWrongAnswer == 0) {
                                    System.out.println("Ответ правильный");
                                    quizCorrectAnswer++;
                                    countCorrectAnswer = 0;
                                    countWrongAnswer = 0;
                                    q.remove(numQuestion);
                                    break;
                                } else {
                                    countCorrectAnswer = 0;
                                    countWrongAnswer = 0;
                                    q.remove(numQuestion);
                                    break;
                                }

                            }
                            if (!exit) {
                                corrAnswBool = q.get(numQuestion).corrAnswer(corrAnswInt);
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

                        if (count2 == 20) {
                            break;
                        }
                        if ("end".equals(corrAnsw)) {
                            break;
                        }

                    }

                    if (count2 == 20) {
                        break;
                    }

                    if ("exit".equals(corrAnsw)) {
                        break;
                    }
                }

                if (count2 == 20) {
                    break;
                }
                if ("end".equals(corrAnsw)) {
                    corrAnsw = null;
                    break;
                }
                count1++;
            }
            if (count2 == 20) {
                break;
            }
            if ("exit".equals(corrAnsw)) {
                break;
            }

        } while (true);


        System.out.println("Число правильных ответов" + " - " + quizCorrectAnswer);
        for (Map.Entry<Quiz, List<UserResult>> resUs : resultQuizUser.entrySet()) {
            if (resUs.getKey().getName().equals(quizName)) {
                List<UserResult> set1 = resUs.getValue();
                for (int i = 0; i < set1.size(); i++) {
                    if (set1.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)) {
                        set1.remove(i);
                    }
                }
                set1.add(new UserResult(GameQuiz.staticLoginUser, quizCorrectAnswer));
                System.out.println("Викторина : " + resUs.getKey().getName());
                List<UserResult> set = resUs.getValue();
                Collections.sort(set);
                for (int i = 0; i < set.size(); i++) {
                    if (set.get(i).getUserLogin().equals(GameQuiz.staticLoginUser)) {
                        System.out.println("Ваше место : " + (i + 1));
                    }
                }
            }
        }
        for (Map.Entry<UserQuiz, List<ResultUserQuiz>> rr : userQuizResult.entrySet()) {
            if (rr.getKey().getLogin().equals(GameQuiz.staticLoginUser)) {
                List<ResultUserQuiz> lr = rr.getValue();
                lr.add(new ResultUserQuiz(quizName, quizCorrectAnswer));
            }
        }

        quizCorrectAnswer = 0;
        GameUser.saveObjectUserQuizResult(userQuizResult, "UserQuizResult.dat");
        QuizMapAdd.saveObjectResultUserQuiz(resultQuizUser, "ResultQuizUser.dat");
    }

}

class ViewTop implements Action<Context> {
    Map<Quiz, List<UserResult>> resultQuizUser = new TreeMap<>();
    Scanner scanner = new Scanner(System.in);
    private String status;
    private boolean exit;
    private int quizNumber;


    @Override
    public void doIt(Context context) throws IOException {
        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = QuizMapAdd.readObjectResultUserQuiz(fileName2);
        }
        int count = 1;
        for (Map.Entry<Quiz, List<UserResult>> rr : resultQuizUser.entrySet()) {
            System.out.println("Викторина : " + " № " + count + " " + rr.getKey().getName());
            count++;
        }
        count = 1;
        System.out.println("Введите номер викторины для отображения");
        System.out.println("exit - выход");
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
        int count1 = 1;
        for (Map.Entry<Quiz, List<UserResult>> rr : resultQuizUser.entrySet()) {
            if (count1 == quizNumber) {
                System.out.println("Викторина : " + rr.getKey().getName());
                List<UserResult> ur = rr.getValue();
                Collections.sort(ur);
                for (int i = 0; i < ur.size(); i++) {
                    System.out.println("Пользователь : " + ur.get(i).getUserLogin());
                    System.out.println("Число правильных ответов : " + ur.get(i).getCountCorrectAnswer());
                    System.out.println("Место в таблице : " + (i + 1));
                    if (i == 19) {
                        break;
                    }
                }
            }
            count1++;
        }
    }
}