package org.example;


import java.io.*;
import java.util.*;

public class QuizMapAdd {
    private String name;
    private static QuizMapAdd instance;
    Map<Quiz, List<Question>> quiz = new TreeMap<>();
    Map<Quiz, List<UserResult>> resultQuizUser = new TreeMap<>();
    List<Question> mixedList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private int count;
    private String question;
    private String status;
    private String answer;
    private String correctAnswer;
    private String deleteNumber;
    private int deleteNumberInt;
    private boolean deleteTrue;
    private String newName;
    private int statusInt;
    private boolean tapMenu1_2;
    private String editQuestion;
    private int editQuestionInt;
    private String newQuestion;
    private boolean presentQuiz;
    private String deleteName;

    private QuizMapAdd() {

    }

    public static QuizMapAdd getInstance() {
        if (instance == null) {
            instance = new QuizMapAdd();
        }
        return instance;
    }

    public void addQuiz() throws IOException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }

        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if (file.exists()) {
            mixedList = readObjectMixedQuestion(fileName);
        }
        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = readObjectResultUserQuiz(fileName2);
        }

        for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
            if (r.getKey().getName().equals("Викторина смешанных вопросов")) {
                presentQuiz = true;
                break;
            }
        }
        if (!presentQuiz) {
            quiz.put(new Quiz("Викторина смешанных вопросов"), new ArrayList<>());
        }

        Question question;
        System.out.println("Введите название викторины");
        System.out.println("exit - выход");
        name = scanner.nextLine();
        if (!"exit".equals(name)) {
            quiz.put(new Quiz(name), new ArrayList<>());
            resultQuizUser.put(new Quiz(name), new ArrayList<>());
            do {
                for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
                    if (r.getKey().getName().equals(name)) {
                        count = r.getValue().size();
                    }
                }
                Answer answer = null;
                System.out.println("Введите вопрос" + " " + (count + 1));
                System.out.println("exit - выход");
                this.question = scanner.nextLine();
                if ("exit".equals(this.question)) {
                    break;
                }
                question = new Question(name, this.question);
                do {
                    System.out.println("Введите вариант ответа");
                    System.out.println("exit -  выход");
                    this.answer = scanner.nextLine();
                    if ("exit".equals(this.answer)) {
                        break;
                    }
                    do {
                        System.out.println("Введите это правильный ответ?");
                        System.out.println("1 - правильный ответ");
                        System.out.println("2 - не правильный ответ");
                        correctAnswer = scanner.nextLine();
                        if ("1".equals(correctAnswer)) {
                            answer = new Answer(name, this.answer, CorrectAnswer.YES);
                        }
                        if ("2".equals(correctAnswer)) {
                            answer = new Answer(name, this.answer, CorrectAnswer.NO);
                        }
                    } while (!"1".equals(correctAnswer) && !"2".equals(correctAnswer));
                    question.addQuestion(answer);
                } while (true);
                mixedList.add(question);

                for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
                    if (r.getKey().getName().equals(name)) {
                        if (r.getValue().size() <= 20) {
                            r.getValue().add(question);
                        } else {
                            System.out.println("В викторине должно быть не более 20 вопросов");
                        }
                    }
                }
            } while (true);
            saveObjectQuiz(quiz, "Quiz.dat");
            saveObjectMixedQuestion(mixedList, "MixedQuestion.dat");
            saveObjectResultUserQuiz(resultQuizUser, "ResultQuizUser.dat");
        }
    }

    public void printListQuiz() throws FileNotFoundException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }
        int count = 1;
        for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
            if (!r.getKey().getName().equals("Викторина смешанных вопросов")) {
                System.out.println(count + " - " + r.getKey().getName());
                count++;
            }
        }
    }

    public void editQuiz() throws IOException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }
        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if (file.exists()) {
            mixedList = readObjectMixedQuestion(fileName);
        }

        printListQuiz();
        do {
            System.out.println("Ведите номер той викторины которую нужно редактировать");
            deleteNumber = scanner.nextLine();
            try {
                deleteNumberInt = Integer.parseInt(deleteNumber);
                break;
            } catch (Exception e) {
                System.out.println("Введите правильно номер");
            }
        } while (true);

        int count = 1;
        for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
            if (!r.getKey().getName().equals("Викторина смешанных вопросов")) {
                if (deleteNumberInt == count) {
                    name = r.getKey().getName();
                    List<Question> questionList = r.getValue();

                    Menu menu = new Menu("Редактор викторин");

                    Menu menu1 = new Menu("Редактировать название викторины", context -> {
                        System.out.println("Введите новое название викторины");
                        newName = scanner.nextLine();
                        r.getKey().setName(newName);


                        for(Map.Entry<Quiz,List<UserResult>> rq : resultQuizUser.entrySet()){
                            if(rq.getKey().getName().equals(name)){
                                rq.getKey().setName(newName);
                            }
                        }


                        for (int i = 0; i < questionList.size(); i++) {
                            questionList.get(i).setTheme(newName);
                        }
                        for (int i = 0; i < mixedList.size() ; i++) {
                            if(mixedList.get(i).getTheme().equals(name)){
                                mixedList.get(i).setTheme(newName);
                            }
                        }
                        System.out.println("Название викторины изменено");
                    });
                    Menu menu2 = new Menu("Редактировать вопросы викторины", context -> {
                        tapMenu1_2 = true;
                    });

                    menu.addSubMenu(menu1);
                    menu.addSubMenu(menu2);
                    menu.print();
                    menu.action(quizAction());

                    if (tapMenu1_2) {
                        Menu menuP2Level1_1 = new Menu("Показать список вопросов", context -> {
                            if (!"exit".equals(status)) {
                                for (int i = 0; i < questionList.size(); i++) {
                                    System.out.println((i + 1) + " - " + questionList.get(i).getQuestion());
                                }
                            }
                        });
                        Menu menuP2Level1_2 = new Menu("Удалить вопрос", context -> {
                            if (!"exit".equals(status)) {
                                System.out.println("Введите номер вопроса который нужно удалить");
                                deleteNumber = scanner.nextLine();
                                try {
                                    deleteNumberInt = Integer.parseInt(deleteNumber);
                                } catch (Exception e) {
                                    System.out.println("Введите правильно номер вопроса");
                                }
                                for (int i = 0; i < questionList.size(); i++) {
                                    if (i == (deleteNumberInt - 1)) {
                                        Question qw = questionList.remove(i);
                                        for (int m = 0; m < mixedList.size(); m++) {
                                            if (mixedList.get(m).getQuestion().equals(qw.getQuestion())) {
                                                mixedList.remove(m);
                                            }
                                        }

                                        System.out.println("Вопрос удален");
                                    }
                                }
                            }

                        });


                        Menu menuP2Level1_3 = new Menu("Редактировать вопрос", context -> {
                            if (!"exit".equals(status)) {
                                Answer answer1 = null;
                                System.out.println("Введите номер вопроса который нужно редактировать");
                                editQuestion = scanner.nextLine();
                                try {
                                    editQuestionInt = Integer.parseInt(editQuestion);
                                } catch (Exception e) {
                                    System.out.println("Введите правильно номер вопроса");
                                }
                                for (int i = 0; i < questionList.size(); i++) {
                                    if (i == (editQuestionInt - 1)) {
                                        String question = questionList.get(i).getQuestion();
                                        for (int j = 0; j < mixedList.size(); j++) {
                                            if(mixedList.get(j).getQuestion().equals(question)){
                                                mixedList.remove(j);
                                            }
                                        }
                                        System.out.println("Введите новый вопрос");
                                        newQuestion = scanner.nextLine();
                                        questionList.get(i).setQuestion(newQuestion);
                                        questionList.get(i).deleteAnswers();
                                        do {
                                            System.out.println("Введите вариант ответа");
                                            System.out.println("exit - выход");
                                            answer = scanner.nextLine();
                                            if ("exit".equals(answer)) {
                                                break;
                                            }
                                            System.out.println("1 - это правильный ответ");
                                            System.out.println("2 - это не правильный ответ");
                                            correctAnswer = scanner.nextLine();
                                            if ("1".equals(correctAnswer)) {
                                                answer1 = new Answer(name, answer, CorrectAnswer.YES);
                                            }
                                            if ("2".equals(correctAnswer)) {
                                                answer1 = new Answer(name, answer, CorrectAnswer.NO);
                                            }
                                            questionList.get(i).addQuestion(answer1);
                                            System.out.println("Ответ добавлен");
                                        } while (true);
                                        mixedList.add(questionList.get(i));
                                    }
                                }
                            }
                        });

                        Menu menuP2Level1_4 = new Menu("Создать вопрос", context -> {
                            if (!"exit".equals(status)) {
                                Answer answer1 = null;
                                if (questionList.size() < 20) {
                                    System.out.println("Введите новый вопрос" + " № " + (questionList.size() + 1));
                                    newQuestion = scanner.nextLine();
                                    questionList.add(new Question(name, newQuestion));
                                    for (int i = 0; i < questionList.size(); i++) {
                                        if (questionList.get(i).getQuestion().equals(newQuestion)) {
                                            do {
                                                System.out.println("Введите вариант ответа");
                                                System.out.println("exit - выход");
                                                answer = scanner.nextLine();
                                                if ("exit".equals(answer)) {
                                                    break;
                                                }
                                                System.out.println("1 - это правильный ответ");
                                                System.out.println("2 - это не правильный ответ");
                                                correctAnswer = scanner.nextLine();
                                                if ("1".equals(correctAnswer)) {
                                                    answer1 = new Answer(name, answer, CorrectAnswer.YES);
                                                }
                                                if ("2".equals(correctAnswer)) {
                                                    answer1 = new Answer(name, answer, CorrectAnswer.NO);
                                                }
                                                questionList.get(i).addQuestion(answer1);
                                                System.out.println("Ответ добавлен");
                                            } while (true);
                                            mixedList.add(questionList.get(i));
                                        }
                                    }
                                } else {
                                    System.out.println("В викторине может быть не более 20 вопросов");
                                }
                            }
                        });


                        menu2.addSubMenu(menuP2Level1_1);
                        menu2.addSubMenu(menuP2Level1_2);
                        menu2.addSubMenu(menuP2Level1_3);
                        menu2.addSubMenu(menuP2Level1_4);

                        do {
                            menu2.print();
                            menu2.action(quizAction());
                        } while (!"exit".equals(status));
                    }
                }
                count++;
            }
        }

        saveObjectQuiz(quiz, "Quiz.dat");
        saveObjectMixedQuestion(mixedList, "MixedQuestion.dat");

    }


    public void deleteQuiz() throws IOException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }
        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if (file.exists()) {
            mixedList = readObjectMixedQuestion(fileName);
        }
        String fileName2 = "ResultQuizUser.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            resultQuizUser = readObjectResultUserQuiz(fileName2);
        }


        Quiz quiz1 = null;
        printListQuiz();
        do {
            System.out.println("Ведите номер той викторины которую нужно удалить");
            deleteNumber = scanner.nextLine();
            try {
                deleteNumberInt = Integer.parseInt(deleteNumber);
                break;
            } catch (Exception e) {
                System.out.println("Введите правильно номер");
            }
        } while (true);

        int count = 1;
        for (Map.Entry<Quiz, List<Question>> r : quiz.entrySet()) {
            if (!r.getKey().getName().equals("Викторина смешанных вопросов")) {
                if (deleteNumberInt == count) {
                    deleteName = r.getKey().getName();
                    quiz1 = r.getKey();
                    for (int i = 0; i < mixedList.size(); i++) {
                        if (mixedList.get(i).getTheme().equals(deleteName)) {
                            mixedList.remove(i);
                        }
                    }
                    deleteTrue = true;
                }
                count++;
            }
        }
        if (deleteTrue) {
            quiz.remove(quiz1);
            resultQuizUser.remove(quiz1);

            System.out.println("Викторина успешно удаленна");
        } else {
            System.out.println("Викторины под таким номером нет");
        }
        saveObjectQuiz(quiz, "Quiz.dat");
        saveObjectMixedQuestion(mixedList, "MixedQuestion.dat");
        saveObjectResultUserQuiz(resultQuizUser, "ResultQuizUser.dat");

    }

    public void printMixedList() throws IOException {
        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if (file.exists()) {
            mixedList = readObjectMixedQuestion(fileName);
        }

        for (int i = 0; i < mixedList.size(); i++) {
            System.out.println(mixedList.get(i).getTheme());
            System.out.println(mixedList.get(i).getQuestion());
            mixedList.get(i).printAnswer();

        }
    }

     static void saveObjectQuiz(Map<Quiz, List<Question>> question, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(question);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     static Map<Quiz, List<Question>> readObjectQuiz(String fileName) throws FileNotFoundException {
        Map<Quiz, List<Question>> quiz = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            quiz = (Map<Quiz, List<Question>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public int quizAction() {
        do {
            System.out.println("exit -  выход");
            status = scanner.nextLine();
            if ("exit".equals(status)) {
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

    static void saveObjectMixedQuestion(List<Question> mixedQuestion, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(mixedQuestion);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static List<Question> readObjectMixedQuestion(String fileName) throws IOException {
        List<Question> mixedQuestion = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            mixedQuestion = (List<Question>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mixedQuestion;
    }

    static Map<Quiz, List<UserResult>> readObjectResultUserQuiz(String fileName) throws FileNotFoundException {
        Map<Quiz, List<UserResult>> resultQuizUser = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            resultQuizUser = (Map<Quiz, List<UserResult>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultQuizUser;
    }

    static void saveObjectResultUserQuiz(Map<Quiz, List<UserResult>> resultQuizUser, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(resultQuizUser);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}

