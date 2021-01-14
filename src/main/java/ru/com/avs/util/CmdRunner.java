package ru.com.avs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdRunner {
    public static void run() throws IOException {
        String[] cmd = { "sh", "run.sh"};
        Runtime.getRuntime().exec(cmd);

    }
    public static void main(String[] args) throws IOException {
       CmdRunner.run();
    }
}
