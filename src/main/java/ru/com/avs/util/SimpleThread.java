package ru.com.avs.util;


import java.io.File;

public class SimpleThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15000);
                if (new File("DB.json").exists()) {
                    UpdateDBHelper.runDatabaseUpdate();
                    new File("DB.json").delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
