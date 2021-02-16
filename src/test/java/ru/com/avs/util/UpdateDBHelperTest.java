package ru.com.avs.util;

import org.flywaydb.core.Flyway;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import ru.com.avs.WindowedApplication;
import ru.com.avs.controller.UpdateDBController;

import java.io.IOException;
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

   /* @Test
    public void testCreateSQLfile() throws IOException, ParseException {
    String json = "DB test.json";
    assertEquals(null, UpdateDBController.createSQLfile(json));
    }

    @Test
    public void initSQL() throws IOException, ParseException {
       String sqlQuery= "SELECT COUNT(*)FROM \"APP\".\"WEIGHINGS\" WHERE WAYBILL_ID = 11";
       assertEquals(null, UpdateDBController.initSQL(sqlQuery));
    }*/

  /*  @Test
    public void deleteWeightings() throws IOException, ParseException {
        byte[] data = Files.readAllBytes(Paths.get("DB test.json"));
        String json = new String(data);
        JSONObject jo = (JSONObject) new JSONParser().parse(json);
        assertEquals("11",UpdateDBHelper.deleteWeightings(jo));
    }*/
}