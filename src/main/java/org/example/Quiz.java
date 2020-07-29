package org.example;

public class Quiz {
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
}
