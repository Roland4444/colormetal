package ru.com.avs.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserUtilsTest {
    String dumbString = "123 562";

    public String c1 = "10";
    public String c2 = "4A";
    public String c3 = "00";
    public String c4 = "00";

    @Test
    public void toByte() {
        assertEquals(new String(dumbString.getBytes()), new String(UserUtils.toByte(dumbString)));
    }

    @Test
    public void toByte2() {
        assertEquals(c1, new String(UserUtils.toByte(c1)));

    }

    @Test
    public void toByte3() {

        assertEquals(c2, new String(UserUtils.toByte(c2)));

    }

    @Test
    public void toByte5() {

        assertEquals(c3, new String(UserUtils.toByte(c3)));

    }

    @Test
    public void toByte4() {

        assertEquals(c4, new String(UserUtils.toByte(c4)));
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    @Test
    public void testToByteStack() {
        String hex = "00A0BF";
        byte[] result =  {0x00, (byte) 0xA0, (byte) 0xBf};
        byte[] f3000 = {0b0010000};
        byte[] f600 = {0b01001010};
        byte[] f60 =  {0b00000000};
        String s3000 = "10";
        String s600 = "4A";
        String s60 = "00";
        assertEquals(new String(result), new String(UserUtils.toByte(hex)));
        assertEquals(new String(result), new String(hexStringToByteArray(hex)));
        assertEquals(new String(f3000), new String(UserUtils.toByte("10")));

        assertEquals(new String(f3000), new String(UserUtils.toByte(s3000)));
        assertEquals(new String(f600), new String(UserUtils.toByte(s600)));
        assertEquals(new String(f60), new String(UserUtils.toByte(s60)));


    }



}