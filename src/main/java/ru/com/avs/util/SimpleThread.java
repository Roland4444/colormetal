package ru.com.avs.util;

import ru.com.avs.controller.WaybillEditController;

import java.io.File;
import java.io.IOException;

public class SimpleThread extends  Thread{
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (new File("DB.json").exists()) {
                try {
                    WaybillEditController.saveJSON("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
