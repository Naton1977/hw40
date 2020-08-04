package org.example;

import java.io.Serializable;

public class ResultUserQuiz  implements Serializable,Comparable<ResultUserQuiz> {
    private String quizName;
    private int rightAnswer;

    public ResultUserQuiz(String quizName, int rightAnswer) {
        this.quizName = quizName;
        this.rightAnswer = rightAnswer;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "ResultUserQuiz{" +
                "quizName='" + quizName + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }

    @Override
    public int compareTo(ResultUserQuiz o) {
        return rightAnswer - o.rightAnswer;
    }
}
