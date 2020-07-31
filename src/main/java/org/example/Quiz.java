package org.example;

import java.io.Serializable;

public class Quiz implements Comparable<Quiz>, Serializable {
    private String theme;

    public Quiz(String name) {
        this.theme = name;
    }

    public String getName() {
        return theme;
    }

    public void setName(String name) {
        this.theme = name;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "name='" + theme + '\'' +
                '}';
    }

    @Override
    public int compareTo(Quiz o) {
        return theme.compareTo(o.theme);
    }
}
