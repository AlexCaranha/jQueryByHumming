package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.system.Processing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        
        public List<Gravacao> getGravacao(String title) {
            List<Gravacao> gravacaoTitle = new ArrayList<Gravacao>();
            
            for(Gravacao gravacao : this.gravacoes) {
                if (gravacao.nome.equalsIgnoreCase(title)) {
                    gravacaoTitle.add(gravacao);
                }
            }
            
            return gravacaoTitle;
        }
    }
    
    public EvaluationSystem() {
        
    }
    
    public static List<Usuario> GetFromXML(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(path);
        List<Usuario> usuarios = (List<Usuario>)Convert.deserialize(inputStream);
        return usuarios;
    }
    
    public static List<String> getTitles() {
        List<String> result = new ArrayList<String>();
        
        result.add("Águas de março");
        result.add("Carinhoso");
        result.add("Asa branca");
        result.add("Chega de saudade");
        result.add("Detalhes");
        result.add("Alegria, alegria");
        result.add("Aquarela do Brasil");
        result.add("Trem das onze");
        result.add("Quero que vá tudo pro inferno");
        result.add("Preta pretinha");
        result.add("Inútil");
        result.add("Eu sei que vou te amar");
        result.add("País tropical");
        result.add("Garota de ipanema");
        result.add("Pra não dizer que não falei das flores");
        result.add("Travessia");
        result.add("Eu quero é botar meu bloco na rua");
        result.add("Manhã de carnaval");
        result.add("Ponteio");
        result.add("Me chama");
        result.add("Conversa de botequim");
        result.add("Luar do sertão");
        result.add("Alagados");
        result.add("As curvas da estrada de Santos");
        result.add("A banda");
        result.add("Comida");
        result.add("Ronda");
        result.add("Gita");
        result.add("Sentado à beira do caminho");
        result.add("Foi um rio que passou em minha vida");
        result.add("Que país é este?");
        result.add("Ideologia");
        result.add("Rosa");
        result.add("O barquinho");
        result.add("Meu mundo e nada mais");
        result.add("A flor e o espinho");
        result.add("Felicidade");
        result.add("Casa no campo");
        result.add("Disritmia");
        result.add("Você não soube me amar");
        result.add("A noite de meu bem");
        result.add("Anna Júlia");
        result.add("Parabéns a você");
        result.add("Ciranda cirandinha");
        result.add("Escravos de jó");
        result.add("Hino nacional");
        result.add("Hino da bandeira");
        result.add("Ilariê");
        result.add("Fim de Ano");
        result.add("Mamãe eu quero");
        result.add("Coelhinho de olhos vermelhos");
        result.add("Chegou a hora da foqueira");
        
        return result;
    }
    
    public static String getTitle(String label) {
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
    
    // PAREI AQUI.
    public static String getCode(String label) {
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
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String dirDB = "/home/alexcaranha/Documentos/Mestrado/DataBase/DBSolfejos";
        
        String prefixo = "/home/alexcaranha/Documentos/Mestrado/jQueryByHumming/";
        String path1 = prefixo + "evaluationUsuarios-LevenshteinDistance.xml";
        String path2 = prefixo + "evaluationUsuarios-DTW_RelativePitch.xml";
        String path3 = prefixo + "evaluationUsuarios-DTW_AbsolutePitch.xml";
        
        List<Usuario> usuarios1 = GetFromXML(path1);
        List<Usuario> usuarios2 = GetFromXML(path2);
        List<Usuario> usuarios3 = GetFromXML(path3);
        
        List<String> titles = getTitles();        
        List<String> tipos = Util.createList("Tipo1","Tipo2","Tipo3");
                
        FileWriter outFile = new FileWriter(Util.getDirExecution("DadosEstatisticosDB.txt"));
        PrintWriter out = new PrintWriter(outFile);
        
        Map<Integer, List<Usuario>> usuarios = new HashMap<Integer, List<Usuario>>();
        Map<String, Integer> musicas = new HashMap<String, Integer>();
        
        usuarios.put(1, usuarios1);  // LevenshteinDistance
        usuarios.put(2, usuarios2);  // DTW_RelativePitch
        usuarios.put(3, usuarios3);  // DTW_AbsolutePitch
        //---------------------------------------------------------------------------------------------
        System.out.println("Gravações por música: Codigo;Titulo;Tipo1;Tipo2;Tipo3");
        out.println("Gravações por música: Codigo;Titulo;Tipo1;Tipo2;Tipo3");

        File raiz = new File(dirDB);
        for(String title : titles) {
            int qtdTipo1 = 0;
            int qtdTipo2 = 0;
            int qtdTipo3 = 0;

            String codigoMusica = "";

            for(File fileNivel1: raiz.listFiles()) {  
                if(fileNivel1.isDirectory() && fileNivel1.getName().contains("Usuario_")) {
                    //String usuarioNome = fileNivel1.getName().trim();

                    for(File fileNivel2: fileNivel1.listFiles()) {
                        String tipo = fileNivel2.getName();
                        if ("Tipo1|Tipo2|Tipo3".contains(tipo)) {
                            for(File fileNivel3: fileNivel2.listFiles()) {  
                                if (fileNivel3.getName().contains(".wav")) {
                                    String musicaEsperada = getTitle(fileNivel3.getName().trim());
                                    if (musicaEsperada.equalsIgnoreCase(title)) {
                                        codigoMusica = fileNivel3.getName().trim();

                                        if (tipo.equalsIgnoreCase("Tipo1")) qtdTipo1 += 1;
                                        if (tipo.equalsIgnoreCase("Tipo2")) qtdTipo2 += 1;
                                        if (tipo.equalsIgnoreCase("Tipo3")) qtdTipo3 += 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            codigoMusica = codigoMusica.replaceAll("musica_", "").replaceAll("_g1a", "").replaceAll("_g1b", "").replaceAll("_g2", "").replaceAll("_s", "").replaceAll(".wav", "");
            System.out.println(String.format("%s;%s;%d;%d;%d",codigoMusica, title, qtdTipo1, qtdTipo2, qtdTipo3));            
            out.println(String.format("%s;%s;%d;%d;%d",codigoMusica, title, qtdTipo1, qtdTipo2, qtdTipo3));            
        }
        
        //----------------------------------------------------------------------
        System.out.println("\nGravações saturadas por música: Codigo;Titulo;Tipo1;Tipo2;Tipo3");
        out.println("\nGravações saturadas por música: Codigo;Titulo;Tipo1;Tipo2;Tipo3");

        File diretorio = new File(dirDB);
        for(String title : titles) {
            int qtdTipo1 = 0;
            int qtdTipo2 = 0;
            int qtdTipo3 = 0;

            String codigoMusica = "";

            for(File fileNivel1: diretorio.listFiles()) {  
                if(fileNivel1.isDirectory() && fileNivel1.getName().contains("Usuario_")) {
                    //String usuarioNome = fileNivel1.getName().trim();

                    for(File fileNivel2: fileNivel1.listFiles()) {
                        String tipo = fileNivel2.getName();
                        if ("Tipo1|Tipo2|Tipo3".contains(tipo)) {
                            for(File fileNivel3: fileNivel2.listFiles()) {
                                if (fileNivel3.getName().contains(".wav") && fileNivel3.getName().contains("_s.wav")) {
                                    String musicaEsperada = getTitle(fileNivel3.getName().trim());
                                    if (musicaEsperada.equalsIgnoreCase(title)) {
                                        codigoMusica = fileNivel3.getName().trim();

                                        if (tipo.equalsIgnoreCase("Tipo1")) qtdTipo1 += 1;
                                        if (tipo.equalsIgnoreCase("Tipo2")) qtdTipo2 += 1;
                                        if (tipo.equalsIgnoreCase("Tipo3")) qtdTipo3 += 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            codigoMusica = codigoMusica.replaceAll("musica_", "").replaceAll("_g1a", "").replaceAll("_g1b", "").replaceAll("_g2", "").replaceAll("_s", "").replaceAll(".wav", "");
            System.out.println(String.format("%s;%s;%d;%d;%d",codigoMusica, title, qtdTipo1, qtdTipo2, qtdTipo3));            
            out.println(String.format("%s;%s;%d;%d;%d",codigoMusica, title, qtdTipo1, qtdTipo2, qtdTipo3));            
        }
        //---------------------------------------------------------------------------------------------
        // Tipo -> Algoritmo -> MRR (usuarios).
        System.out.println("\nMRR por tipo de gravação e algoritmo: Tipo; MRR Algoritmo1; MRR Algoritmo2; MRR Algoritmo3");
        out.println("\nMRR por tipo de gravação e algoritmo: Tipo; MRR Algoritmo1; MRR Algoritmo2; MRR Algoritmo3");
                
        for(String tipo : tipos) {
            int qtdAlgoritmo1 = 0;
            int qtdAlgoritmo2 = 0;
            int qtdAlgoritmo3 = 0;
            
            double sumAlgoritmo1 = 0.0;
            double sumAlgoritmo2 = 0.0;
            double sumAlgoritmo3 = 0.0;
                        
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                Integer       algoritmo = item.getKey();
                List<Usuario> lista     = item.getValue();
                                
                for(Usuario usuario : lista) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {                        
                        if (gravacao.getTipo().equalsIgnoreCase(tipo)) {
                            switch (algoritmo) {
                                case 1:
                                    qtdAlgoritmo1 += 1;
                                    sumAlgoritmo1 += 1 / gravacao.getPosicao();
                                    break;
                                case 2:
                                    qtdAlgoritmo2 += 1;
                                    sumAlgoritmo2 += 1 / gravacao.getPosicao();
                                    break;
                                case 3:
                                    qtdAlgoritmo3 += 1;
                                    sumAlgoritmo3 += 1 / gravacao.getPosicao();
                                    break;
                            }
                        }
                    }
                }
            }
            
            double MRR_A1 = qtdAlgoritmo1 == 0 ? 0.0 : sumAlgoritmo1 / qtdAlgoritmo1;
            double MRR_A2 = qtdAlgoritmo2 == 0 ? 0.0 : sumAlgoritmo2 / qtdAlgoritmo2;
            double MRR_A3 = qtdAlgoritmo3 == 0 ? 0.0 : sumAlgoritmo3 / qtdAlgoritmo3;

            System.out.println(String.format("%s;%.6fs;%.6f;%.6f", tipo, MRR_A1, MRR_A2, MRR_A3));
        }
        //---------------------------------------------------------------------------------------------        
        out.close();
        //---------------------------------------------------------------------------------------------        
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
                    if ("Tipo1|Tipo2|Tipo3".contains(tipo)) {
                        for(File fileNivel3: fileNivel2.listFiles()) {  
                            if (fileNivel3.getName().contains(".wav")) {
                                musicaEsperada = getTitle(fileNivel3.getName().trim());
                                
                                System.out.print(String.format("Arquivo: %s, Música: %s", fileNivel3.getName().trim(), musicaEsperada));
                                out.print(String.format("Arquivo: %s, Música: %s", fileNivel3.getName().trim(), musicaEsperada));
                                //------------------------------------------------------------------------------
                                InputStream inputStream = new FileInputStream(fileNivel3);
                                WavSignal signal = new WavSignal();
                                signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", inputStream)));

                                Processing processing = new Processing(signal);
                                processing.execute();

                                List<String> getResult = processing.getListTitleSongsResultant();
                                //List<Point<Double, Database_Detail_Model>> getResult = processing.getResult();
                                int position = getMRR(getResult, musicaEsperada);

                                usuario.addGravacao(tipo, musicaEsperada, position);
                                //------------------------------------------------------------------------------
                                System.out.println(String.format(", Posicao no ranque: %d", position));
                                out.println(String.format(", Posicao no ranque: %d", position));
                            }
                        }
                    }else {
                        out.println("Tipo Desconhecido.");
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
