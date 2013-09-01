package com.alexcaranha.jquerybyhumming.mvp;

import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public interface IPresenter {
    IModel getModel();
    JComponent getView();
}