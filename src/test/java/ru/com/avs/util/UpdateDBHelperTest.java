package ru.com.avs.util;

import org.flywaydb.core.Flyway;
import org.junit.Test;
import ru.com.avs.WindowedApplication;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class UpdateDBHelperTest {

    @Test
    public void createSQLfile() {
        String host = null;

        try (InputStream input =
                     WindowedApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties property = new Properties();
            property.load(input);
            host = property.getProperty("jdbc.url") + ";create=true";
        } catch (Exception e){
            e.printStackTrace();
        }

        Flyway.configure()
                .dataSource(host, null, null)
                .validateOnMigrate(false)
                .initSql("")
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }
}