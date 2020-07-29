package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AddQuiz {
    List<UserUtil> userUtils = new ArrayList<>();
    Map<Quiz, List <Question>> quiz = new TreeMap<>();




    private static void saveObjectUserUtil(List<UserUtil> userUtils, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<UserUtil> readObjectUserUtil(String fileName) throws FileNotFoundException {
        List <UserUtil> userUtils= null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            userUtils = (List<UserUtil>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userUtils;
    }


    private static void saveObjectUserQuiz(Map<Quiz,List <Question>> question, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(question);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map <Quiz, List<Question>> readObjectQuiz(String fileName) throws FileNotFoundException {
        Map <Quiz, List <Question>> quiz = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            quiz = (Map<Quiz, List<Question>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quiz;
    }

}
