package com.alexcaranha.jquerybyhumming.screen.search.figures;

import com.alexcaranha.jquerybyhumming.components.XYGraphSignal;
import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignalPlayer;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.search.Search_Presenter;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import org.jfree.chart.plot.Marker;

/**
 *
 * @author alexcaranha
 */
public class Search_Figures_Presenter extends Observable implements IPresenter {
    private List<XYGraphSignal>     panels;
    private Search_Presenter        searchPresenter;
    private Search_Figures_Model    model;
    private Search_Figures_View     view;

    private WavSignalPlayer         player;
    
    public Search_Figures_Presenter(Search_Presenter databasePresenter) throws IOException {
        this.player = null;
        this.panels = new ArrayList<XYGraphSignal>();
        
        this.searchPresenter = databasePresenter;
        this.model = new Search_Figures_Model();
        this.view = new Search_Figures_View(this);
    }
    
    public Search_Figures_Model getModel() {
        return model;
    }

    public Search_Presenter getMainSearchPresenter() {
        return searchPresenter;
    }

    public JComponent getView() {
        return view;
    }
    
    public List<XYGraphSignal> getPanelsSignalXY() {
        return this.panels;
    }

    public void associateMidiFile(String pathFile) throws IOException, InvalidMidiDataException, MidiUnavailableException, ClassNotFoundException {
        model.setMidiFile(pathFile);
        updateFigureInView();
    }

    private void updateFigureInView() throws IOException, ClassNotFoundException {
        MelodyRepresentation midiFileSimplified = model.getMidiFileSimplified();

        System.out.println(midiFileSimplified.toString());

        List<Marker> dataset = midiFileSimplified.convertToOnsetMarks(Color.BLUE);

        setChanged();
        notifyObservers(Util.createArray(new KeyValue("CLASS", XYGraphSignal.class),
                                         new KeyValue("ALIAS", "Gantt"),
                                         new KeyValue("DATASET",
                                         Util.createArray(new KeyValue("DATASET", dataset),
                                                          new KeyValue("TITLE_X", "time (in seconds)"),
                                                          new KeyValue("TITLE_Y", "MIDI Notes")))));
    }

    public void back() {
        this.searchPresenter.setCurrentOption_Main();
    }

    public void next() throws IOException {
        this.searchPresenter.setCurrentOption_Result();
    }

    public void play() throws IOException, UnsupportedAudioFileException {
        player = new WavSignalPlayer(Constants.PATH_TMP_WAVE_FILE, Search_Figures_View.class.getSimpleName());
        for(XYGraphSignal panel : this.panels) {
            player.addObserver(panel);
        }
        player.addObserver(view);
        
        Thread myrunnable = new Thread(player, "Player");
        myrunnable.start();

        this.view.updatePlayStopButton(true);
    }

    public void stop() throws IOException {
        this.view.updatePlayStopButton(false);
        player.end();
    }
}
