package com.zf.utils.emailutil;

public class RandomUtil {

    public String randomCode() {
        String strRand = "";
        for (int i = 0; i < 6; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }

}
