package ru.com.avs.scalereader;

import ru.com.avs.model.Scale;


import java.io.IOException;

public class Scale600RsReader extends RsReader {

    public Scale600RsReader(Scale scale) {
        super(scale);
    }

    protected String parseWeight(String weight) throws IOException {
        if (weight != null) {
            String[] results = weight.split(" ");
            if (results.length >= 4) {
                String hx = results[3] + results[2];
                weight = String.valueOf((Integer.parseInt(hx, 16)) * 0.1);
            }
        }
        return weight;
    ///    return new Mock().mockWeigth;
    }
}
