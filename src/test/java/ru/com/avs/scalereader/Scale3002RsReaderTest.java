package ru.com.avs.scalereader;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class Scale3002RsReaderTest {

    @Test
    public void parseWeight() throws IOException {
        String hex =new String( Files.readAllBytes(Paths.get("2_5246866811982449245.htm")));
        assertEquals(Scale3002RsReader.parseWeight3002(hex), Scale3000RsReader.parseWeight3000(hex));
    }
}