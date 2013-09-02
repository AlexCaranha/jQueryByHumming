package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.main.Main_Presenter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alexcaranha
 */
public class App {
    private static final Map<String, Configuration> configurations = new HashMap<String, Configuration>();
    private static final Map<String, Object> objects = new HashMap<String, Object>();
    
    private static final ElasticSearchDB db = new ElasticSearchDB();
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("configuration/spring-config.xml");

    public static Configuration getConfiguration(String key) {
        return configurations.get(key);
    }
    
    public static Object getObject(String key) {
        return objects.get(key);
    }
    
    public static void loadConfigurations() {
        configurations.clear();
        configurations.put("database", (Configuration) App.getContext().getBean("database"));
        configurations.put("pitchTracking", (Configuration) App.getContext().getBean("pitchTracking"));
        configurations.put("onsetDetection", (Configuration) App.getContext().getBean("onsetDetection"));
    }
    
    public static Configuration reloadConfiguration(String key) {
        return configurations.put(key, (Configuration) App.getContext().getBean(key));
    }
    
    public static void loadObject(String key, Object object) {
        objects.put(key, object);
    }
    
    public static void unloadConfigurations() {
        configurations.clear();
    }
    
    public static void unloadObjects() {
        objects.clear();
    }
   
    public static void startApplication() throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        App.loadConfigurations();        
        db.connect();
    }

    public static void endApplication() {
        db.disconnect();
        App.unloadConfigurations();
        App.unloadObjects();
    }

    public static ElasticSearchDB getDB() {
        return db;
    }

    public static ApplicationContext getContext() {
        return context;
    }

//    In ElasticSearch, a Document is the unit of search and index.
//    An index consists of one or more Documents, and a Document consists of one or more Fields.
//    In database terminology, a Document corresponds to a table row, and a Field corresponds to a table column.
    public static void main(String[] args) throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException, Exception {
        if (args.length > 0) {
            Console.processingCommands(args);
            return;
        }
        
        startApplication();
        
        Main_Presenter presenterMain = new Main_Presenter();
        JFrame viewMain = (JFrame) presenterMain.getView();
        
        viewMain.setResizable(true);
        viewMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewMain.setVisible(true);
        
        App.loadObject("main", presenterMain);        
    }
}
