package com.alexcaranha.jquerybyhumming.screen.database.search;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import com.alexcaranha.jquerybyhumming.mvp.IPresenterChild;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Model;
import com.alexcaranha.jquerybyhumming.screen.database.Database_Presenter;
import com.alexcaranha.jquerybyhumming.screen.database.detail.DatabasePanel;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 *
 * @author alexcaranha
 */
public class Database_Search_Presenter implements IPresenterChild {
    private Database_Presenter databasePresenter;
    private List<Database_Detail_Model> listDatabaseDetail;
    private Database_Search_View view;

    public Database_Search_Presenter(Database_Presenter databasePresenter) {
        this.databasePresenter = databasePresenter;

        this.listDatabaseDetail = new ArrayList<Database_Detail_Model>();
        this.view = new Database_Search_View(this);
    }
    
    public IModel getModel() {
        return null;
    }

    public JComponent getView() {
        return this.view;
    }

    public JPanel getSongsPanel() {
        return null;
    }

    public boolean foundAnySong () {
        return (listDatabaseDetail.size() > 0);
    }

    public void update() {
        App.getDB().readAll(Database_Detail_Model.type, view);
    }

    void update(SearchResponse response) {
        listDatabaseDetail.clear();
        //----------------------------------------------------------------------
        SearchResponse scrollResp;
        while (true) {
            scrollResp = App.getDB().prepareSearchScroll(response);
            boolean hitsRead = false;
            for (SearchHit hit : scrollResp.getHits()) {
                hitsRead = true;
                listDatabaseDetail.add(new Database_Detail_Model(hit.getId(), hit.getSource()));
            }
            //Break condition: No hits are returned
            if (!hitsRead) {
                break;
            }
        }
        //----------------------------------------------------------------------
        view.update(new DatabasePanel(listDatabaseDetail));
        this.updateButtons();
    }

    public Database_Detail_Model getDatabaseDetailSelected() {
        return listDatabaseDetail.get(view.getPanelData().getTable().getSelectedRow());
    }

    public void updateButtons() {
        boolean bolEnabled = foundAnySong();
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.SEARCH, true);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DETAIL, bolEnabled);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.INSERT, true);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.EDIT, bolEnabled);
        databasePresenter.setOptionEnabled(Database_Model.OPTIONS.DELETE, bolEnabled);
        databasePresenter.refreshButtons();
    }
}
