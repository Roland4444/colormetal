package ru.com.avs.util;

import ru.com.avs.scalereader.Scale600EthReader;
import ru.com.avs.scalereader.Scale600RsReader;

import java.io.IOException;

public class Mock {
    public  String mockWeigth ;
    public Mock() throws IOException {
        mockWeigth = new Scale600EthReader().readWeight(18,7);
    }
}
