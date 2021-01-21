package ru.com.avs.scalereader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ru.com.avs.model.Scale;
import ru.com.avs.util.Mock;

public class Scale600EthReader extends EthReader {

    public Scale600EthReader(Scale scale) {
        super(scale);
    }
    public Scale600EthReader() {
     }

    @Override
    public String readWeight() throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write(this.scale.getEthCmdBytes());
        byte[] data = new byte[20];
        int decimal6 = (int) data[6] & 0xff;
        int decimal7 = (int) data[7] & 0xff;

        String hex6 = Integer.toHexString(decimal6);
        String hex7 = Integer.toHexString(decimal7);
        String weightHex = hex7 + hex6;
        float weight = (float) Integer.parseInt(weightHex, 16) / 10;
       // return String.valueOf(weight);
        return String.valueOf(weight);
       // return Mock.mockWeigth;
      /////  return new Mock().mockWeigth;

    }

    public String readWeight(int a, int b) throws IOException {
        String hex6 = Integer.toHexString(a);
        String hex7 = Integer.toHexString(b);
        String weightHex = hex7 + hex6;
        float weight = (float) Integer.parseInt(weightHex, 16) / 10;
        return String.valueOf(weight);
        //    return String.valueOf(weight);
        // return Mock.mockWeigth;

    }


}
