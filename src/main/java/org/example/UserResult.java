package org.example;

import java.io.Serializable;

public class UserResult implements Serializable,Comparable<UserResult> {

    private String UserLogin;
    private int countCorrectAnswer;

    public UserResult(String userLogin, int countCorrectAnswer) {
        UserLogin = userLogin;
        this.countCorrectAnswer = countCorrectAnswer;
    }

    public String getUserLogin() {
        return UserLogin;
    }

    public void setUserLogin(String userLogin) {
        UserLogin = userLogin;
    }

    public int getCountCorrectAnswer() {
        return countCorrectAnswer;
    }

    public void setCountCorrectAnswer(int countCorrectAnswer) {
        this.countCorrectAnswer = countCorrectAnswer;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "UserLogin='" + UserLogin + '\'' +
                ", countCorrectAnswer=" + countCorrectAnswer +
                '}';
    }

    @Override
    public int compareTo(UserResult o) {
        return countCorrectAnswer - o.countCorrectAnswer;
    }
}
