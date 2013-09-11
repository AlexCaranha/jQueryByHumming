package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Distances;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class MM_DTW extends MM {
    private boolean DEBUG = false;
    private double cost;
    
    @Override
    public String getTitle() {
        return "DYNAMIC TIME WARPING";
    }

    @Override
    public String getAlias() {
        return "DTW";
    }
    
    public MM_DTW(String pitchEncoding, int N) {
        super(N);
        
        ConfigurationTableItem variable;        
        variable = new ConfigurationTableItem<Encoding>("pitchEncoding", pitchEncodingArray, (Encoding)Util.getItemByToString(pitchEncodingArray, pitchEncoding), "Indicates the pitch encoding method.");
        this.variables.put("pitchEncoding", variable);
        
        this.cost = 0.0;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public void execute(Map<String, Object> params) throws Exception {        
        List<MelodyRepresentationNote> target = (List<MelodyRepresentationNote>) params.get("target");
        List<MelodyRepresentationNote> sequence = (List<MelodyRepresentationNote>) params.get("sequence");
        
        Encoding pitchEncoding = getPitchEncoding();
        
        List<Object> melodyTarget = pitchEncoding.execute(target);        
        List<Object> melodySequence = pitchEncoding.execute(sequence);
        
        double[][] distances = new double[target.size()][sequence.size()];
        for(int linha = 0; linha < target.size(); linha += 1) {
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {
                distances[linha][coluna] = Distances.euclidian(Convert.toDouble(melodySequence.get(coluna)), Convert.toDouble(melodyTarget.get(linha)));
            }
        }
        
        if (this.DEBUG) printMatrix("distances", distances, target.size(), sequence.size(), melodyTarget, melodySequence);
        
        double[][] path = distances.clone();
        for(int linha = 0; linha < target.size(); linha += 1) {
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {            
                boolean e1 = coluna > 0;
                boolean e2 = linha > 0;
                boolean e3 = e1 && e2;
                
                double value = distances[linha][coluna];
                value += (e1 || e2 || e3) 
                            ? Util.minDouble(
                                    (e1) ? distances[linha][coluna-1] : Double.MAX_VALUE, 
                                    (e2) ? distances[linha-1][coluna] : Double.MAX_VALUE, 
                                    (e3) ? distances[linha-1][coluna-1] : Double.MAX_VALUE)
                            : 0;
                path[linha][coluna] = value;
            }
        }
        if (this.DEBUG) printMatrix("path", path, target.size(), sequence.size(), melodyTarget, melodySequence);
        
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
        
        double v1 = e1 ? path[linha-1][coluna] : Integer.MAX_VALUE;
        double v2 = e2 ? path[linha][coluna-1] : Integer.MAX_VALUE;
        double v3 = e3 ? path[linha-1][coluna-1] : Integer.MAX_VALUE;
        
        double min = Util.minDouble(v1, v2, v3);
        
        if (v3 == min) {
            return calculateSteps(path, linha-1, coluna-1, steps+1);
        } else
        if (v2 == min) {
            return calculateSteps(path, linha, coluna-1, steps+1);
        }
        return calculateSteps(path, linha-1, coluna, steps+1);
    }
    
    private void printMatrix(String name, double[][] matrix, 
                             int L, int C, 
                             List<Object> target, List<Object> sequence) {
        
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
