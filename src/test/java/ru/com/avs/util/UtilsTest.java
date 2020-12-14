package ru.com.avs.util;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class UtilsTest {

    public UtilsTest() throws FileNotFoundException {
    }

    @Test
    public void testsafeDelete() throws IOException {
        String test = "test";
        FileOutputStream fos = new FileOutputStream(test);
        fos.write("test".getBytes());
        fos.close();
        assertEquals(true, new File(test).exists());
        Utils.safeDelete(test);
        assertEquals(false, new File(test).exists());
        Utils.safeDelete("nonexisted");
    };


}