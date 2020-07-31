package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Action <T>{
    void doIt(T context) throws IOException;
}
