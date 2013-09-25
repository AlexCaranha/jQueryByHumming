package com.alexcaranha.jquerybyhumming.screen.main;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Presenter;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author alexcaranha
 */
public final class Main_Presenter extends Observable {
    private Main_View  view;
    private Main_Model model;

    public Main_Presenter() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException, Exception {
        model = new Main_Model(Main_Model.SCREENS.ABOUT);
        view  = new Main_View(this);
    }
    
    public void startApplication() throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        App.startApplication();
    }
    
    public void endApplication() {
        App.endApplication();
    }

    public JComponent getCurrentScreen() {
        return model.getCurrentScreen();
    }

    public JFrame getView() {
        return view;
    }

    public void setCurrentOption_SearchSong() {
        model.setCurrentOption(Main_Model.SCREENS.SEARCH_SONG);
        view.update();
    }

    public void setCurrentOption_Database() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setCurrentOption(Main_Model.SCREENS.DATABASE);
        Database_Presenter presenter = (Database_Presenter)model.getCurrentPresenter();
        presenter.setCurrentOption_Search();
        view.update();
    }

    public void setCurrentOption_Configuration() {
        model.setCurrentOption(Main_Model.SCREENS.CONFIGURATION);
        view.update();
    }

    public void setCurrentOption_About() {
        model.setCurrentOption(Main_Model.SCREENS.ABOUT);
        view.update();
    }

    public void setEnabledMicrophone(boolean result) {
        
    }
}
