package ru.com.avs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdRunner {
    public void run(){
        Process p;
        try {
            String[] cmd = { "sh", "startup.sh"};
            p = Runtime.getRuntime().exec(cmd);
            p.exitValue();
            BufferedReader reader=new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Process p;
        try {
            String[] cmd = { "sh", "startup.sh"};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader=new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
