package com.alexcaranha.jquerybyhumming.model;

import com.alexcaranha.jquerybyhumming.components.XYGraphSignal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;


/**
 *
 * @author alexcaranha
 */
public class WavSignalPlayer extends Observable implements Runnable {

    private int                     iDataList;
    private AudioFormat             audioFormat;
    private State                   state;
    private List<KeyValue>          bufferAudio, bufferAudioBackup;
    private List<String>            errors;
    private Double                  durationInSeconds;
    private String                  alias;
    private TPlayingPlotLine        line;

    public enum State {
        FREE, LOCKED
    }

    public WavSignalPlayer(String path, String alias) throws UnsupportedAudioFileException, IOException {
        this.alias = alias;

        Map<String, Object> result = WavSignal.loadBufferAudio(path, this.audioFormat);
        this.bufferAudioBackup = (List<KeyValue>) result.get("bufferAudio");
        this.audioFormat = (AudioFormat) result.get("audioFormat");
        this.durationInSeconds = ((Double) result.get("durationInSeconds")).doubleValue();

        this.line = new TPlayingPlotLine(100);

        this.errors = new ArrayList<String>();
        this.state = State.FREE;
    }

    public void end() {
        this.state = State.FREE;
    }

    @Override
    public void run() {
        //------------------------------------------------------------------
        DataLine.Info   info;
        SourceDataLine  auline = null;
        int             nBytesRead;
        byte[]          abData;
        //------------------------------------------------------------------
        info        = new DataLine.Info(SourceDataLine.class, audioFormat);
        errors.clear();
        bufferAudio = (List<KeyValue>) ((ArrayList<KeyValue>)bufferAudioBackup).clone();
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(audioFormat);
        } catch (LineUnavailableException e) {
            this.errors.add(e.getMessage());
            return;
        } catch (Exception e) {
            this.errors.add(e.getMessage());
            return;
        }

        if (bufferAudio.size() > 0){
            try{
                state = State.LOCKED;
                this.line.start();

                setChanged();
                notifyObservers(Util.createArray(new KeyValue("CLASS", XYGraphSignal.class),
                                                 new KeyValue("ALIAS", this.alias),
                                                 new KeyValue("LINE", new KeyValue("VISIBLE", true))));

                auline.start();
                for (iDataList = 0 ; iDataList < bufferAudio.size() && state == State.LOCKED; iDataList += 1){
                    KeyValue pair = bufferAudio.get(iDataList);
                    abData     = (byte[]) pair.getKey();
                    nBytesRead = Convert.toInteger(pair.getValue());

                    auline.write(abData, 0, nBytesRead);
                }
            } catch(Exception ex) {
                errors.add(ex.getMessage());
            } finally{
                bufferAudio.clear();
                setChanged();
                notifyObservers(Util.createArray(new KeyValue("CLASS", XYGraphSignal.class),
                                                 new KeyValue("ALIAS", this.alias),
                                                 new KeyValue("LINE", new KeyValue("VISIBLE", false))));
                this.line.stop();
                state = State.FREE;
            }
        }

        auline.drain();
        auline.close();

        setChanged();
        notifyObservers(Util.createArray(new KeyValue("CLASS", null),
                                         new KeyValue("ALIAS", this.alias),
                                         new KeyValue("STATE", false)));
        //------------------------------------------------------------------
    }

    private class TPlayingPlotLine extends Timer implements ActionListener {

        public TPlayingPlotLine(int interval) {
                super(interval, null);
                addActionListener(this);
            }

        @Override
        public void actionPerformed(ActionEvent e) {
            double value = (double) iDataList * durationInSeconds / bufferAudio.size();

            setChanged();
            notifyObservers(Util.createArray(new KeyValue("CLASS", XYGraphSignal.class),
                                             new KeyValue("ALIAS", alias),
                                             new KeyValue("LINE", new KeyValue("VALUE", value))));
        }
    }
}