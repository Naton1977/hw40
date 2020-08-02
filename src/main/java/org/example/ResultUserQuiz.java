package org.example;

import java.io.Serializable;

public class ResultUserQuiz  implements Serializable {
    private String quizName;
    private String rightAnswer;

    public ResultUserQuiz(String quizName, String rightAnswer) {
        this.quizName = quizName;
        this.rightAnswer = rightAnswer;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "ResultUserQuiz{" +
                "quizName='" + quizName + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }
}
