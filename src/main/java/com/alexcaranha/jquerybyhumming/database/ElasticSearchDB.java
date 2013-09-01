package com.alexcaranha.jquerybyhumming.database;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationDB;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Presenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

/**
 *
 * @author alexcaranha
 */
public class ElasticSearchDB /*implements Runnable*/ {
    public enum STATUS { ONLINE, OFFLINE, CLOSING };
    
    private TransportClient client;
    private STATUS status;

    public ElasticSearchDB() {
        this.client = null;
        this.status = STATUS.OFFLINE;
        /*
        this.thread = new Thread(this);
        this.thread.start();
        */ 
    }
    
    public void clean() {
        List<Database_Detail_Model> list = new ArrayList<Database_Detail_Model>();
        
        Database_Detail_Presenter.readAllItemsFromDataBase(list, true);
        
        for (Database_Detail_Model item : list) {
            DeleteResponse dResponse = this.delete(Database_Detail_Model.type, item.getId());
            if (dResponse.isNotFound()) {
                System.out.println(String.format("The item id %s is not found in database.", item.getId()));
            }
        }
    }
    
    public STATUS getStatus() {
        if (client == null) return STATUS.OFFLINE;
        this.status = client.connectedNodes().isEmpty() ? STATUS.OFFLINE : STATUS.ONLINE;
        return this.status;
    }

    public void connect() {
        ConfigurationDB database = (ConfigurationDB)App.getConfiguration("database");
        
        if (client != null) this.client.close();
        this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(database.getHostName(), database.getPort()));
    }
    
    public Client getClient() {
        return this.client;
    }

    public void disconnect() {
        this.status = STATUS.CLOSING;
        if (this.client != null) {
            this.client.close();
        }
    }

    public void create(String type, String id,
                       Map<String, Object> source,
                       ActionListener<IndexResponse> indexResponse) {

        if (status == STATUS.ONLINE)
        this.client.prepareIndex(App.class.getPackage().getName(), type, id)
                   .setSource(source)
                   .setOperationThreaded(true)
                   .setPercolate("*")
                   .execute(indexResponse);
    }
    
    public IndexResponse create(String type, String id,
                                Map<String, Object> source) throws InterruptedException, ExecutionException {

        if (status == STATUS.ONLINE){
            return this.client.prepareIndex(App.class.getPackage().getName(), type, id)
                          .setSource(source)
                          .setOperationThreaded(true)
                          .setPercolate("*")
                          .execute()
                          .get();
        }
        return null;
    }
    
    public SearchResponse readAll() {
        
        if (status == STATUS.ONLINE) {
            return client.prepareSearch(App.class.getPackage().getName())
              .setSearchType(SearchType.SCAN)
              .setScroll(new TimeValue(60000))
              .execute()
              .actionGet();
        }
        return null;
    }
     
    public void readAll(String type, 
                        ActionListener<SearchResponse> searchResponse) {
        
        if (status == STATUS.ONLINE)
        client.prepareSearch(App.class.getPackage().getName())
              .setTypes(type)
              .setSearchType(SearchType.SCAN)
              .setScroll(new TimeValue(60000))
              .execute(searchResponse);
    }
        
    public SearchResponse prepareSearchScroll(SearchResponse scrollResp) {
        
        if (status == STATUS.ONLINE) {
            return client.prepareSearchScroll(scrollResp.getScrollId())
                     .setScroll(new TimeValue(60000))
                     .execute()
                     .actionGet();
        } 
        return null;       
    }

    public DeleteResponse delete(String type, String id) {
        if (status == STATUS.ONLINE) {
            return client.prepareDelete(App.class.getPackage().getName(), type, id)
                         .setOperationThreaded(false)
                         .execute()
                         .actionGet();
        }
        return null;    
    }
}
