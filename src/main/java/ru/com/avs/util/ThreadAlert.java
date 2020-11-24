package ru.com.avs.util;

import static javax.swing.JOptionPane.showMessageDialog;

public class ThreadAlert extends Thread{
    public void run(){
        showMessageDialog(null, "редактирование разрешено");
    }
}
