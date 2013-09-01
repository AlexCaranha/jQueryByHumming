package com.alexcaranha.jquerybyhumming.screen.database.insert;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.components.XYGraphSignal;
import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignalPlayer;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.mvp.IPresenterChild;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Model;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import org.jfree.chart.plot.Marker;
import org.jfree.data.xy.DefaultXYDataset;

/**
 *
 * @author alexcaranha
 */
public class Database_Insert_Presenter extends Observable implements IPresenterChild {

    private Database_Presenter    databasePresenter;
    private Database_Detail_Model model;
    private WavSignalPlayer       player;

    private Database_Insert_View  view;

    public Database_Insert_Presenter(Database_Presenter databasePresenter) throws IOException {
        this.databasePresenter = databasePresenter;
        this.model = new Database_Detail_Model();
        this.view = new Database_Insert_View(this);
        this.player = null;
    }

    public Database_Presenter getDatabasePresenter() {
        return databasePresenter;
    }
    
    public Database_Detail_Model getModel() {
        return model;
    }

    public JComponent getView() {
        return view;
    }

    public void save() throws IOException, ClassNotFoundException {
        model.setTitle(view.getTitle());
        model.setAuthor(view.getAuthor());
        model.setObs(view.getObs());

        if (!validated()) {
            view.dataInvalidated();
            return;
        }

        App.getDB().create(Database_Detail_Model.type, null, model.getVariables(), view);
    }

    public void back() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.clear();
        databasePresenter.setCurrentOption_Search();
    }

    private boolean validated() throws IOException, ClassNotFoundException {
        boolean bolAuthor = !model.getAuthor().isEmpty();
        boolean bolTitle = !model.getTitle().isEmpty();
        boolean bolMidiFileSimplified = model.getMidiFileSimplified() != null;

        return (bolAuthor && bolTitle && bolMidiFileSimplified);
    }

    public void associateMidiFile(String pathFile) throws IOException, InvalidMidiDataException, MidiUnavailableException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setMidiFile(new FileInputStream(pathFile));
        MelodyRepresentation melodyRepresentation = model.getMidiFileSimplified();
        melodyRepresentation.createWaveFile(Constants.PATH_TMP_WAVE_FILE);
        player = new WavSignalPlayer(Constants.PATH_TMP_WAVE_FILE, Database_Insert_View.class.getSimpleName());
        player.addObserver(view.getJPanelGraphSignal());
        player.addObserver(view);

        updateFigureInView();
        view.setEnabledPlayStopButton(true);
    }

    public void update() {
        this.deleteObservers();
        Map<String, Object> fields = new HashMap<String, Object>();

        model.clear();

        try {
            fields.put("title", model.getTitle());
            fields.put("author", model.getAuthor());
            fields.put("obs", model.getObs());
            fields.put("midiFileSimplified", model.getMidiFileSimplified() != null);
        } catch (IOException ex) {
            Logger.getLogger(Database_Insert_Presenter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Insert_Presenter.class.getName()).log(Level.SEVERE, null, ex);
        }

        view.update(fields);
        this.addObserver(this.view.getJPanelGraphSignal());
    }

    private void updateFigureInView() throws IOException, ClassNotFoundException {
        MelodyRepresentation midiFileSimplified = model.getMidiFileSimplified();

        if (midiFileSimplified == null) return;

        // System.out.println(midiFileSimplified.toString());

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("serie1", midiFileSimplified.convertToXYSeries("Signal", true).toArray());

        setChanged();
        notifyObservers(
            Util.createArray(
                new KeyValue<String, Class>("CLASS", XYGraphSignal.class),
                new KeyValue("ALIAS", Database_Insert_View.class.getSimpleName()),
                new KeyValue("DATASET", Util.createArray(
                    new KeyValue("DATASET", dataset),
                    new KeyValue("TITLE_X", "time (in seconds)"),
                    new KeyValue("TITLE_Y", "MIDI Notes")
                )),
                new KeyValue<String, List<Marker>>("MARKERS", midiFileSimplified.convertToOnsetMarks(Color.BLUE)),
                new KeyValue<String, KeyValue<Double, Double>>("LIMIT-X", new KeyValue(0.0, null))
            )
        );
    }

    public void updateButtons() {
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.SEARCH, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DETAIL, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.INSERT, true);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.EDIT, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DELETE, false);
    }

    public void play() throws IOException {
        Thread myrunnable = new Thread(player, "Player");
        myrunnable.start();

        this.view.updatePlayStopButton(true);
    }

    public void stop() throws IOException {
        this.view.updatePlayStopButton(false);
        player.end();
    }
}
