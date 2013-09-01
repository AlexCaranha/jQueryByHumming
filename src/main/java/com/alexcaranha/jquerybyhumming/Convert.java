package com.alexcaranha.jquerybyhumming;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *
 * @author alexcaranha
 */
public class Convert {

    public static boolean toBoolean(Object value) {
        return Boolean.parseBoolean(String.valueOf(value));
    }

    public static double toDouble(Object value) {
        return Double.parseDouble(String.valueOf(value));
    }

    public static int toInteger(Object value) {
        return Integer.parseInt(String.valueOf(value));
    }

    public static long toLong(Object value) {
        return Long.parseLong(String.valueOf(value));
    }

    public static String toString(Object value) {
        return String.valueOf(value);
    }

    public static String serialize(Object object) {
        XStream xstream = new XStream(new DomDriver());
        String result = xstream.toXML(object);
        return result;
    }

    public static Object deserialize(String object) {
        XStream xstream = new XStream(new DomDriver());
        Object result = xstream.fromXML(object);
        return result;
    }
}
