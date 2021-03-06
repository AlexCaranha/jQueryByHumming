package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB;
import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.Microphone;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationDB;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationMelodyMatching;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationOnsetDetection;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationPitchTracking;
import com.alexcaranha.jquerybyhumming.screen.main.Main_Presenter;
import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    
    private static       Microphone microphone = new Microphone(Constants.PATH_TMP_WAVE_FILE_SEARCH);
    private static final ElasticSearchDB db = new ElasticSearchDB();
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("configuration/spring-config.xml");

    public static Configuration getConfiguration(String key) {
        return configurations.get(key);
    }
    
    public static Object getObject(String key) {
        return objects.get(key);
    }
    
    public static void loadConfigurations() throws FileNotFoundException {
        configurations.clear();
        configurations.put("database", (Configuration) App.getContext().getBean("database"));
        configurations.put("pitchTracking", (Configuration) App.getContext().getBean("pitchTracking"));
        configurations.put("onsetDetection", (Configuration) App.getContext().getBean("onsetDetection"));
        configurations.put("melodyMatching", (Configuration) App.getContext().getBean("melodyMatching"));        
        //----------------------------------------------------------------------
        String path = Util.getDirExecution("configuration.xml");
        if (Util.fileExist(path)) {
            XStream xstream = new XStream();
            List<Configuration> configurationsFile = (List<Configuration>) xstream.fromXML(new FileInputStream(path));
            for(Configuration config : configurationsFile) {
                if (config instanceof ConfigurationDB) {
                    configurations.put("database", config);
                } else if (config instanceof ConfigurationPitchTracking) {
                    configurations.put("pitchTracking", config);
                } else if (config instanceof ConfigurationOnsetDetection) {
                    configurations.put("onsetDetection", config);
                } else if (config instanceof ConfigurationMelodyMatching) {
                    configurations.put("melodyMatching", config);
                }
            }
        }
        //----------------------------------------------------------------------
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
    
    public static Microphone getMicrophone() {
        return microphone;
    }
    
    public static void resetMicrophone() {
        microphone = new Microphone(Constants.PATH_TMP_WAVE_FILE_SEARCH);
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
