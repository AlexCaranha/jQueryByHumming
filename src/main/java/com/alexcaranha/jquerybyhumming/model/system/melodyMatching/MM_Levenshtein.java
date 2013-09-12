package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.ParsonsCode;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class MM_Levenshtein extends MM {
    private boolean DEBUG = false;
    private double cost;
    
    @Override
    public String getTitle() {
        return "LEVENSHTEIN DISTANCE";
    }

    @Override
    public String getAlias() {
        return "LD";
    }
    
    public MM_Levenshtein() {
        /*
        ConfigurationTableItem variable;        
        variable = new ConfigurationTableItem<Encoding>("pitchEncoding", pitchEncodingArray, (Encoding)Util.getItemByToString(pitchEncodingArray, pitchEncodingSelected), "Indicates the pitch encoding method.");
        this.variables.put("pitchEncoding", variable);
        */
        this.cost = 0.0;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public void execute(Map<String, Object> params) throws Exception {        
        List<MelodyRepresentationNote> target = (List<MelodyRepresentationNote>) params.get("target");
        List<MelodyRepresentationNote> sequence = (List<MelodyRepresentationNote>) params.get("sequence");
        
        Encoding pitchEncoding = new ParsonsCode();
        
        List<Object> melodyTarget = pitchEncoding.execute(target);
        List<Object> melodySequence = pitchEncoding.execute(sequence);        
        /*
        List<String> melodySequence = Util.createList("B", "A", "N", "A", "N", "A");
        List<String> melodyTarget = Util.createList("B", "N", "A", "N", "A", "S");
        */
        double[][] distances = new double[melodyTarget.size() + 1][melodySequence.size() + 1];
        
        distances[0][0] = 0.0;
        
        for(int linha = 1; linha <= melodyTarget.size(); linha += 1) {
            distances[linha][0] = linha;
        }
        
        for(int coluna = 1; coluna <= melodySequence.size(); coluna += 1) {
            distances[0][coluna] = coluna;
        }
        
        if (DEBUG) System.out.println(String.format("Comprimento de target: %d, Comprimento de sequence: %d", melodyTarget.size(), melodySequence.size()));
        
        for(int linha = 1; linha <= melodyTarget.size(); linha += 1) {
            String car1 = melodyTarget.get(linha-1).toString();
            
            for(int coluna = 1; coluna <= melodySequence.size(); coluna += 1) {
                String car2 = melodySequence.get(coluna-1).toString();
                
                double costLocal = (car1.equalsIgnoreCase(car2) ? 0 : 1);
                double minDistance = Util.minDouble(distances[linha][coluna-1], distances[linha-1][coluna], distances[linha-1][coluna-1]);
                
                distances[linha][coluna] = minDistance + costLocal;
            }
        }
        
        this.cost = distances[melodyTarget.size()][melodySequence.size()];
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
        params.put("sequence", Util.createArray(1, 1, 2, 1, 4, 3));
        params.put("target", Util.createArray(1, 1, 1, 2, 1, 3));
        
        MM mm = new MM_Levenshtein("Parson's Code");
        mm.execute(params);
    }
    */
}
