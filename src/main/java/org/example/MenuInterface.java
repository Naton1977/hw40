package org.example;

import java.io.FileNotFoundException;

public interface MenuInterface {
    void make (Context context) throws FileNotFoundException;
    void remove (Menu menu);
    void addSubMenu(Menu menu);
}
