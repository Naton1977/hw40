package org.example;

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

    public Menu (String name, Action<Context> action){
        this.name = name;
        this.action = action;
    }

    @Override
    public void make(Context context) {
        action.doIt(context);
    }

    @Override
    public void remove(Menu menu) {

    }

    @Override
    public void addSubMenu(Menu sabMenu) {
        menuList.add(sabMenu);
    }

    public void print (){
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println((i +1) + menuList.get(i).name);
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                '}';
    }
}
