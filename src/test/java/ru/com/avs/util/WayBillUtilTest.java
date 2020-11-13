package ru.com.avs.util;

import org.junit.Test;
import ru.com.avs.model.WeighingView;

import java.io.File;
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
        WeighingView restored = WayBillUtil.restoreBytesToWayBill(Files.readAllBytes(Paths.get(new File("waybill_xus.bin").getPath())));
        assertNotEquals(null, restored);
        assertEquals("5А", restored.getMetal().getName());
        assertEquals(2, restored.getWaybill()); //waybill № накладной
        assertEquals("хусаинов", restored.getComment());//комментарий
        assertEquals(new BigDecimal(35.6).floatValue(), restored.getBrutto().floatValue(), 0.2);
    }

    @Test
    public void restoreBytesToWayBill2() throws IOException {
        WeighingView restored = WayBillUtil.restoreBytesToWayBill(Files.readAllBytes(Paths.get(new File("waybill_vit.bin").getPath())));
        assertNotEquals(null, restored);
        assertEquals("Алюминий хлам", restored.getMetal().getName());
        assertEquals(15, restored.getWaybill()); //waybill № накладной
        assertEquals("витек", restored.getComment());//комментарий
        assertEquals(new BigDecimal(294.5).floatValue(), restored.getBrutto().floatValue(), 0.2);
    }
}