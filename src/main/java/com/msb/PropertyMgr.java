package com.msb;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther： WangLei
 * @Date： 2021/7/21-07-21-22:45
 * @Description: com.msb
 * @version: 1.0
 */
public class PropertyMgr {
    private static Properties props = new Properties();

    static {

        try {
            props.load(Properties.class.getClassLoader().getResourceAsStream(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String  k){
        return (String)props.get(k).toString();
    }

    public static int getInt(String k){
        return (Integer)props.get(k);
    }

}
