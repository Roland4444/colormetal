package ru.com.avs.util;

import ru.com.avs.model.WeighingView;

public class JSONizer {
    public String JSONedRestored(WeighingView input){
        StringBuilder buf = new StringBuilder();
      //  buf.append(input.)
        buf.append("{");
        buf.append(returnField("Date", input.getDateCreate().toString())+'\n');
        buf.append(returnField("Time", input.getTimeCreate().toString())+'\n');
        buf.append(returnField("Waybill_number", String.valueOf(input.getWaybill()))+'\n');
        buf.append(returnField("Comment", input.getComment())+'\n');
        buf.append(returnField("Metall", input.getMetal().toString())+'\n');
        buf.append(returnField("Brutto", input.getBrutto().toString())+'\n');
        buf.append(returnField("Tara", input.getTare().toString())+'\n');
        buf.append(returnField("Clogging", input.getClogging().toString())+'\n');
        buf.append(returnField("Trash", input.getTrash().toString())+'\n');
        buf.append(returnField("Netto", input.getNetto().toString())+'\n');
        buf.append(returnField("Mode", input.getMode())+'\n');
        buf.append(returnField("Complete", input.getComplete(), true)+'\n');
        buf.append("}");


        return buf.toString();
    }

    public String returnField(String nameField, String Value){
        StringBuilder sb = new StringBuilder();
        sb.append("\""+nameField+"\":");
        sb.append("\""+Value+"\",");
        return sb.toString();
    }
    public String returnField(String nameField, String Value, boolean last){
        StringBuilder sb = new StringBuilder();
        sb.append("\""+nameField+"\":");
        sb.append("\""+Value+"\"");
        return sb.toString();
    }
}
