package org.example;

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

    public void addMixedQuestion(Question question){
        mixedList.add(question);
    }

    public void deleteMixedQuestion(Question question){
        mixedList.remove(question);
    }

    public void editMixedQuestion(){

    }
}
