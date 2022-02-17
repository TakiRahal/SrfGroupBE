package com.takirahal.srfgroup.utils;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomUtil {

    public static String generateActivationKey() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        System.out.println(generatedString);
        return generatedString;
    }
}
