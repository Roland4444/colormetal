package ru.com.avs.controller;

import org.flywaydb.core.Flyway;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.com.avs.WindowedApplication;
import ru.com.avs.model.Weighing;
import ru.com.avs.service.*;
import ru.com.avs.util.SpringLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@Component("UpdateDBController")
public class UpdateDBController {

    @Autowired
    private static WeighingService weighingService;
    @Autowired
    private static WaybillService waybillService;


    public static void initSQL(String sqlQuery) {
        if (sqlQuery.equals("break"))
            return;
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
                .initSql(sqlQuery)
                .load()
                .migrate();
    }

    public static void runDatabaseUpdate() throws IOException, ParseException {
        initSQL(createSQLFile("DB.json"));
    }

    public static String createSQLFile(String filenamejson) throws IOException, ParseException {
        byte[] data = Files.readAllBytes(Paths.get(filenamejson));
        String json = new String(data);
        JSONObject jo = (JSONObject) new JSONParser().parse(json);

        if (!String.valueOf(jo.get("Netto")).equals("0.00"))
            return new String(("UPDATE waybills SET COMMENT = '" + (String) jo.get("Comment") + "' WHERE DATE_CREATE = '" + jo.get("Date") + "' AND TIME_CREATE = '" +
                    jo.get("Time") + "' AND WAYBILL = " + jo.get("Waybill_number") + " ;\n" + "UPDATE weighings SET BRUTTO = " + jo.get("Brutto") +
                    ", NETTO = " + jo.get("Netto") + ", CLOGGING = " + jo.get("Clogging") + ", TRASH = " + jo.get("Trash") +
                    ", METAL_ID = (select id from METALS where NAME = '" + jo.get("Metall") + "'), TARE = " + jo.get("Tara") + " WHERE ID = " +
                    jo.get("Weighing_id") + ";").getBytes("Windows-1251"), "UTF-8");
        else deleteWeightings(jo);
        return "break";
    }

    public static void deleteWeightings(JSONObject jo) {
        weighingService = (WeighingService) SpringLoader.getBean("WeighingService");
        waybillService = (WaybillService) SpringLoader.getBean("WaybillService");
        /*Scanner fileInfo = new Scanner(new File("info"));
        if (fileInfo.next().equals("1")) {
            waybillService.delete(weighingService.getById(Integer.parseInt(jo.get("Weighing_id").toString())).getWaybill());
            System.out.println(1);}
        else {weighingService.delete(weighingService.getById(Integer.parseInt(jo.get("Weighing_id").toString())));
            System.out.println(2);}*/
        List<Weighing> weighingList = weighingService.getList();
        int waybill_id = weighingService.getById(Integer.parseInt(jo.get("Weighing_id").toString())).getWaybill().getId();
        int count = 0;
        for (Weighing weighing : weighingList)
            if (weighing.getWaybill().getId() == waybill_id)
                count++;
        if (count == 1)
            waybillService.delete(weighingService.getById(Integer.parseInt(jo.get("Weighing_id").toString())).getWaybill());
        else
            weighingService.delete(weighingService.getById(Integer.parseInt(jo.get("Weighing_id").toString())));
    }

}
