package com.alexcaranha.jquerybyhumming.screen.database;

import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.mvp.IPresenterChild;
import com.alexcaranha.jquerybyhumming.screen.database.delete.Database_Delete_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.search.Database_Search_Presenter;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public class Database_Presenter implements IPresenter {

    private Database_View  view;
    private Database_Model model;

    public Database_Presenter() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model = new Database_Model(this);
        view  = new Database_View(this);

        setCurrentOption_Search();
    }

    public JComponent getCurrentOption() {
        IPresenterChild presenterChild = model.getPresenterCurrentOption();
        if (presenterChild == null) return null;
        return presenterChild.getView();
    }
    
    public Database_Model getModel() {
        return model;
    }

    public JComponent getView() {
        return view;
    }

    public void setCurrentOption_Search() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setCurrentOption(Database_Model.OPTIONS.SEARCH);
        IPresenterChild presenterChild = model.getPresenterOption(Database_Model.OPTIONS.SEARCH);
        if (presenterChild != null) presenterChild.updateButtons();
        view.update();
    }

    public void setCurrentOption_Detail() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setCurrentOption(Database_Model.OPTIONS.DETAIL);
        IPresenterChild presenterChild = model.getPresenterOption(Database_Model.OPTIONS.DETAIL);
        if (presenterChild != null) presenterChild.updateButtons();
        view.update();
    }

    public void setCurrentOption_Insert() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setCurrentOption(Database_Model.OPTIONS.INSERT);
        IPresenterChild presenterChild = model.getPresenterOption(Database_Model.OPTIONS.INSERT);
        if (presenterChild != null) presenterChild.updateButtons();
        view.update();
    }

    public void setCurrentOption_Edit() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        model.setCurrentOption(Database_Model.OPTIONS.EDIT);
        IPresenterChild presenterChild = model.getPresenterOption(Database_Model.OPTIONS.EDIT);
        if (presenterChild != null) presenterChild.updateButtons();
        view.update();
    }

    public void setCurrentOption_Delete() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException {
        Database_Delete_Presenter presenterDelete = (Database_Delete_Presenter) model.getPresenterOption(Database_Model.OPTIONS.DELETE);
        presenterDelete.delete();
    }

    public void setOptionEnabled(Database_Model.OPTIONS option, boolean enabled) {
        IPresenter presenterOption = model.getPresenterOption(option);
        if (presenterOption == null) return;
        JComponent viewOption = presenterOption.getView();
        if (viewOption == null) return;
        viewOption.setEnabled(enabled);
    }

    public boolean isOptionEnabled(Database_Model.OPTIONS option) {
        IPresenter presenter = model.getPresenterOption(option);
        if (presenter == null) return false;

        if (presenter instanceof Database_Delete_Presenter) {
            Database_Model dbModel = (Database_Model) this.getModel();
            Database_Search_Presenter databaseSearchPresenter = ((Database_Search_Presenter) dbModel.getPresenterOption(Database_Model.OPTIONS.SEARCH));

            return databaseSearchPresenter.foundAnySong() && isOptionEnabled(Database_Model.OPTIONS.SEARCH);
        }
        return presenter.getView().isEnabled();
    }

    public void refreshButtons() {
        view.update();
    }
}
