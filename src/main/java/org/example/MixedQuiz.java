package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MixedQuiz {
    private static MixedQuiz instance;
    List<Question> mixedList = new ArrayList<>();

    private MixedQuiz() {

    }

    public static MixedQuiz getInstance(){
        if(instance == null){
            instance = new MixedQuiz();
        }
        return instance;
    }

    public void addMixedQuestion(Question question) throws IOException {
        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if(file.exists()){
            mixedList = readObjectMixedQuestion(fileName);
        }
        mixedList.add(question);

        saveObjectMixedQuestion(mixedList,"MixedQuestion.dat");
    }

    public void deleteMixedQuestion(Question question){
        mixedList.remove(question);
    }

    public void editMixedQuestion(){

    }

    public void printMixedQuestion() throws IOException {
        String fileName = "MixedQuestion.dat";
        File file = new File(fileName);
        if(file.exists()){
            mixedList = readObjectMixedQuestion(fileName);
        }

        for (int i = 0; i < mixedList.size() ; i++) {
            System.out.println(mixedList.get(i).getTheme());
            System.out.println(mixedList.get(i).getQuestion());
            mixedList.get(i).printAnswer();
        }
    }

    private static void saveObjectMixedQuestion(List<Question> mixedQuestion, String fileName) throws FileNotFoundException {
        try(ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))){
            output.writeObject(mixedQuestion);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private static List<Question> readObjectMixedQuestion(String fileName) throws IOException {
        List<Question> mixedQuestion = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))){
            mixedQuestion = (List<Question>)input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mixedQuestion;
    }
}
