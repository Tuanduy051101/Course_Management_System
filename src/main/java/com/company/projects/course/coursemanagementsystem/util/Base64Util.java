package com.company.projects.course.coursemanagementsystem.util;

import java.util.Base64;

public class Base64Util {
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String stringEncoded) {
        return Base64.getDecoder().decode(stringEncoded);
    }
}
