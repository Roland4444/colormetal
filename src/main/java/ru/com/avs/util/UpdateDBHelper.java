package ru.com.avs.util;

import org.flywaydb.core.Flyway;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.com.avs.WindowedApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class UpdateDBHelper {

    public static void runDatabaseUpdate() throws IOException, ParseException {
        String host = null;

        try (InputStream input =
                     WindowedApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties property = new Properties();
            property.load(input);
            host = property.getProperty("jdbc.url") + ";create=true";
        } catch (Exception e) {
            e.printStackTrace();
        }

        Flyway.configure()
                .dataSource(host, null, null)
                .validateOnMigrate(false)
                .initSql(createSQLfile("DB.json"))
                .load()
                .migrate();
    }

    public static String createSQLfile(String filenamejson) throws IOException, ParseException {


        byte[] data = Files.readAllBytes(Paths.get(filenamejson));
        String json = new String(data);
        Scanner weighing_id = new Scanner(new File("temp"));
        JSONObject jo = (JSONObject) new JSONParser().parse(json);

        String string = "UPDATE waybills SET COMMENT = '" + (String) jo.get("Comment") + "' WHERE DATE_CREATE = '" + jo.get("Date") + "' AND TIME_CREATE = '" +
                jo.get("Time") + "' AND WAYBILL = " + jo.get("Waybill_number") + " ;\n" + "UPDATE weighings SET BRUTTO = " + jo.get("Brutto") +
                ", NETTO = " + jo.get("Netto") + ", CLOGGING = " + jo.get("Clogging") + ", TRASH = " + jo.get("Trash") +
                ", METAL_ID = (select id from METALS where NAME = '" + jo.get("Metall") + "'), TARE = " + jo.get("Tara") + " WHERE ID = " +
                weighing_id.nextLine() + ";";
        String out = new String(string.getBytes("Windows-1251"), "UTF-8");
        System.out.println(out);
        return out;
    }
}
