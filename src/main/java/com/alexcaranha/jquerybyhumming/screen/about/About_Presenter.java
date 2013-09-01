package com.alexcaranha.jquerybyhumming.screen.about;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import java.io.IOException;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public class About_Presenter implements IPresenter {

    private About_View  view;
    private About_Model model;

    public About_Presenter() throws IOException {
    	this.model = (About_Model)App.getContext().getBean("about");
        this.view  = new About_View(this);
    }

    public About_Model getModel() {
        return model;
    }

    public JComponent getView() {
        return (JComponent) view;
    }
}
