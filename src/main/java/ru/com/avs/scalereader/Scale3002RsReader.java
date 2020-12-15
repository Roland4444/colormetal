package ru.com.avs.scalereader;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import ru.com.avs.model.Scale;
import ru.com.avs.util.Mock;

public class Scale3002RsReader extends RsReader {

    public Scale3002RsReader(Scale scale) {
        super(scale);
    }

    protected String parseWeight(String weight) throws IOException {
        if (weight != null) {
            String hex = weight;
            Pattern pattern = Pattern.compile("3D([0-9 ]{21})");
            Matcher matcher = pattern.matcher(hex);
            String result = "";
            if (matcher.find()) {
                String[] tmp = matcher.group(1).split(" ");
                for (String s : tmp) {
                    if (!s.isEmpty()) {
                        byte[] bytes = DatatypeConverter.parseHexBinary(s);
                        result += new BigDecimal(new String(bytes, StandardCharsets.UTF_8)).toString();
                    }
                }
            }
            weight = new BigDecimal(new StringBuffer(result).reverse().toString()).toString();
        }
     //   return weight;
        return new Mock().mockWeigth;

    }
}
