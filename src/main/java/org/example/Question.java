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

    public void addQuestion(Answer answer) {
        answers.add(answer);
    }

    public void deleteAnswers() {
        answers.clear();
    }


    public void printAnswer() {
        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + " - " + answers.get(i).getAnswer());
        }
    }

    public boolean corrAnswer(int numberQuestion){
      if(answers.get(numberQuestion -1).getCorrectAnswer().equals(CorrectAnswer.YES)){
          return true;
      }
      return false;
    }

    public int countCorrAnswer(){
        int count = 0;
        for (int i = 0; i < answers.size(); i++) {
            if(answers.get(i).getCorrectAnswer().equals(CorrectAnswer.YES)){
                count ++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Question{" +
                "theme='" + theme + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
