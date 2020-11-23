package ru.com.avs.util.abstractions;

import org.junit.Test;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;
import ru.com.avs.util.JSONizer;
import ru.com.avs.util.WayBillUtil;

import java.io.IOException;

import static org.junit.Assert.*;

public class JSONizerTest {
    WeighingView restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);;
    JSONizer json = new JSONizer();

    public JSONizerTest() throws IOException {
    }

    @Test
    public void JSONedRestored() {
        assertNotEquals(null, json.JSONedRestored(restored));
        System.out.println(json.JSONedRestored(restored));
    }

    @Test
    public void returnField() {
        String etalon = "\"name\":\"roman\",";
        String etalonlast = "\"name\":\"roman\"";
        System.out.println(etalon);
        System.out.println(etalonlast);

        assertEquals(etalon, json.returnField("name", "roman"));
        assertEquals(etalonlast, json.returnField("name", "roman", true));
    }
}