package ru.com.avs.util;


import ru.com.avs.controller.UpdateDBController;

import java.io.File;

public class SimpleThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                if (new File("DB.json").exists()) {
                    UpdateDBController updateDBController = new UpdateDBController();
                    updateDBController.runDatabaseUpdate();
                    new File("DB.json").delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
