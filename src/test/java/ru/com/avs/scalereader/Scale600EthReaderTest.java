package ru.com.avs.scalereader;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Scale600EthReaderTest {
    Scale600EthReader reader = new Scale600EthReader();


    @Test
    public void testReadWeight() throws IOException {
        String res = reader.readWeight(1,2);
        assertNotEquals(null, res);
        System.out.println("\n\n\n"+res);
    }
}