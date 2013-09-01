package com.alexcaranha.jquerybyhumming.screen.database;

import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import com.alexcaranha.jquerybyhumming.mvp.IPresenterChild;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Model.OPTIONS;
import com.alexcaranha.jquerybyhumming.screen.database.delete.Database_Delete_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.insert.Database_Insert_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.search.Database_Search_Presenter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author alexcaranha
 */
public class Database_Model implements IModel {
    public enum OPTIONS {
        SEARCH, DETAIL, INSERT, EDIT, DELETE
    }

    private Map<OPTIONS, IPresenterChild> options;
    private OPTIONS                  currentKey;

    private Database_View view;

    public Database_Model(Database_Presenter databasePresenter) throws IOException {
        options = new EnumMap<OPTIONS, IPresenterChild>(OPTIONS.class);

        options.put(OPTIONS.DETAIL, new Database_Detail_Presenter(databasePresenter));
        options.put(OPTIONS.INSERT, new Database_Insert_Presenter(databasePresenter));
        options.put(OPTIONS.DELETE, new Database_Delete_Presenter(databasePresenter));
        options.put(OPTIONS.SEARCH, new Database_Search_Presenter(databasePresenter));
    }
    
    public Map<String, Object> getVariables() {
        return null;
    }

    public OPTIONS getKeyCurrentOption() {
        return currentKey;
    }

    public void setCurrentOption(OPTIONS key) throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        currentKey = key;
        if (key != null) {
            IPresenterChild presenterChild = options.get(currentKey);

            if (presenterChild != null) presenterChild.update();
        }
    }

    public IPresenterChild getPresenterCurrentOption() {
        return options.get(currentKey);
    }

    public IPresenterChild getPresenterOption(OPTIONS option) {
        boolean keyExist = options.containsKey(option);
        if (!keyExist) return null;
        return options.get(option);
    }
}