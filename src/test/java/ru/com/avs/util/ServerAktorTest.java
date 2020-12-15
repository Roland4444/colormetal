package ru.com.avs.util;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.Assert.*;
public class ServerAktorTest {


    @Test
    public void URLJaktor() throws UnknownHostException {
       ServerAktor akt = new ServerAktor();
       akt.editButton = null;
       akt.setAddress("http://127.0.0.1:12215/");
       assertNotEquals(null, akt.getURL_thisAktor());
       System.out.println(akt.getURL_thisAktor());
    };

    @Test
    public void addr() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String addr = inetAddress.toString();
        System.out.println(addr);
    }
}