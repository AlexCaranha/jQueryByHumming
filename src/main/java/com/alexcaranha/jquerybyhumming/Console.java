package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB.STATUS;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.Database;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import java.awt.Color;
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
                //es.execute("D:\\Alex\\Mestrado\\Database\\");
                es.execute("/home/alexcaranha/Documentos/Mestrado/DataBase/DBSolfejos");
                //--------------------------------------------------------------
                App.endApplication();
            }
            if (command.trim().toLowerCase().equalsIgnoreCase("graphic")) {
                Database_Detail_Model piano = new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/101.mid").getInputStream(), "Parabéns a você", "Bertha Celeste");
                MelodyRepresentation  musica = piano.getMidiFileSimplified();
                
                Figure.save("", "tempo (segundos)", "Hz",
                    false,
                    Util.createArray(musica.convertToXYSeries("onset detection", false)),
                    Util.createArray(Color.BLACK), null,
                    new KeyValue<Double, Double>(0.0, 13.0), new KeyValue<Double, Double>(250.0, 900.0), 
                    new Figure(600, 200),
                    Util.getDirExecution("parabensPitchTrackingPerfeito.pdf"),
                    Util.getDirExecution("parabensPitchTrackingPerfeito.png"));
            }
        }
    }
}
