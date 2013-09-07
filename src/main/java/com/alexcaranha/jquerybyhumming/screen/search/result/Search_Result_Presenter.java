package com.alexcaranha.jquerybyhumming.screen.search.result;

import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.search.Search_Presenter;
import java.io.IOException;
import java.util.Observable;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public class Search_Result_Presenter extends Observable implements IPresenter {

    private Search_Presenter    searchPresenter;
    private Search_Result_Model model;
    private Search_Result_View  view;

    public Search_Result_Presenter(Search_Presenter searchPresenter) throws IOException {
        this.searchPresenter = searchPresenter;
        this.model = new Search_Result_Model();
        this.view = new Search_Result_View(this);
    }
    
    public Search_Result_Model getModel() {
        return model;
    }

    public Search_Presenter getMainSearchPresenter() {
        return searchPresenter;
    }

    public JComponent getView() {
        return view;
    }

    public void back() throws IOException {
        this.searchPresenter.setCurrentOption_Figures();
    }
}
