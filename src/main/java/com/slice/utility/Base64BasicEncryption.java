package com.slice.utility;

import java.util.Base64;

public class Base64BasicEncryption {
    public static String decode(String element) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String deCodeString = new String(decoder.decode(element));
        return deCodeString;
    }
}