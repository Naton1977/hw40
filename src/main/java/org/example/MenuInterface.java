package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MenuInterface {
    void make (Context context) throws IOException;
    void remove (Menu menu);
    void addSubMenu(Menu menu);
}
