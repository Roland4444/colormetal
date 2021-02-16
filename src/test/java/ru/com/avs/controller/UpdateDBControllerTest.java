package ru.com.avs.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class UpdateDBControllerTest {

  /*  @Test
    public void deleteWeightings() throws IOException, ParseException {
        String json = "{\"Comment\":\"гитинова\",\"Netto\":\"0.00\",\"Weighing_id\":\"4\",\"Complete\":\"Да\",\"Mode\":\"\",\"Clogging\":\"0.00\",\"Time\":\"17:33:57\",\"Trash\":\"0.00\",\"Date\":\"2019-09-05\",\"Metall\":\"Латунь\",\"Brutto\":\"0.00\",\"Tara\":\"0.00\",\"Waybill_number\""+
                ":\"25\"}";
        JSONObject jo = (JSONObject) new JSONParser().parse(json);
        UpdateDBController updateDBController = new UpdateDBController();
        assertEquals("DELETE FROM WEIGHINGS WHERE WAYBILL_ID = 25;\n" +
                "DELETE FROM WAYBILLS WHERE ID = 25;",updateDBController.deleteWeightings(jo));
    }

    @Test
    public void deleteWeightings2() throws IOException, ParseException {
        String json = "{\"Comment\":\"гитинова\",\"Netto\":\"0.00\",\"Weighing_id\":\"4\",\"Complete\":\"Да\",\"Mode\":\"\",\"Clogging\":\"0.00\",\"Time\":\"17:33:57\",\"Trash\":\"0.00\",\"Date\":\"2019-09-05\",\"Metall\":\"Латунь\",\"Brutto\":\"0.00\",\"Tara\":\"0.00\",\"Waybill_number\""+
                ":\"18\"}";
        JSONObject jo = (JSONObject) new JSONParser().parse(json);
        UpdateDBController updateDBController = new UpdateDBController();
        assertEquals("DELETE FROM WEIGHINGS WHERE ID = 4;",updateDBController.deleteWeightings(jo));
    }*/
}