package ru.com.avs.util;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class SimpleThreadTest {

    @Test
    public void run() throws InterruptedException, IOException {
        Thread.sleep(1000);
         FileOutputStream fos = new FileOutputStream("DB.json");
         fos.write("".getBytes());
         fos.close();
        Thread.sleep(5000);
        new File("DB.json").delete();
    }
}