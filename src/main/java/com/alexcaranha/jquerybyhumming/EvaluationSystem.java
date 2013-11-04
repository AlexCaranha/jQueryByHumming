package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Point;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.system.Processing;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.common.io.stream.StreamOutput;

/**
 *
 * @author alexcaranha
 */
public class EvaluationSystem {
    
    public class Gravacao {
        private String tipo;
        private String nome;
        private double posicao;
        
        public Gravacao(String tipo, String nome, double posicao) {
            this.tipo = tipo;
            this.nome = nome;
            this.posicao = posicao;
        }
        
        public String getTipo() {
            return this.tipo;
        }
        
        public String getNome() {
            return this.nome;
        }
        
        public double getPosicao() {
            return this.posicao;
        }
    }
    
    public class Usuario {
        private String usuario;
        private List<Gravacao> gravacoes;
        
        public Usuario(String usuarioNome) {
            this.usuario = usuarioNome;
            this.gravacoes = new ArrayList<Gravacao>();
        }
        
        public void clearGravacoes() {
            this.gravacoes.clear();
        }
        
        public void addGravacao(String tipo, String nome, double value) {
            this.gravacoes.add(new Gravacao(tipo, nome, value));
        }
        
        public String getUsuario() {
            return this.usuario;
        }
        
        public List<Gravacao> getGravacoes() {
            return this.gravacoes;
        }
    }
    
    public EvaluationSystem() {
        
    }
    
    public String getTitle(String label) {
        if (label.contains("musica_002_")) return "Águas de março";
        if (label.contains("musica_003_")) return "Carinhoso";
        if (label.contains("musica_004_")) return "Asa branca";
        if (label.contains("musica_006_")) return "Chega de saudade";
        if (label.contains("musica_008_")) return "Detalhes";
        if (label.contains("musica_010_")) return "Alegria, alegria";
        if (label.contains("musica_012_")) return "Aquarela do Brasil";
        if (label.contains("musica_015_")) return "Trem das onze";
        if (label.contains("musica_019_")) return "Quero que vá tudo pro inferno";
        if (label.contains("musica_020_")) return "Preta pretinha";
        if (label.contains("musica_023_")) return "Inútil";
        if (label.contains("musica_024_")) return "Eu sei que vou te amar";
        if (label.contains("musica_025_")) return "País tropical";
        if (label.contains("musica_027_")) return "Garota de ipanema";
        if (label.contains("musica_028_")) return "Pra não dizer que não falei das flores";
        if (label.contains("musica_031_")) return "Travessia";
        if (label.contains("musica_038_")) return "Eu quero é botar meu bloco na rua";
        if (label.contains("musica_041_")) return "Manhã de carnaval";
        if (label.contains("musica_046_")) return "Ponteio";
        if (label.contains("musica_047_")) return "Me chama";
        if (label.contains("musica_057_")) return "Conversa de botequim";
        if (label.contains("musica_062_")) return "Luar do sertão";
        if (label.contains("musica_063_")) return "Alagados";
        if (label.contains("musica_064_")) return "As curvas da estrada de Santos";
        if (label.contains("musica_067_")) return "A banda";
        if (label.contains("musica_068_")) return "Comida";
        if (label.contains("musica_070_")) return "Ronda";
        if (label.contains("musica_072_")) return "Gita";
        if (label.contains("musica_074_")) return "Sentado à beira do caminho";
        if (label.contains("musica_075_")) return "Foi um rio que passou em minha vida";
        if (label.contains("musica_081_")) return "Que país é este?";
        if (label.contains("musica_083_")) return "Ideologia";
        if (label.contains("musica_084_")) return "Rosa";
        if (label.contains("musica_085_")) return "O barquinho";
        if (label.contains("musica_087_")) return "Meu mundo e nada mais";
        if (label.contains("musica_089_")) return "A flor e o espinho";
        if (label.contains("musica_091_")) return "Felicidade";
        if (label.contains("musica_093_")) return "Casa no campo";
        if (label.contains("musica_096_")) return "Disritmia";
        if (label.contains("musica_097_")) return "Você não soube me amar";
        if (label.contains("musica_098_")) return "A noite de meu bem";
        if (label.contains("musica_100_")) return "Anna Júlia";
        if (label.contains("musica_101_")) return "Parabéns a você";
        if (label.contains("musica_102_")) return "Ciranda cirandinha";
        if (label.contains("musica_103_")) return "Escravos de jó";
        if (label.contains("musica_104_")) return "Hino nacional";
        if (label.contains("musica_105_")) return "Hino da bandeira";
        if (label.contains("musica_107_")) return "Ilariê";
        if (label.contains("musica_108_")) return "Fim de Ano";
        if (label.contains("musica_109_")) return "Mamãe eu quero";
        if (label.contains("musica_111_")) return "Coelhinho de olhos vermelhos";
        if (label.contains("musica_112_")) return "Chegou a hora da foqueira";
        
        return null;
    }
    
