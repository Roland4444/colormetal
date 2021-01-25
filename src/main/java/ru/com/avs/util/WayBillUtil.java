package ru.com.avs.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import ru.com.avs.model.WeighingView;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class WayBillUtil {
    public static Object[][] dataFromObject(JSONObject input) throws JSONException {
        Object[][] result = new Object[1][13];
        result[0][0] = input.get("Date");
        result[0][1] =  input.get("Time");
        result[0][2] =  input.get("Waybill_number");
        result[0][3] = input.get("Comment");
        result[0][4] = input.get("Metall");
        result[0][5] = input.get("Brutto");
        result[0][6] = input.get("Tara");
        result[0][7] =input.get("Clogging");///засор
        result[0][8] = input.get("Trash");;        //примеси
        result[0][9] = input.get("Netto");;
        result[0][10] ="";;
        result[0][11] = input.get("Complete");;
        result[0][12] = "";
        return result;
    }

    public static String trimApply(String input){
        BigDecimal bd = new BigDecimal(input);
        BigDecimal result  =  bd.setScale(2, RoundingMode.HALF_UP);
        return String.valueOf(result);
    };

    public static void saveWayBilltoJSON(String FileName, WeighingView input) throws JSONException, IOException {
        JSONObject rowJSON = new JSONObject();
        rowJSON.put("Date", input.getDateCreate());
        rowJSON.put("Time", input.getTimeCreate());
        rowJSON.put("Waybill_number", input.getWaybill());
        rowJSON.put("Comment" , input.getComment());
        rowJSON.put("Metall", String.valueOf(input.getMetal().toString()));
        rowJSON.put("Brutto", trimApply(String.valueOf(input.getBrutto())));
        rowJSON.put("Tara", trimApply(String.valueOf(input.getTare())));
        rowJSON.put("Clogging" , trimApply(String.valueOf(input.getClogging())));
        rowJSON.put("Trash", trimApply(String.valueOf(input.getTrash())));
        rowJSON.put("Netto", trimApply(String.valueOf(input.getNetto())));
        rowJSON.put("Complete" , input.getComplete());
        rowJSON.put("Condition" , "");
        FileOutputStream fos = new FileOutputStream(FileName);
        FileOutputStream fos1 = new FileOutputStream("temp");
        fos1.write(String.valueOf(input.getWeighingId()).getBytes());
        fos1.close();
        /*if (System.getProperty("os.name").equals("Linux"))*/
            fos.write(rowJSON.toString().getBytes("UTF-8"));
     /*   else
            fos.write(rowJSON.toString().getBytes("windows-1251"));*/
        fos.close();
    };

    public static WeighingView restoreBytesToWayBill(byte[] input){
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
        return (WeighingView) o;
    }


}
