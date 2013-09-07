package com.alexcaranha.jquerybyhumming.screen.database.detail;

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
import com.alexcaranha.jquerybyhumming.screen.database.search.Database_Search_Presenter;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.jfree.chart.plot.Marker;
import org.jfree.data.xy.DefaultXYDataset;

/**
 *
 * @author alexcaranha
 */
public class Database_Detail_Presenter extends Observable implements IPresenterChild {
    private Database_Presenter    databasePresenter;
    private Database_Detail_Model model;
    private WavSignalPlayer       player;

    private Database_Detail_View  view;

    public Database_Detail_Presenter(Database_Presenter databasePresenter) throws IOException {
        this.databasePresenter = databasePresenter;

        this.model = null;
        this.view = new Database_Detail_View(this);
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

    public void back() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        databasePresenter.setCurrentOption_Search();
    }

    private boolean validated() throws IOException, ClassNotFoundException {
        boolean bolAuthor = !model.getAuthor().isEmpty();
        boolean bolTitle = !model.getTitle().isEmpty();
        boolean bolMidiFileSimplified = model.getMidiFileSimplified() != null;

        return (bolAuthor || bolTitle || bolMidiFileSimplified);
    }

    public void update() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        this.deleteObservers();

        Database_Model dbModel = (Database_Model) databasePresenter.getModel();
        this.model = ((Database_Search_Presenter) dbModel.getPresenterOption(Database_Model.OPTIONS.SEARCH)).getDatabaseDetailSelected();

        Map<String, Object> fields = new HashMap<String, Object>();
        MelodyRepresentation melodyRepresentation = model.getMidiFileSimplified();
        melodyRepresentation.createWaveFile(Constants.PATH_TMP_WAVE_SYNTHESIS_FILE);

        fields.put("title", model.getTitle());
        fields.put("author", model.getAuthor());
        fields.put("obs", model.getObs());

        view.update(fields);
        this.addObserver(this.view.getJPanelGraphSignal());

        player = new WavSignalPlayer(Constants.PATH_TMP_WAVE_SYNTHESIS_FILE, Database_Detail_View.class.getSimpleName());
        player.addObserver(view.getJPanelGraphSignal());
        player.addObserver(view);

        updateFigureInView();
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
                new KeyValue("ALIAS", Database_Detail_View.class.getSimpleName()),
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
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DETAIL, true);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.INSERT, false);
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
    
    public static void readAllItemsFromDataBase(List<Database_Detail_Model> list, boolean debug) {
        SearchResponse sResponse = App.getDB().readAll();
        SearchResponse scrollResp;
        while (true) {
            scrollResp = App.getDB().prepareSearchScroll(sResponse);
            boolean hitsRead = false;
            for (SearchHit hit : scrollResp.getHits()) {
                hitsRead = true;
                Database_Detail_Model detail = new Database_Detail_Model(hit.getId(), hit.getSource());
                list.add(detail);
                if (debug) System.out.println("title: " + detail.getTitle());
            }
            //Break condition: No hits are returned
            if (!hitsRead) {
                break;
            }
        }
    }
}
