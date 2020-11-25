package ru.com.avs.util;

import ru.com.avs.Example;

import javax.swing.*;

public class ExampleThread extends Thread {


    @Override
    public void run() {
        try {
            new Example().preperaGUI();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
