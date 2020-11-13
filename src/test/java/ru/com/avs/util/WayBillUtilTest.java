package ru.com.avs.util;

import org.junit.Test;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WayBillView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class WayBillUtilTest {

    @Test
    public void saveWayBillToBytes() {
    }

    @Test
    public void restoreBytesToWayBill() throws IOException {
        WayBillView restored = WayBillUtil.restoreBytesToWayBill(Files.readAllBytes(Paths.get(new File(WaybillJournalController.FileNameDump).getPath())));
        assertNotEquals(null, restored);
        assertEquals("5А", restored.getMetal().getName());
        assertEquals(2, restored.getWaybill()); //waybill № накладной
        assertEquals("хусаинов", restored.getComment());//комментарий
        assertEquals(new BigDecimal(35.6).floatValue(), restored.getBrutto().floatValue(), 0.2);
    }
}