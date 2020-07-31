package org.example;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.*;

public class QuizMapAdd {
    private String name;
    private static QuizMapAdd instance;
    Map<Quiz, List<Question>> quiz =  new TreeMap<>();
    Scanner scanner = new Scanner(System.in);
    private int count;
    private String question;
    private String status;
    private String answer;
    private String correctAnswer;

    private QuizMapAdd() {

    }
    public static QuizMapAdd getInstance() {
        Scanner scanner = new Scanner(System.in);
        if (instance == null) {
            instance = new QuizMapAdd();
        }
        return instance;
    }

    public void addQuiz() throws FileNotFoundException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }
        Question question;
        System.out.println("Введите название викторины");
        name = scanner.nextLine();
        quiz.put(new Quiz(name), new ArrayList<>());

        do {
            for(Map.Entry<Quiz, List<Question>> r : quiz.entrySet()){
                if(r.getKey().getName().equals(name)){
                   count = r.getValue().size();
                }
            }
            Answer answer = null;
            System.out.println("Введите вопрос" + " " + (count + 1));
            System.out.println("exit - выход");
            this.question = scanner.nextLine();
            if("exit".equals(this.question)){
                break;
            }
            question =  new Question(name,this.question);
            do {
                System.out.println("Введите вариант ответа");
                System.out.println("exit -  выход");
                this.answer = scanner.nextLine();
                if("exit".equals(this.answer)){
                    break;
                }
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
                question.addQuestion(answer);
            } while (true);
            for(Map.Entry<Quiz, List<Question>> r : quiz.entrySet()){
                if(r.getValue().size() <= 20){
                    r.getValue().add(question);
                } else {
                    System.out.println("В викторине должно быть не более 20 вопросов");
                }
            }
        } while (true);
        saveObjectQuiz(quiz, "Quiz.dat");
    }

    public void printListQuiz() throws FileNotFoundException {
        String fileName1 = "Quiz.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            quiz = readObjectQuiz(fileName1);
        }
        int count = 1;
        for(Map.Entry<Quiz, List<Question>> r : quiz.entrySet()){
            System.out.println(count + " - " + r.getKey().getName());
            count++;
        }
    }

    private static void saveObjectQuiz(Map<Quiz, List<Question>> question, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(question);
        } catch (IOException e) {
            e.printStackTrace();
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

}

