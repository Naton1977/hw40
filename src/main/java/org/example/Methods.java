package org.example;
import java.util.Scanner;

public class Methods {



}

class CreateUser implements Action <CreateUser>{
Scanner scanner = new Scanner(System.in);
    @Override
    public void doIt(CreateUser context) {
        UserUtil userUtil = UserUtil.getInstance();
        AddQuiz.userUtils.add(userUtil);
    }
}