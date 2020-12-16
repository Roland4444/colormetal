package ru.com.avs;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class Example2Test {

  //  @Test
    public void checkInitialRequest() throws IOException, InterruptedException {
        Example2 e2 = new Example2();
        FileOutputStream fos = new FileOutputStream(e2.approve_lock);
        fos.write('a');
        fos.close();

        FileOutputStream fos2 = new FileOutputStream(e2.decline_lock);
        fos2.write('a');
        fos2.close();

        assertEquals(true, e2.checkHaveResponce());

    }
}