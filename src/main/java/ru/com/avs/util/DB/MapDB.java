package ru.com.avs.util.DB;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MapDB implements Serializable {

    public Map<String, Integer> requests = new HashMap<>();
    static MapDB restored(byte[] input) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException var15) {
            var15.printStackTrace();
        } catch (ClassNotFoundException var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException var14) {
            }

        }

        return (MapDB)o;
    }

    static byte[] savedToBLOB(MapDB input) {
        byte[] Result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(input);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException var12) {
            }

        }

        return Result;
    }
}
