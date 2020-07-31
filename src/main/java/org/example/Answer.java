package org.example;

import java.io.Serializable;

public class Answer implements Serializable {
    private String quizName;
    private String answer;
    CorrectAnswer correctAnswer;

    public Answer(String quizName, String answer, CorrectAnswer correctAnswer) {
        this.quizName = quizName;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public CorrectAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(CorrectAnswer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "quizName='" + quizName + '\'' +
                ", answer='" + answer + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
