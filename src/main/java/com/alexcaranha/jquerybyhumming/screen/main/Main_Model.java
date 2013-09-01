package com.alexcaranha.jquerybyhumming.screen.main;

import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.about.About_Presenter;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Presenter;
import com.alexcaranha.jquerybyhumming.screen.search.Search_Presenter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public final class Main_Model {

    public enum SCREENS {
        SEARCH_SONG, DATABASE, CONFIGURATION, ABOUT
    }

    private Map<SCREENS, IPresenter> presenters;
    private IPresenter               currentPresenter;

    public Main_Model(SCREENS screen) throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException, Exception {
        presenters = new EnumMap<SCREENS, IPresenter>(SCREENS.class);
        presenters.put(SCREENS.SEARCH_SONG, new Search_Presenter());
        presenters.put(SCREENS.DATABASE, new Database_Presenter());
        presenters.put(SCREENS.CONFIGURATION, new Configuration_Presenter());
        presenters.put(SCREENS.ABOUT, new About_Presenter());

        setCurrentOption(screen);
    }

    public void setCurrentOption(SCREENS screen) {
        currentPresenter = presenters.get(screen);
    }
    
    public IPresenter getCurrentPresenter() {
        return currentPresenter;
    }

    public JComponent getCurrentScreen() {
        return currentPresenter.getView();
    }
}
