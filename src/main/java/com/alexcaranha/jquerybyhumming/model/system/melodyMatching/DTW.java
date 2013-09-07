package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class DTW extends MelodyMatching {
    private boolean DEBUG = false;
    private double cost;
    
    public DTW() {
        this.cost = 0.0;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    private void printMatrix(String name, int[][] matrix, 
                             int L, int C, 
                             List<Integer> target, List<Integer> sequence) {
        
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

    public void execute(Map<String, Object> params) throws Exception {        
        MelodyRepresentation target = (MelodyRepresentation) params.get("target");
        MelodyRepresentation sequence = (MelodyRepresentation) params.get("sequence");
        
        target.
        
        /*
        List<Integer> target = (List<Integer>)params.get("target");
        List<Integer> sequence = (List<Integer>)params.get("sequence");
        
        int[][] distances = new int[target.size()][sequence.size()];
        for(int linha = 0; linha < target.size(); linha += 1) {
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {
                int value = (sequence.get(coluna) - target.get(linha));
                distances[linha][coluna] = Math.abs(value);
            }
        }
        
        if (this.DEBUG) printMatrix("distances", distances, target.size(), sequence.size(), target, sequence);
        
        int[][] path = distances.clone();
        for(int linha = 0; linha < target.size(); linha += 1) {
            for(int coluna = 0; coluna < sequence.size(); coluna += 1) {            
                boolean e1 = coluna > 0;
                boolean e2 = linha > 0;
                boolean e3 = e1 && e2;
                
                int value = distances[linha][coluna];
                value += (e1 || e2 || e3) 
                            ? Util.minInteger(
                                    (e1) ? distances[linha][coluna-1] : Integer.MAX_VALUE, 
                                    (e2) ? distances[linha-1][coluna] : Integer.MAX_VALUE, 
                                    (e3) ? distances[linha-1][coluna-1] : Integer.MAX_VALUE)
                            : 0;
                path[linha][coluna] = value;
            }
        }
        if (this.DEBUG) printMatrix("path", path, target.size(), sequence.size(), target, sequence);
        
        int steps = calculateSteps(path, target.size()-1, sequence.size()-1, 1);
        int lastValue = path[target.size()-1][sequence.size()-1];
        this.cost = (double) lastValue / steps;
        
        if (this.DEBUG) System.out.println(String.format("steps: %d, cost: %.4f", steps, cost));
        */ 
    }
    
    private int calculateSteps(int[][] path, 
                               int linha, int coluna,
                               int steps) {
        
        if (linha == 0 && coluna == 0) {
            return steps;
        }
        
        boolean e1 = linha-1 >= 0;
        boolean e2 = coluna-1 >= 0;
        boolean e3 = e1 && e2;
        
        int v1 = e1 ? path[linha-1][coluna] : Integer.MAX_VALUE;
        int v2 = e2 ? path[linha][coluna-1] : Integer.MAX_VALUE;
        int v3 = e3 ? path[linha-1][coluna-1] : Integer.MAX_VALUE;
        
        int min = Util.minInteger(v1, v2, v3);
        
        if (v3 == min) {
            return calculateSteps(path, linha-1, coluna-1, steps+1);
        } else
        if (v2 == min) {
            return calculateSteps(path, linha, coluna-1, steps+1);
        }
        return calculateSteps(path, linha-1, coluna, steps+1);
    }
    
    /*
    public static void main(String[] args) throws Exception  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("target", Util.createArray(1, 2, 3, 4, 6, 7, 8));
        params.put("sequence", Util.createArray(3, 5, 6));
        
        DTW dtw = new DTW();
        dtw.execute(params);
    }
    */
}
