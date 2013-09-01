package com.alexcaranha.jquerybyhumming.screen.search.main;

import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.WavSignalPlayer;
import com.alexcaranha.jquerybyhumming.model.system.Processing;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.search.Search_Presenter;
import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author alexcaranha
 */
public class Search_Main_Presenter implements IPresenter {

    private Search_Presenter  searchPresenter;
    private Search_Main_Model model;
    private WavSignalPlayer   player;

    private Search_Main_View  view;

    public Search_Main_Presenter(Search_Presenter searchPresenter) throws IOException, Exception {
        this.searchPresenter = searchPresenter;
        this.model = new Search_Main_Model();
        this.view = new Search_Main_View(this);

        WavSignal wavSignal = ((WavSignal) this.model.getVariable("wavSignal"));
        wavSignal.addObserver(this.view.getJPanelGraphSignal());
        wavSignal.addObserver(this.view);
        
        this.player = null;
    }
    
    public Search_Main_Model getModel() {
        return this.model;
    }
    
    public JComponent getView() {
        return this.view;
    }

    public void selectWavFile(InputStream inputStream) throws Exception {
        WavSignal signal = ((WavSignal) this.model.getVariable("wavSignal"));
        signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", inputStream),
                                              new KeyValue<String, Object>("notifyObservers", true),
                                              new KeyValue<String, Object>("alias", Search_Main_View.class.getSimpleName())));
    }

    public void play() throws IOException {
        Thread myrunnable = new Thread(this.player, "Player");
        myrunnable.start();

        this.view.updatePlayStopButton(true);
    }

    public void stop() throws IOException {
        this.view.updatePlayStopButton(false);
        this.player.end();
    }
    
    public void updatePlayer() throws IOException, UnsupportedAudioFileException {
        this.player = new WavSignalPlayer(Constants.PATH_TMP_WAVE_FILE, Search_Main_View.class.getSimpleName());
                                     this.player.addObserver(this.view.getJPanelGraphSignal());
                                     this.player.addObserver(this.view);
                                     this.view.setEnabledPlayStopButton(true);
    }

    public void ok() {
        try{
            this.view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Thread processing = createThreadProcessing();
            processing.start();
        } finally {
            this.view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private Thread createThreadProcessing() {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                Processing processing = null;
                try {
                    processing = new Processing((WavSignal) model.getVariable("wavSignal"));
                    processing.execute();
                } catch (Exception ex) {
                    processing = null;
                    Logger.getLogger(Search_Main_Model.class.getName()).log(Level.SEVERE, null, ex);
                    
                    String message[] = {"Ok"};
                    Util.showMessage("Process fail! Error message: " + ex.getMessage(),
                                     "Requisition",
                                     JOptionPane.YES_OPTION,
                                     JOptionPane.QUESTION_MESSAGE, null, message, message[0]);
                } finally {
                    model.setVariable("processing", processing);
                }
                
                try {
                    searchPresenter.setCurrentOption_Figures();
                } catch (IOException ex) {
                    Logger.getLogger(Search_Main_Presenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return thread;
    }
}
