package org.example;

public class Quiz implements Comparable<Quiz> {
    private String name;

    public Quiz(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Quiz o) {
        return name.compareTo(o.name);
    }
}
