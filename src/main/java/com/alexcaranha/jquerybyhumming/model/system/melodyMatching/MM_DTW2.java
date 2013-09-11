package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.Convert;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.AbsolutePitch;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.ParsonsCode;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.RelativePitch;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.time.InterOnsetInterval;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.time.InterOnsetIntervalRatio;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.time.LogInterOnsetIntervalRatio;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class MM_DTW2 /*extends MM*/ {
    /*
    private boolean DEBUG = true;
    private double cost;
    
    @Override
    public String getTitle() {
        return "DYNAMIC TIME WARPING 2";
    }

    @Override
    public String getAlias() {
        return "DTW2";
    }
    
    public MM_DTW2(String pitchEncodingSelected,
                  String timeEncodingSelected) {
        
        ConfigurationTableItem variable;
        
        Encoding[] pitchEncoding = Util.createArray(new AbsolutePitch(), new RelativePitch(), new ParsonsCode());
        variable = new ConfigurationTableItem<Encoding>("pitchEncoding", pitchEncoding, (Encoding)Util.getItemByToString(pitchEncoding, pitchEncodingSelected), "Indicates the pitch encoding method.");
        this.variables.put("pitchEncoding", variable);
        
        Encoding[] timeEncoding = Util.createArray(new InterOnsetInterval(), new InterOnsetIntervalRatio(), new LogInterOnsetIntervalRatio());
        variable = new ConfigurationTableItem<Encoding>("timeEncoding", timeEncoding, (Encoding)Util.getItemByToString(timeEncoding, timeEncodingSelected), "Indicates the time encoding method.");
        this.variables.put("timeEncoding", variable);
        
        variable = new ConfigurationTableItem<Double>("k1", null, 6.0, "Is the parameter weighting the relative importance of pitch and time differences.");
        this.variables.put("k1", variable);
        
        variable = new ConfigurationTableItem<Double>("Cdel", null, 0.5, "Note deletion cost.");
        this.variables.put("Cdel", variable);
        
        variable = new ConfigurationTableItem<Double>("Cins", null, 0.5, "Note insertion cost .");
        this.variables.put("Cins", variable);
        
        this.cost = 0.0;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    private double calculateWeight(double pitchTarget, double timeTarget, 
                                   double pitchSequence, double timeSequence, 
                                   Encoding pitchEncoding, Encoding timeEncoding,
                                   double k1) {
        
        double pitchWeight = 0.0;
        double timeWeight = 0.0;
        
        if (pitchEncoding instanceof AbsolutePitch || 
            pitchEncoding instanceof RelativePitch || 
            pitchEncoding instanceof ParsonsCode) {
            
            pitchWeight = Math.abs(pitchTarget - pitchSequence);
        }
        
        if (timeEncoding instanceof InterOnsetInterval || 
            timeEncoding instanceof LogInterOnsetIntervalRatio) {
            
            timeWeight = Math.abs(timeTarget - timeSequence);
        } else if (timeEncoding instanceof InterOnsetIntervalRatio) {
            timeWeight = Math.max(timeTarget/timeSequence, timeSequence/timeTarget);
        }
        
        return pitchWeight + k1 * timeWeight;
    }
        
    public void execute(Map<String, Object> params) throws Exception {        
        List<MelodyRepresentationNote> target = (List<MelodyRepresentationNote>) params.get("target");
        List<MelodyRepresentationNote> sequence = (List<MelodyRepresentationNote>) params.get("sequence");
        
        Encoding pitchEncoding = (Encoding)this.variables.get("pitchEncodingSelected").getValue();
        Encoding timeEncoding = (Encoding)this.variables.get("timeEncodingSelected").getValue();
        
        List<Object> pitchMelodyTarget = pitchEncoding.execute(target);
        List<Object> timeMelodyTarget = pitchEncoding.execute(target);
        
        List<Object> pitchMelodySequence = pitchEncoding.execute(sequence);
        List<Object> timeMelodySequence = pitchEncoding.execute(sequence);
        
        double k = (Double)this.variables.get("k").getValue();
        double Cdel = (Double)this.variables.get("Cdel").getValue();
        double Cins = (Double)this.variables.get("Cins").getValue();
        double Crep = (Double)this.variables.get("Crep").getValue();
        double value;
        
        double[][] distances = new double[target.size()][sequence.size()];
        for(int linha = 0; linha < target.size(); linha += 1) {
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {
                distances[linha][coluna] = calculateWeight(Convert.toDouble(pitchMelodyTarget.get(coluna)), Convert.toDouble(timeMelodyTarget.get(coluna)), 
                                                           Convert.toDouble(pitchMelodySequence.get(linha)), Convert.toDouble(timeMelodySequence.get(coluna)), 
                                                           pitchEncoding, timeEncoding,
                                                           k);
            }
        }
        
        //if (this.DEBUG) printMatrix("distances", distances, target.size(), sequence.size(), melodyTarget, melodySequence);
        
        double[][] path = distances.clone();
        for(int linha = 0; linha < target.size(); linha += 1) {                 // Target   
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {        // Sequence (Query)
                boolean e1 = coluna > 0;
                boolean e2 = linha > 0;
                boolean e3 = e1 && e2;
                
                double distInsertion = (e1) ? distances[linha][coluna-1] + k * Cins : Double.MAX_VALUE;    // coluna > 0
                double distDeletion = (e2) ? distances[linha-1][coluna] + k * Cdel: Double.MAX_VALUE;      // linha > 0
                double distReplacement = (e3) ? distances[linha-1][coluna-1] : Double.MAX_VALUE;            // coluna > 0 && linha > 0
                
                value = distances[linha][coluna] + ((e1 || e2 || e3) 
                                                    ? Util.minDouble(distInsertion, distDeletion, distReplacement)
                                                    : 0);
                path[linha][coluna] = value;
            }
        }
        //if (this.DEBUG) printMatrix("path", path, target.size(), sequence.size(), melodyTarget, melodySequence);
        
        int steps = calculateSteps(path, target.size()-1, sequence.size()-1, 1);
        double lastValue = path[target.size()-1][sequence.size()-1];
        this.cost = steps == 0 ? 0.0 : lastValue / steps;
        
        if (this.DEBUG) System.out.println(String.format("steps: %d, cost: %.4f", steps, cost));
    }
    
    private int calculateSteps(double[][] path, 
                               int linha, int coluna,
                               int steps) {
        
        if (linha == 0 && coluna == 0) {
            return steps;
        }
        
        boolean e1 = linha-1 >= 0;
        boolean e2 = coluna-1 >= 0;
        boolean e3 = e1 && e2;
        
        double vInsertion = e2 ? path[linha][coluna-1] : Integer.MAX_VALUE;         // Insertion
        double vDeletion = e1 ? path[linha-1][coluna] : Integer.MAX_VALUE;          // Deletion
        double vReplacement = e3 ? path[linha-1][coluna-1] : Integer.MAX_VALUE;     // Replacement
        
        double vMin = Util.minDouble(vInsertion, vDeletion, vReplacement);
        
        if (vMin == vReplacement) {
            return calculateSteps(path, linha-1, coluna-1, steps+1);                // Replacement
        } else
        if (vMin == vInsertion) {
            return calculateSteps(path, linha, coluna-1, steps+1);                  // Insertion
        }
        
        return calculateSteps(path, linha-1, coluna, steps+1);                      // Deletion
    }
    
    private void printMatrix(String name, double[][] matrix, 
                             int L, int C, 
                             List<Double> target, List<Double> sequence) {
        
        System.out.print("\nMatrix: " + name);
                
        for(int i = L - 1; i >= 0; i -= 1) {
            System.out.print(String.format("\n%d\t[", target.get(i)));
            for(int j = 0; j < C; j += 1) {
                System.out.print("\t" + matrix[i][j]);
            }
            System.out.print("\t]");
        }
        System.out.print("\n\t");
        for(int j = 0; j < C; j += 1) {
            System.out.print("\t" + sequence.get(j));
        }
        System.out.print("\n");
    }
    
    /*
    public static void main(String[] args) throws Exception  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("target", Util.createArray(1, 2, 3, 4, 6, 7, 8));
        params.put("sequence", Util.createArray(3, 5, 6));
        
        MM_DTW dtw = new MM_DTW();
        dtw.execute(params);
    }
    */
}
