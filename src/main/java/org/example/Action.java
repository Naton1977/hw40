package org.example;

import java.io.FileNotFoundException;

public interface Action <T>{
    void doIt(T context) throws FileNotFoundException;
}
