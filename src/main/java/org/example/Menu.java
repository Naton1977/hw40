package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements MenuInterface {
    private String name;
    private List<Menu> menuList = new ArrayList<>();
    private Action<Context> action;

    public Menu(String name) {
        this.name = name;
        action = null;
    }

    public Menu(String name, Action<Context> action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public void make(Context context) throws IOException {
        action.doIt(context);
    }

    @Override
    public void remove(Menu menu) {

    }

    @Override
    public void addSubMenu(Menu sabMenu) {
        menuList.add(sabMenu);
    }

    public void print() {
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println((i + 1) + " - " + menuList.get(i).name);
        }
    }

    public void action(int num) throws IOException {
        if (!AddQuiz.exitTrue) {
            for (int i = 0; i < menuList.size(); i++) {
                if (i == (num - 1)) {
                    menuList.get(i).make(null);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                '}';
    }
}
