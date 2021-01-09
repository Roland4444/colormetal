package ru.com.avs.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckerTest {

    @Test
    public void isnumber() {
        String notnumber = "6765fghfgh";
        String isnumber = "1222.12";
        Checker check = new Checker();
        assertEquals(false, check.isnumber(notnumber));
        assertEquals(true, check.isnumber(isnumber));

    }

    @Test
    public void testIsnumber() {
    }
}