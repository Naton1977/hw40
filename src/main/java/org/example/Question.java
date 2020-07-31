package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private String theme;
    private String question;
    List<Answer> answers = new ArrayList<>();

    public Question(String theme, String question) {
        this.theme = theme;
        this.question = question;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addQuestion(Answer answer){
        answers.add(answer);
    }
    public void printAnswer(){
        for (int i = 0; i < answers.size() ; i++) {
            System.out.println( (i + 1) + " - " +answers.get(i).getAnswer());
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "theme='" + theme + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
