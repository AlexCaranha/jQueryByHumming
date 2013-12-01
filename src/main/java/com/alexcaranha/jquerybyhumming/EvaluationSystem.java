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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

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
    
    public static String[] getTitles() {
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
        
        return result.toArray(new String[result.size()]);
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
        
    public static String getCode(String titulo) {
        if (titulo.equalsIgnoreCase("Águas de março")) return "002";
        if (titulo.equalsIgnoreCase("Carinhoso")) return "003";
        if (titulo.equalsIgnoreCase("Asa branca")) return "004";
        if (titulo.equalsIgnoreCase("Chega de saudade")) return "006";
        if (titulo.equalsIgnoreCase("Detalhes")) return "008";
        if (titulo.equalsIgnoreCase("Alegria, alegria")) return "010";
        if (titulo.equalsIgnoreCase("Aquarela do Brasil")) return "012";
        if (titulo.equalsIgnoreCase("Trem das onze")) return "015";
        if (titulo.equalsIgnoreCase("Quero que vá tudo pro inferno")) return "019";
        if (titulo.equalsIgnoreCase("Preta pretinha")) return "020";
        if (titulo.equalsIgnoreCase("Inútil")) return "023";
        if (titulo.equalsIgnoreCase("Eu sei que vou te amar")) return "024";
        if (titulo.equalsIgnoreCase("País tropical")) return "025";
        if (titulo.equalsIgnoreCase("Garota de ipanema")) return "027";
        if (titulo.equalsIgnoreCase("Pra não dizer que não falei das flores")) return "028";
        if (titulo.equalsIgnoreCase("Travessia")) return "031";
        if (titulo.equalsIgnoreCase("Eu quero é botar meu bloco na rua")) return "038";
        if (titulo.equalsIgnoreCase("Manhã de carnaval")) return "041";
        if (titulo.equalsIgnoreCase("Ponteio")) return "046";
        if (titulo.equalsIgnoreCase("Me chama")) return "047";
        if (titulo.equalsIgnoreCase("Conversa de botequim")) return "057";
        if (titulo.equalsIgnoreCase("Luar do sertão")) return "062";
        if (titulo.equalsIgnoreCase("Alagados")) return "063";
        if (titulo.equalsIgnoreCase("As curvas da estrada de Santos")) return "064";
        if (titulo.equalsIgnoreCase("A banda")) return "067";
        if (titulo.equalsIgnoreCase("Comida")) return "068";
        if (titulo.equalsIgnoreCase("Ronda")) return "070";
        if (titulo.equalsIgnoreCase("Gita")) return "072";
        if (titulo.equalsIgnoreCase("Sentado à beira do caminho")) return "074";
        if (titulo.equalsIgnoreCase("Foi um rio que passou em minha vida")) return "075";
        if (titulo.equalsIgnoreCase("Que país é este?")) return "081";
        if (titulo.equalsIgnoreCase("Ideologia")) return "083";
        if (titulo.equalsIgnoreCase("Rosa")) return "084";
        if (titulo.equalsIgnoreCase("O barquinho")) return "085";
        if (titulo.equalsIgnoreCase("Meu mundo e nada mais")) return "087";
        if (titulo.equalsIgnoreCase("A flor e o espinho")) return "089";
        if (titulo.equalsIgnoreCase("Felicidade")) return "091";
        if (titulo.equalsIgnoreCase("Casa no campo")) return "093";
        if (titulo.equalsIgnoreCase("Disritmia")) return "096";
        if (titulo.equalsIgnoreCase("Você não soube me amar")) return "097";
        if (titulo.equalsIgnoreCase("A noite de meu bem")) return "098";
        if (titulo.equalsIgnoreCase("Anna Júlia")) return "100";
        if (titulo.equalsIgnoreCase("Parabéns a você")) return "101";
        if (titulo.equalsIgnoreCase("Ciranda cirandinha")) return "102";
        if (titulo.equalsIgnoreCase("Escravos de jó")) return "103";
        if (titulo.equalsIgnoreCase("Hino nacional")) return "104";
        if (titulo.equalsIgnoreCase("Hino da bandeira")) return "105";
        if (titulo.equalsIgnoreCase("Ilariê")) return "107";
        if (titulo.equalsIgnoreCase("Fim de Ano")) return "108";
        if (titulo.equalsIgnoreCase("Mamãe eu quero")) return "109";
        if (titulo.equalsIgnoreCase("Coelhinho de olhos vermelhos")) return "111";
        if (titulo.equalsIgnoreCase("Chegou a hora da foqueira")) return "112";

        return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //----------------------------------------------------------------------
        String   dirDB = "/home/alexcaranha/Documentos/Mestrado/DataBase/DBSolfejos";
        //String[] tipos = Util.createArray("Tipo1","Tipo2","Tipo3");
        String[] algoritmos = Util.createArray("LevenshteinDistance", "DTW_AbsolutePitch", "DTW_RelativePitch");
        String[] titles = getTitles();
        
        String prefixo = "/home/alexcaranha/Documentos/Mestrado/jQueryByHumming/";
        String path1 = prefixo + "evaluationUsuarios-" + algoritmos[0] + ".xml";
        String path2 = prefixo + "evaluationUsuarios-" + algoritmos[1] + ".xml";
        String path3 = prefixo + "evaluationUsuarios-" + algoritmos[2] + ".xml";        
                
        Map<Integer, List<Usuario>> usuarios = new HashMap<Integer, List<Usuario>>();
        usuarios.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuarios.put(2, GetFromXML(path2));  // DTW_RelativePitch
        usuarios.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        
        Map<Integer, List<Usuario>> usuarios1e3 = new HashMap<Integer, List<Usuario>>();
        usuarios1e3.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuarios1e3.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        
        int iMusica;
        //----------------------------------------------------------------------
        PrintWriter out1 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M1.txt")));
        PrintWriter out2 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M2.txt")));
        PrintWriter out3 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M3.txt")));
        PrintWriter out4 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M4.txt")));
        PrintWriter out5 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M5.txt")));
        PrintWriter out6 = new PrintWriter(new FileWriter(Util.getDirExecution("DadosEstatisticosDB_2_M6.txt")));
        //----------------------------------------------------------------------
        // Medição 1:
        System.out.print("Medição 1: ");
        out1.print("Medição 1:");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            Map<String, int[]> dicionario = new HashMap<String, int[]>();
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                int iAlgoritmo = item.getKey();
                
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            int iTipoGravacao = gravacao.getTipo().equalsIgnoreCase("Tipo1")
                                                    ? 1 : gravacao.getTipo().equalsIgnoreCase("Tipo2")
                                                    ? 2 : gravacao.getTipo().equalsIgnoreCase("Tipo3")
                                                    ? 3 : 0;
                            
                            String key = String.format("%d%d", iTipoGravacao, iAlgoritmo);
                            if (!dicionario.containsKey(key)) {
                                dicionario.put(key, new int[titles.length]);
                            }
                            
                            int posicao = (int)gravacao.getPosicao();
                            dicionario.get(key)[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }
            
            int registros = 0;
            for (int iTipo = 1; iTipo <= 3; iTipo += 1) {
                for (int iAlgoritmo = 1; iAlgoritmo <= 3; iAlgoritmo += 1) {
                    for (Entry<String, int[]> item : dicionario.entrySet()) {
                        String  key = item.getKey();
                        int     iTipoGravacao = Convert.toInteger(key.charAt(0));
                        int     iAlgoritmoGravacao = Convert.toInteger(key.charAt(1));
                        
                        if (iTipoGravacao == iTipo && iAlgoritmoGravacao == iAlgoritmo) {
                            registros += 1;
                            if (1 == registros) out1.println(String.format("\n\t%s", musica));
                            
                            out1.print(String.format("\t\tT:%d;A:%d;[", iTipoGravacao, iAlgoritmo));
                            int[] curva = item.getValue();
                            for (int index = 0; index < curva.length-1; index += 1) {
                                out1.print(String.format("%d;", curva[index]));
                            }
                            out1.println(String.format("%d]", curva[curva.length-1]));
                        }
                    } // end for item
                } // end for iAlgoritmo
            } // end for iTipo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 2:
        System.out.print("Medição 2: ");
        out2.print("Medição 2:");
        for (String musica : titles) {
            Map<String, int[]> dicionario = new HashMap<String, int[]>();
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {                
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            int iTipoGravacao = gravacao.getTipo().equalsIgnoreCase("Tipo1")
                                                    ? 1 : gravacao.getTipo().equalsIgnoreCase("Tipo2")
                                                    ? 2 : gravacao.getTipo().equalsIgnoreCase("Tipo3")
                                                    ? 3 : 0;
                            
                            String key = String.format("%d", iTipoGravacao);
                            if (!dicionario.containsKey(key)) {
                                dicionario.put(key, new int[titles.length]);
                            }
                            
                            int posicao = (int)gravacao.getPosicao();
                            dicionario.get(key)[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }
            
            int registros = 0;
            for (int iTipo = 1; iTipo <= 3; iTipo += 1) {
                for (Entry<String, int[]> item : dicionario.entrySet()) {
                    String  key = item.getKey();
                    int     iTipoGravacao = Convert.toInteger(key.charAt(0));

                    if (iTipoGravacao == iTipo) {
                        registros += 1;
                        if (1 == registros) out2.println(String.format("\n\t%s", musica));

                        out2.print(String.format("\t\tT:%d;[", iTipoGravacao));
                        int[] curva = item.getValue();
                        for (int index = 0; index < curva.length-1; index += 1) {
                            out2.print(String.format("%d;", curva[index]));
                        }
                        out2.println(String.format("%d]", curva[curva.length-1]));
                    }
                } // end for item
            } // end for iTipo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 3:
        System.out.print("Medição 3: ");
        out3.print("Medição 3: ");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            Map<String, int[]> dicionario = new HashMap<String, int[]>();
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                int iAlgoritmo = item.getKey();
                
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            String key = String.format("%d", iAlgoritmo);
                            if (!dicionario.containsKey(key)) {
                                dicionario.put(key, new int[titles.length]);
                            }
                            
                            int posicao = (int)gravacao.getPosicao();
                            dicionario.get(key)[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }
            
            int registros = 0;
            for (int iAlgoritmo = 1; iAlgoritmo <= 3; iAlgoritmo += 1) {
                for (Entry<String, int[]> item : dicionario.entrySet()) {
                    String  key = item.getKey();
                    int     iAlgoritmoGravacao = Convert.toInteger(key.charAt(0));

                    if (iAlgoritmoGravacao == iAlgoritmo) {
                        registros += 1;
                        if (1 == registros) out3.println(String.format("\n\t%s", musica));

                        out3.print(String.format("\t\tA:%d;[", iAlgoritmo));
                        int[] curva = item.getValue();
                        for (int index = 0; index < curva.length-1; index += 1) {
                            out3.print(String.format("%d;", curva[index]));
                        }
                        out3.println(String.format("%d]", curva[curva.length-1]));
                    }
                } // end for item
            } // end for iAlgoritmo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 4:
        System.out.print("Medição 4: ");
        out4.print("Medição 4:");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            int[] curva = new int[titles.length];
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            int posicao = (int)gravacao.getPosicao();
                            curva[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }
            
            out4.println(String.format("\n\t%s", musica));
            out4.print("\t\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out4.print(String.format("%d;", curva[index]));
            }
            out4.println(String.format("%d]", curva[curva.length-1]));
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 5:
        TreeMap<String, Integer> dicionario5 = new TreeMap<String, Integer>();
        System.out.print("Medição 5: ");
        out5.print("Medição 5. (Junção do resultado para todos os algoritmos e todas as gravações.)");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            if (!dicionario5.containsKey(musica)) {
                                dicionario5.put(musica, 0);
                            }
                            dicionario5.put(musica, 1 + dicionario5.get(musica));
                        } // end if.
                    } // end for.
                } // end for.
            }
        } // end for musica
        
        // Get entries and sort them.
        List<Entry<String, Integer>> entriesDic5 = new ArrayList<Entry<String, Integer>>(dicionario5.entrySet());
        Collections.sort(entriesDic5, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        
        for (Entry<String, Integer> entry : entriesDic5) {
            String musica = entry.getKey();
            int[] curva = new int[titles.length];

            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            int posicao = (int)gravacao.getPosicao();
                            curva[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }

            out5.println(String.format("\n\t%s. Total de gravações: %d", musica, entry.getValue()));
            out5.print("\t\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out5.print(String.format("%d;", curva[index]));
            }
            out5.println(String.format("%d]", curva[curva.length-1]));

            System.out.println(String.format("%d", iMusica));
        }
                
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 6:
        TreeMap<String, Integer> dicionario6 = new TreeMap<String, Integer>();
        System.out.print("Medição 6: ");
        out6.print("Medição 6. (Junção do resultado para os algoritmos: 1 e 3, e todas as gravações.)");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            
            for(Entry<Integer, List<Usuario>> item : usuarios1e3.entrySet()) {
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            if (!dicionario6.containsKey(musica)) {
                                dicionario6.put(musica, 0);
                            }
                            dicionario6.put(musica, 1 + dicionario6.get(musica));
                        } // end if.
                    } // end for.
                } // end for.
            }
        } // end for musica
        
        // Get entries and sort them.
        List<Entry<String, Integer>> entriesDic6 = new ArrayList<Entry<String, Integer>>(dicionario6.entrySet());
        Collections.sort(entriesDic6, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        
        for (Entry<String, Integer> entry : entriesDic6) {
            String musica = entry.getKey();
            int[] curva = new int[titles.length];

            for(Entry<Integer, List<Usuario>> item : usuarios1e3.entrySet()) {
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (musica.equalsIgnoreCase(gravacao.getNome())) {
                            int posicao = (int)gravacao.getPosicao();
                            curva[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
            }

            out6.println(String.format("\n\t%s. Total de gravações: %d", musica, entry.getValue()));
            out6.print("\t\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out6.print(String.format("%d;", curva[index]));
            }
            out6.println(String.format("%d]", curva[curva.length-1]));

            System.out.println(String.format("%d", iMusica));
        }
                
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        out1.close();
        out2.close();
        out3.close();
        out4.close();
        out5.close();
        out6.close();
        //----------------------------------------------------------------------
    }
    
    /*
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String dirDB = "/home/alexcaranha/Documentos/Mestrado/DataBase/DBSolfejos";
        
        String prefixo = "/home/alexcaranha/Documentos/Mestrado/jQueryByHumming/";
        String path1 = prefixo + "evaluationUsuarios-LevenshteinDistance.xml";
        String path2 = prefixo + "evaluationUsuarios-DTW_AbsolutePitch.xml";
        String path3 = prefixo + "evaluationUsuarios-DTW_RelativePitch.xml";        
        
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
        System.out.println("Gravações por música: Tipo1; Tipo2; Tipo3; Codigo; Titulo");
        out.println("Gravações por música: Tipo1; Tipo2; Tipo3; Codigo; Titulo");

        File raiz = new File(dirDB);
        for(String title : titles) {
            int qtdTipo1 = 0;
            int qtdTipo2 = 0;
            int qtdTipo3 = 0;

            String codigoMusica = getCode(title);

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
                                        //codigoMusica = fileNivel3.getName().trim();

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

            //codigoMusica = codigoMusica.replaceAll("musica_", "").replaceAll("_g1a", "").replaceAll("_g1b", "").replaceAll("_g2", "").replaceAll("_s", "").replaceAll(".wav", "");
            
            System.out.println(String.format("%d; %d; %d; %s; %s", qtdTipo1, qtdTipo2, qtdTipo3, codigoMusica, title));
            out.println(String.format("%d; %d; %d; %s; %s", qtdTipo1, qtdTipo2, qtdTipo3, codigoMusica, title));            
        }
        //----------------------------------------------------------------------
        System.out.println("\nGravações saturadas por música: Tipo1; Tipo2 Tipo3; Codigo; Titulo");
        out.println("\nGravações saturadas por música: Tipo1; Tipo2 Tipo3; Codigo; Titulo");

        File diretorio = new File(dirDB);
        for(String title : titles) {
            int qtdTipo1 = 0;
            int qtdTipo2 = 0;
            int qtdTipo3 = 0;

            String codigoMusica = getCode(title);

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
                                        //codigoMusica = fileNivel3.getName().trim();

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

            System.out.println(String.format("%d; %d; %d; %s; %s", qtdTipo1, qtdTipo2, qtdTipo3, codigoMusica, title));
            out.println(String.format("%d; %d; %d; %s; %s", qtdTipo1, qtdTipo2, qtdTipo3, codigoMusica, title));
        }
        //---------------------------------------------------------------------------------------------
        // Tipo -> Algoritmo -> MRR (usuarios).
        System.out.println("\nMRR por tipo de gravação e algoritmo: Tipo; MRR Algoritmo1; MRR Algoritmo2; MRR Algoritmo3");
        out.println("\nMRR por tipo de gravação e algoritmo: Tipo; MRR Algoritmo1; MRR Algoritmo2; MRR Algoritmo3");
                
        System.out.println("Obs1.: Tipo1 (Gravação com acompanhamento de piano), Tipo2 (Gravação com acompanhamento de mp3) e Tipo3 (Gravação sem acompanhamento.)");
        out.println("Obs1.: Tipo1 (Gravação com acompanhamento de piano), Tipo2 (Gravação com acompanhamento de mp3) e Tipo3 (Gravação sem acompanhamento.)");
        
        System.out.println("Obs2.: Algoritmo1 (Distância de Levenshtein), Algoritmo2 (DTW com pitch absoluto) e Algoritmo3 (DTW com pitch relativo)");
        out.println("Obs2.: Algoritmo1 (Distância de Levenshtein), Algoritmo2 (DTW com pitch absoluto) e Algoritmo3 (DTW com pitch relativo)");
                
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

            System.out.println(String.format("%s; %.6f; %.6f; %.6f", tipo, MRR_A1, MRR_A2, MRR_A3));
            out.println(String.format("%s; %.6f; %.6f; %.6f", tipo, MRR_A1, MRR_A2, MRR_A3));
        }
        //----------------------------------------------------------------------
        System.out.println("\nMúsica, tipo e MRR de algoritmo: Tipo; Algoritmo1, Algoritmo2, Algoritmo3; Codigo; Titulo");
        out.println("\nMúsica, tipo e MRR de algoritmo: Tipo; Algoritmo1, Algoritmo2, Algoritmo3; Codigo; Titulo");
        
        for(String title : titles) {
            String codigoMusica = getCode(title);
            
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
                        List<Gravacao> gravacoes = usuario.getGravacao(title);
                        
                        if (gravacoes != null)
                        for(Gravacao gravacao : gravacoes) {                        
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

                System.out.println(String.format("%s; %.6f; %.6f; %.6f; %s; %s", tipo, MRR_A1, MRR_A2, MRR_A3, codigoMusica, title));
                out.println(String.format("%s; %.6f; %.6f; %.6f; %s; %s", tipo, MRR_A1, MRR_A2, MRR_A3, codigoMusica, title));
            }
        }
        //----------------------------------------------------------------------
        System.out.println("\nMRR Música por algoritmo: Algoritmo1, Algoritmo2, Algoritmo3; Codigo; Titulo");
        out.println("\nMRR Música por algoritmo: Algoritmo1, Algoritmo2, Algoritmo3; Codigo; Titulo");
        
        for(String title : titles) {
            String codigoMusica = getCode(title);
                        
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
                    List<Gravacao> gravacoes = usuario.getGravacao(title);
                    
                    if (gravacoes != null)
                    for(Gravacao gravacao : gravacoes) {                                                
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

            double MRR_A1 = qtdAlgoritmo1 == 0 ? 0.0 : sumAlgoritmo1 / qtdAlgoritmo1;
            double MRR_A2 = qtdAlgoritmo2 == 0 ? 0.0 : sumAlgoritmo2 / qtdAlgoritmo2;
            double MRR_A3 = qtdAlgoritmo3 == 0 ? 0.0 : sumAlgoritmo3 / qtdAlgoritmo3;

            System.out.println(String.format("%.6f; %.6f; %.6f; %s; %s", MRR_A1, MRR_A2, MRR_A3, codigoMusica, title));
            out.println(String.format("%.6f; %.6f; %.6f; %s; %s", MRR_A1, MRR_A2, MRR_A3, codigoMusica, title));            
        }
        //----------------------------------------------------------------------
        out.close();
        //----------------------------------------------------------------------
    }
    */
    
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
