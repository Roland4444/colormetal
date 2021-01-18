package ru.com.avs.util.abstractions;


import ru.com.avs.util.JSONizer;
import ru.com.avs.util.WayBillUtil;

import java.io.IOException;

import static org.junit.Assert.*;

public class JSONizerTest {
    JSONizer json = new JSONizer();

    public JSONizerTest() throws IOException {
    }




    public void returnField() {
        String etalon = "\"name\":\"roman\",";
        String etalonlast = "\"name\":\"roman\"";
        System.out.println(etalon);
        System.out.println(etalonlast);

        assertEquals(etalon, json.returnField("name", "roman"));
        assertEquals(etalonlast, json.returnField("name", "roman", true));
    }
}