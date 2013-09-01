package com.alexcaranha.jquerybyhumming.screen.search;

import com.alexcaranha.jquerybyhumming.mvp.IModel;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.search.figures.Search_Figures_Presenter;
import com.alexcaranha.jquerybyhumming.screen.search.main.Search_Main_Presenter;
import com.alexcaranha.jquerybyhumming.screen.search.result.Search_Result_Presenter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public final class Search_Model implements IModel{
    public enum OPTION {
        MAIN, FIGURES, RESULT
    }

    private Map<OPTION, IPresenter> options;
    private OPTION                  currentKey;

    private Search_View view;

    public Search_Model(OPTION option, Search_Presenter searchPresenter) throws IOException, Exception {
        this.options = new EnumMap<OPTION, IPresenter>(OPTION.class);
        this.options.put(OPTION.MAIN, new Search_Main_Presenter(searchPresenter));
        this.options.put(OPTION.FIGURES, new Search_Figures_Presenter(searchPresenter));
        this.options.put(OPTION.RESULT, new Search_Result_Presenter(searchPresenter));

        this.setCurrentOption(option);
    }
    
    public Map<String, Object> getVariables() {
        return null;
    }

    public OPTION getKeyCurrentOption() {
        return this.currentKey;
    }

    public void setCurrentOption(OPTION key) {
        this.currentKey = key;
    }

    public IPresenter getPresenterCurrentOption() {
        return this.options.get(this.currentKey);
    }
    
    public IPresenter getPresenterByOption(OPTION key) {
        return this.options.get(key);
    }
}