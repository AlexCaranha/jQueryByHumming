package com.alexcaranha.jquerybyhumming.model.system;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Point;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.MM;

import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.system.onsetDetection.OD;
import com.alexcaranha.jquerybyhumming.model.system.pitchTracking.PT;

import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationMelodyMatching;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationOnsetDetection;
import com.alexcaranha.jquerybyhumming.screen.configurations.ConfigurationPitchTracking;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class Processing {
    private WavSignal signal;
    
    private PT pt;
    private OD od;
    private MelodyRepresentation mr;
    private MM mm;
    
    private List<Database_Detail_Model> listSongs;
    private List<Point<Double, Database_Detail_Model>> result;
    
    private Map timeProcessing;
    
    public Map getTimeProcessing() {
        return timeProcessing;
    }

    public Processing(WavSignal signal) throws Exception {
        this.result = new ArrayList<Point<Double, Database_Detail_Model>>();
        
        this.listSongs = new ArrayList<Database_Detail_Model>();
        Database_Detail_Presenter.readAllItemsFromDataBase(this.listSongs, false); 
        this.signal = signal;
        
        ConfigurationPitchTracking cpt = (ConfigurationPitchTracking) App.getConfiguration("pitchTracking");
        this.pt = (PT) cpt.getSelected();

        ConfigurationOnsetDetection cod = (ConfigurationOnsetDetection) App.getConfiguration("onsetDetection");
        this.od = (OD) cod.getSelected();
        
        this.mr = new MelodyRepresentation();
        
        ConfigurationMelodyMatching cmm = (ConfigurationMelodyMatching) App.getConfiguration("melodyMatching");
        this.mm = (MM) cmm.getSelected();
        
        timeProcessing = new HashMap();
    }
    
    public PT getPitchTracking() {
        return this.pt;
    };
    
    public OD getOnsetDetection() {
        return this.od;
    };
    
    public MelodyRepresentation getMelodyRepresentation() {
        return this.mr;
    };
    
    public void execute() throws Exception {
        long start, delay;
        
        List<Configuration> listConfiguration = new ArrayList<Configuration>();
        listConfiguration.add(this.pt);        
        listConfiguration.add(this.od);
        listConfiguration.add(this.mr);
        listConfiguration.add(this.mm);
        
        this.result.clear();
        this.timeProcessing.clear();
        for(Configuration item : listConfiguration) {
            start = System.currentTimeMillis();
            if (item instanceof IExecutable) {
                if (item instanceof PT) {
                    PT pitchTracking = (PT) item;
                    pitchTracking.execute(
                            Util.createMap(new KeyValue<String, Object>("wavSignal", signal))
                    );
                } else
                if (item instanceof OD) {
                    OD onsetDetection = (OD) item;
                    onsetDetection.execute(
                            Util.createMap(new KeyValue<String, Object>("wavSignal", signal))
                    );
                } else
                if (item instanceof MelodyRepresentation) {
                    MelodyRepresentation melodyRepresentation = (MelodyRepresentation) item;
                    melodyRepresentation.execute(
                        Util.createMap(
                            new KeyValue("pitchTracking", this.pt.getSignalXY()),
                            new KeyValue("onsetDetection", this.od.getMarkers())
                        )
                    );
                } else
                if (item instanceof MM) {
                    MM melodyMatching = (MM) item;
                    
                    result.clear();
                    for(int index = 0; index < this.listSongs.size(); index += 1) {
                        Database_Detail_Model song = this.listSongs.get(index);
                                                
                        melodyMatching.execute(
                                Util.createMap(
                                    new KeyValue("target", song.getMidiFileSimplified().getMelody()),
                                    new KeyValue("sequence", this.mr.getMelody())
                                )
                        );
                        
                        double cost = melodyMatching.getCost();
                        result.add(new Point<Double, Database_Detail_Model>(cost, song));
                        System.out.println(String.format("Song number: %d, Cost: %.4f, title: %s", index, cost, song.getTitle()));
                    }
                    //----------------------------------------------------------
                    // Ordenação.
                    for (int i = 0; i < result.size() - 1; i += 1 ) {
                        for (int j = i + 1; j < result.size(); j += 1 ) {
                            double ei = result.get(i).getX();
                            double ej = result.get(j).getX();
                            
                            if (ei > ej) {
                                Point<Double, Database_Detail_Model> pointAux = result.get(i);
                                result.set(i, result.get(j));
                                result.set(j, pointAux);
                            }
                        }                        
                    }
                    //----------------------------------------------------------
                    System.out.println("-------------");
                    System.out.println("Saída: ");
                    for(Point<Double, Database_Detail_Model> point : result) {
                        double cost = point.getX();
                        Database_Detail_Model song = point.getY();
                        System.out.println(String.format("Custo: %.4f, title: %s", cost, song.getTitle()));
                    }
                    System.out.println("-------------");
                    //----------------------------------------------------------
                }
            }
            delay = System.currentTimeMillis() - start;

            timeProcessing.put(item.toString(), delay);
        }
    }
}