    public void execute(String directory) throws FileNotFoundException, Exception {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        String  musicaEsperada;
        double  MRR;
        
        FileWriter outFile = new FileWriter(Util.getDirExecution("evaluation.txt"));
        PrintWriter out = new PrintWriter(outFile);
        
        File raiz = new File(directory);
        for(File fileNivel1: raiz.listFiles()) {  
            if(fileNivel1.isDirectory() && fileNivel1.getName().contains("Usuario_")) {
                String usuarioNome = fileNivel1.getName().trim();
                Usuario usuario = new Usuario(usuarioNome);
                
                System.out.println("\nDiretório: " + usuarioNome);
                out.println("\nDiretório: " + usuarioNome);

                for(File fileNivel2: fileNivel1.listFiles()) {
                    String tipo = fileNivel2.getName();
                    if ("Tipo1|Tipo 2|Tipo3".contains(tipo)) {
                        for(File fileNivel3: fileNivel2.listFiles()) {  
                            if (fileNivel3.getName().contains(".wav")) {
                                musicaEsperada = getTitle(fileNivel3.getName().trim());
                                
                                /*
                                System.out.println(String.format("Arquivo: %s - Inicio", fileNivel3.getName().trim()));
                                if (fileNivel3.getName().contains("musica_027_g2")) {
                                    musicaEsperada = musicaEsperada;
                                }
                                */

                                InputStream inputStream = new FileInputStream(fileNivel3);
                                WavSignal signal = new WavSignal();
                                signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", inputStream)));

                                Processing processing = new Processing(signal);
                                processing.execute();

                                List<String> getResult = processing.getListTitleSongsResultant();
                                //List<Point<Double, Database_Detail_Model>> getResult = processing.getResult();
                                int position = getMRR(getResult, musicaEsperada);

                                usuario.addGravacao(tipo, musicaEsperada, position);

                                System.out.println(String.format("Arquivo: %s, Posicao no ranque: %d", fileNivel3.getName().trim(), position));
                                out.println(String.format("Arquivo: %s, Posicao no ranque: %d", fileNivel3.getName().trim(), position));
                            }
                        }
                    }
                }
                
                usuarios.add(usuario);
            }
        }
        
        out.close();
        
        //OutputStream outputXML = new FileOutputStream("evaluationUsuarios-LevenshteinDistance.xml");
        //OutputStream outputXML = new FileOutputStream("evaluationUsuarios-DTW_AbsolutePitch.xml");
        OutputStream outputXML = new FileOutputStream("evaluationUsuarios-DTW_RelativePitch.xml");
        
        // mm:evaluation
        Convert.serialize(usuarios, outputXML);
        outputXML.close();
    }
    
    /*
    private int getMRR(List<Point<Double, Database_Detail_Model>> listResult, String musicaEsperada) {
        for (int i = 0; i < listResult.size(); i += 1) {
            Point<Double, Database_Detail_Model> item = listResult.get(i);
            String titulo = item.getY().getTitle();
            
            if (titulo.equalsIgnoreCase(musicaEsperada)) {
                return (i + 1);
            }
        }
        return 0;
    }
    */
    
    private int getMRR(List<String> listSongs, String musicaEsperada) {
        for (int i = 0; i < listSongs.size(); i += 1) {
            if (listSongs.get(i).equalsIgnoreCase(musicaEsperada)) {
                return (i + 1);
            }
        }
        return 0;
    }
}
