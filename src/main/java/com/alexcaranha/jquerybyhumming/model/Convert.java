package com.alexcaranha.jquerybyhumming.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.xerial.snappy.Snappy;

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

    /*
    public static byte[] serialize(Object obj) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");
        FileOutputStream fos = new FileOutputStream(tempFile);
        XStream xstream = new XStream(new DomDriver());
        xstream.toXML(obj, fos);
        fos.close();

        byte[] buffer = IOUtils.toByteArray(new FileInputStream(tempFile));

        Util.deleteFile(tempFile.getAbsolutePath());

        return Snappy.compress(buffer);
    }
    */
    /*
    public static String deserializeFile(byte[] buffer) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile)); //Criamos o arquivo
        bos.write(Snappy.uncompress(buffer));
        bos.close();

        return tempFile.getAbsolutePath();
    }
    */
    /*
    public static <T> T deserialize(byte[] buffer) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile)); //Criamos o arquivo
        bos.write(Snappy.uncompress(buffer));
        bos.close();

        InputStream inputStream = new FileInputStream(tempFile);

        XStream xstream = new XStream(new DomDriver());
        T result = (T) xstream.fromXML(inputStream);
        Util.deleteFile(tempFile.getAbsolutePath());

        return result;
    }
    */
    /*
    public static String serialize(Object obj) throws IOException {
        String tempFile = File.createTempFile("temp", ".tmp").getAbsolutePath();

        FileOutputStream fos = new FileOutputStream(tempFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();

        return tempFile;
    }

    public static <T> T deserialize(String buffer) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile)); //Criamos o arquivo
        bos.write(Snappy.uncompress(buffer));
        bos.close();

        InputStream inputStream = new FileInputStream(tempFile);

        XStream xstream = new XStream(new DomDriver());
        T result = (T) xstream.fromXML(inputStream);
        Util.deleteFile(tempFile.getAbsolutePath());

        return result;
    }
    */
    /*
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

    }
    */
    
    public static void serialize(Object object, OutputStream output) {
        XStream xstream = new XStream(new DomDriver());
        xstream.toXML(object, output);
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
    
    public static Object deserialize(InputStream inputStream) {
        XStream xstream = new XStream(new DomDriver());
        Object result = xstream.fromXML(inputStream);
        return result;
    }

    public static String serializeFile(String path) throws IOException {
        byte[] buffer = IOUtils.toByteArray(new FileInputStream(path));
        byte[] bufferCompressed = Snappy.compress(buffer);
        String result = new String(bufferCompressed, "UTF-8");
        return result;
    }

}
