package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB.STATUS;
import com.alexcaranha.jquerybyhumming.model.system.Database;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author alexcaranha
 */
public class Console {
    public static void processingCommands(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        for(String command : args) {
            if (command.trim().toLowerCase().equalsIgnoreCase("db:clean")) {
                App.startApplication();
                
                while(App.getDB().getStatus() != STATUS.ONLINE) {
                    System.out.println("database status: OFFLINE");
                    Thread.sleep(1000);
                }
                
                App.getDB().clean();
                
                App.endApplication();
            }
            if (command.trim().toLowerCase().equalsIgnoreCase("db:reset")) {
                App.startApplication();
                
                while(App.getDB().getStatus() != STATUS.ONLINE) {
                    System.out.println("database status: OFFLINE");
                    Thread.sleep(1000);
                }
                
                App.getDB().clean();
                Database db = new Database();
                db.execute();
                
                App.endApplication();
            }
        }
    }
}
