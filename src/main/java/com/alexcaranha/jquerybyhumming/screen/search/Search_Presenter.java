package com.alexcaranha.jquerybyhumming.screen.search;

import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.search.main.Search_Main_Presenter;
import java.io.IOException;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public class Search_Presenter implements IPresenter {
    private Search_View  view;
    private Search_Model model;

    public Search_Presenter() throws IOException, Exception {
        this.model = new Search_Model(Search_Model.OPTION.MAIN, this);
        this.view  = new Search_View(this);
    }

    public JComponent getViewCurrentOption() {
        return model.getPresenterCurrentOption().getView();
    }
    
    public Search_Model getModel() {
        return model;
    }

    public JComponent getView() {
        return view;
    }

    public void setCurrentOption_Main() {
        model.setCurrentOption(Search_Model.OPTION.MAIN);
        view.update();
    }

    public void setCurrentOption_Figures() throws IOException {
        model.setCurrentOption(Search_Model.OPTION.FIGURES);
        //----------------------------------------------------------------------
        ISearchView viewScreen = (ISearchView) model.getPresenterCurrentOption().getView();
        viewScreen.update();
        //----------------------------------------------------------------------
        view.update();
    }

    public void setCurrentOption_Result() throws IOException {
        model.setCurrentOption(Search_Model.OPTION.RESULT);
        //----------------------------------------------------------------------
        ISearchView viewScreen = (ISearchView) model.getPresenterCurrentOption().getView();
        viewScreen.update();
        //----------------------------------------------------------------------
        view.update();
    }

    public IPresenter getPresenter_Main() {
        return model.getPresenterByOption(Search_Model.OPTION.MAIN);
    }
}
