package ru.com.avs.util;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class SimpleThreadTest {
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public String protocol = "jdbc:derby:";


    @Test
    public void run() throws InterruptedException, IOException {
        Thread.sleep(1000);
         FileOutputStream fos = new FileOutputStream("DB.json");
         fos.write("".getBytes());
         fos.close();
        Thread.sleep(5000);
        new File("DB.json").delete();
    }

    @Test
    public void connectToderby() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(driver).newInstance();


        Connection conn = DriverManager.getConnection(protocol + "derbyDB;create=true", props);
    };
}