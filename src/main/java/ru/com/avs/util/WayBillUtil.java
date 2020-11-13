package ru.com.avs.util;

import ru.com.avs.model.WayBillView;

import java.io.*;

public class WayBillUtil {
    public static byte[] saveWayBillToBytes(WayBillView input) {
        byte[] Result=null ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(input);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {

            }
        }
        return Result;
    }

    public static WayBillView restoreBytesToWayBill(byte[] input){
        Object o=null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return (WayBillView) o;
    }
}
