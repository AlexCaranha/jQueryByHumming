package com.alexcaranha.jquerybyhumming.screen.database.delete;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import com.alexcaranha.jquerybyhumming.mvp.IPresenterChild;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Model;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import com.alexcaranha.jquerybyhumming.screen.database.search.Database_Search_Presenter;
import java.io.IOException;
import java.util.Observable;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.elasticsearch.action.delete.DeleteResponse;

/**
 *
 * @author alexcaranha
 */
public class Database_Delete_Presenter extends Observable implements IPresenterChild {
    private Database_Presenter databasePresenter;

    public Database_Delete_Presenter(Database_Presenter databasePresenter) throws IOException {
        this.databasePresenter = databasePresenter;
    }

    public Database_Presenter getDatabasePresenter() {
        return databasePresenter;
    }

    public void delete() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        String messageLocal[] = {"Ok"};
        String message[] = {"Yes", "No"};

        int i = Util.showMessage("Do you really want to delete the selected item?",
                                 "Exit",
                                 JOptionPane.YES_NO_OPTION,
                                 JOptionPane.QUESTION_MESSAGE, null, message, message[1]);
        //----------------------------------------------------------------------
        if (i == JOptionPane.YES_OPTION) {
            Database_Model mainModel = (Database_Model) databasePresenter.getModel();
            Database_Detail_Model model = ((Database_Search_Presenter) mainModel.getPresenterOption(Database_Model.OPTIONS.SEARCH)).getDatabaseDetailSelected();
            String id = model.getId();

            DeleteResponse response = App.getDB().delete(Database_Detail_Model.type, model.getId());
            if (response.getId().equalsIgnoreCase(id)) {
                Util.showMessage("Requisition succeded!",
                                 "Requisition",
                                 JOptionPane.YES_OPTION,
                                 JOptionPane.QUESTION_MESSAGE, null, messageLocal, messageLocal[0]);
            } else {
                Util.showMessage("Requisition fail.!",
                                 "Requisition",
                                 JOptionPane.YES_OPTION,
                                 JOptionPane.QUESTION_MESSAGE, null, messageLocal, messageLocal[0]);
            }
            databasePresenter.setCurrentOption_Search();
        }
        //----------------------------------------------------------------------
    }

    public void update() {
        // Do nothing for while.
    }

    public void updateButtons() {
        /*
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.SEARCH, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DETAIL, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.INSERT, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.EDIT, false);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DELETE, true);
        */
    }

    public JComponent getView() {
        return null;
    }

    public IModel getModel() {
        return null;
    }
}
