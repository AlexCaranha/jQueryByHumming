package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB.STATUS;
import com.alexcaranha.jquerybyhumming.model.system.Database;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author alexcaranha
 */
public class Console {
    public static void processingCommands(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException, WavFileException, Exception {
        for(String command : args) {
            if (command.trim().toLowerCase().equalsIgnoreCase("db:clean")) {
                App.startApplication();
                
                for(int i = 1; i <= 10 && App.getDB().getStatus() != STATUS.ONLINE; i += 1) {
                    System.out.println("database status: OFFLINE");
                    Thread.sleep(1000);
                }
                if (App.getDB().getStatus() != STATUS.ONLINE) continue;
                
                App.getDB().clean();                
                App.endApplication();
            }
            if (command.trim().toLowerCase().equalsIgnoreCase("db:reset")) {
                App.startApplication();
                
                for(int i = 1; i <= 10 && App.getDB().getStatus() != STATUS.ONLINE; i += 1) {
                    System.out.println("database status: OFFLINE");
                    Thread.sleep(1000);
                }
                if (App.getDB().getStatus() != STATUS.ONLINE) continue;
                
                App.getDB().clean();
                Database db = new Database();
                db.execute();
                
                App.endApplication();
            }
            if (command.trim().toLowerCase().equalsIgnoreCase("mm:evaluation")) {
                App.startApplication();
                
                for(int i = 1; i <= 10 && App.getDB().getStatus() != STATUS.ONLINE; i += 1) {
                    System.out.println("database status: OFFLINE");
                    Thread.sleep(1000);
                }
                if (App.getDB().getStatus() != STATUS.ONLINE) continue;
                //--------------------------------------------------------------
                EvaluationSystem es = new EvaluationSystem();
                es.execute("D:\\Alex\\Mestrado\\Database\\");
                //--------------------------------------------------------------
                App.endApplication();
            }
        }
    }
}
