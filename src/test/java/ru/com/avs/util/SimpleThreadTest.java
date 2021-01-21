package ru.com.avs.util;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class SimpleThreadTest {

    public String protocol = "jdbc:derby:scales-rest-service-db;";


    @Test
    public void run() throws InterruptedException, IOException {
        Thread.sleep(1000);
         FileOutputStream fos = new FileOutputStream("DB.json");
         fos.write("".getBytes());
         fos.close();
        Thread.sleep(20000);
        new File("DB.json").delete();
    }

    @Test
    public void connectToderby() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        Properties props = new Properties();
        Connection conn = DriverManager.getConnection(protocol, props); //+ "derbyDB;create=true",  props);
        assertNotEquals(null, conn);

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM METALS where name=?");
        pst.setString(1, "Нержавейка");
        ResultSet metals = pst.executeQuery();
        if  (metals.next())
            System.out.println(metals.getObject("id"));


    };
}