package com.alexcaranha.jquerybyhumming;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.system.Processing;
import java.awt.Color;
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
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;

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
    
    public static String getTituloByArquivo(String label) {
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
        
    public static String getCodidoByTitulo(String titulo) {
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
    
    public static String getTituloByCodigo(String codigo) {
        if (codigo.equalsIgnoreCase("002")) return ("Águas de março");
        if (codigo.equalsIgnoreCase("003")) return ("Carinhoso");
        if (codigo.equalsIgnoreCase("004")) return ("Asa branca");
        if (codigo.equalsIgnoreCase("006")) return ("Chega de saudade");
        if (codigo.equalsIgnoreCase("008")) return ("Detalhes");
        if (codigo.equalsIgnoreCase("010")) return ("Alegria, alegria");
        if (codigo.equalsIgnoreCase("012")) return ("Aquarela do Brasil");
        if (codigo.equalsIgnoreCase("015")) return ("Trem das onze");
        if (codigo.equalsIgnoreCase("019")) return ("Quero que vá tudo pro inferno");
        if (codigo.equalsIgnoreCase("020")) return ("Preta pretinha");
        if (codigo.equalsIgnoreCase("023")) return ("Inútil");
        if (codigo.equalsIgnoreCase("024")) return ("Eu sei que vou te amar");
        if (codigo.equalsIgnoreCase("025")) return ("País tropical");
        if (codigo.equalsIgnoreCase("027")) return ("Garota de ipanema");
        if (codigo.equalsIgnoreCase("028")) return ("Pra não dizer que não falei das flores");
        if (codigo.equalsIgnoreCase("031")) return ("Travessia");
        if (codigo.equalsIgnoreCase("038")) return ("Eu quero é botar meu bloco na rua");
        if (codigo.equalsIgnoreCase("041")) return ("Manhã de carnaval");
        if (codigo.equalsIgnoreCase("046")) return ("Ponteio");
        if (codigo.equalsIgnoreCase("047")) return ("Me chama");
        if (codigo.equalsIgnoreCase("057")) return ("Conversa de botequim");
        if (codigo.equalsIgnoreCase("062")) return ("Luar do sertão");
        if (codigo.equalsIgnoreCase("063")) return ("Alagados");
        if (codigo.equalsIgnoreCase("064")) return ("As curvas da estrada de Santos");
        if (codigo.equalsIgnoreCase("067")) return ("A banda");
        if (codigo.equalsIgnoreCase("068")) return ("Comida");
        if (codigo.equalsIgnoreCase("070")) return ("Ronda");
        if (codigo.equalsIgnoreCase("072")) return ("Gita");
        if (codigo.equalsIgnoreCase("074")) return ("Sentado à beira do caminho");
        if (codigo.equalsIgnoreCase("075")) return ("Foi um rio que passou em minha vida");
        if (codigo.equalsIgnoreCase("081")) return ("Que país é este?");
        if (codigo.equalsIgnoreCase("083")) return ("Ideologia");
        if (codigo.equalsIgnoreCase("084")) return ("Rosa");
        if (codigo.equalsIgnoreCase("085")) return ("O barquinho");
        if (codigo.equalsIgnoreCase("087")) return ("Meu mundo e nada mais");
        if (codigo.equalsIgnoreCase("089")) return ("A flor e o espinho");
        if (codigo.equalsIgnoreCase("091")) return ("Felicidade");
        if (codigo.equalsIgnoreCase("093")) return ("Casa no campo");
        if (codigo.equalsIgnoreCase("096")) return ("Disritmia");
        if (codigo.equalsIgnoreCase("097")) return ("Você não soube me amar");
        if (codigo.equalsIgnoreCase("098")) return ("A noite de meu bem");
        if (codigo.equalsIgnoreCase("100")) return ("Anna Júlia");
        if (codigo.equalsIgnoreCase("101")) return ("Parabéns a você");
        if (codigo.equalsIgnoreCase("102")) return ("Ciranda cirandinha");
        if (codigo.equalsIgnoreCase("103")) return ("Escravos de jó");
        if (codigo.equalsIgnoreCase("104")) return ("Hino nacional");
        if (codigo.equalsIgnoreCase("105")) return ("Hino da bandeira");
        if (codigo.equalsIgnoreCase("107")) return ("Ilariê");
        if (codigo.equalsIgnoreCase("108")) return ("Fim de Ano");
        if (codigo.equalsIgnoreCase("109")) return ("Mamãe eu quero");
        if (codigo.equalsIgnoreCase("111")) return ("Coelhinho de olhos vermelhos");
        if (codigo.equalsIgnoreCase("112")) return ("Chegou a hora da foqueira");
        
        return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //----------------------------------------------------------------------
        String caption = "";
        String   dirDB = "/home/alexcaranha/Documentos/Mestrado/DataBase/DBSolfejos";
        //String[] tipos = Util.createArray("Tipo1","Tipo2","Tipo3");
        String[] algoritmos = Util.createArray("LevenshteinDistance", "DTW_AbsolutePitch", "DTW_RelativePitch");
        
        String prefixo = "/home/alexcaranha/Documentos/Mestrado/jQueryByHumming/";
        String path1 = prefixo + "evaluationUsuarios-" + algoritmos[0] + ".xml";
        String path2 = prefixo + "evaluationUsuarios-" + algoritmos[1] + ".xml";
        String path3 = prefixo + "evaluationUsuarios-" + algoritmos[2] + ".xml";
                
        Map<Integer, List<Usuario>> usuarios = new HashMap<Integer, List<Usuario>>();
        usuarios.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuarios.put(2, GetFromXML(path2));  // DTW_RelativePitch
        usuarios.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        
        Map<Integer, List<Usuario>> usuariosAlgoritmo_1e3 = new HashMap<Integer, List<Usuario>>();
        usuariosAlgoritmo_1e3.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuariosAlgoritmo_1e3.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        //----------------------------------------------------------------------
        // Tabela A.1 da Dissertação.
        // Título de música >> tipo de gravação >> quantidade
        List<Entry<String, Map<Integer, Integer>>> gravacoesPorMusica_e_Tipo = calculaQtdGravacoes(dirDB, false);        
        caption = "\\caption[Lista de músicas da base de dados com a quantidade de gravações por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento)]{Lista de músicas da base de dados com a quantidade de gravações por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento) ordenada decrescentemente pelo total de gravações.}"; 
        criaTabela_1(gravacoesPorMusica_e_Tipo, "tabela_A1.tex", "tab_A1", caption);
        //----------------------------------------------------------------------
        // Tabela A.2 da Dissertação.
        List<Entry<String, Map<Integer, Integer>>> gravacoesSaturadasPorMusica_e_Tipo = calculaQtdGravacoes(dirDB, true);
        caption = "\\caption[Lista de músicas da base de dados com a quantidade de gravações saturadas por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento)]{Lista de músicas da base de dados com a quantidade de gravações saturadas por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento) ordenada decrescentemente pelo total de gravações.}"; 
        criaTabela_1(gravacoesSaturadasPorMusica_e_Tipo, "tabela_A2.tex", "tab_A2", caption);
        //----------------------------------------------------------------------
        // Tabela A.3 da Dissertação.
        // codMusica >> tipo e algoritmo >> rank.
        Map<String, Map<String, List<Gravacao>>> dadosPorMusica = getDadosPorMusica(usuarios);
        caption = "\\caption[Lista de músicas ordenadas pelo percentual de acerto em cada uma das 10 primeiras posições do \\textit{ranking}.]{Lista de músicas ordenadas pelo percentual de acerto em cada uma das 10 primeiras posições do \\textit{ranking}.}"; 
        List<KeyValue<String, double[]>> musicasMaisEncontradas = criaTabela_2(dadosPorMusica, "tabela_A3.tex", "tab_A3", caption);
        //----------------------------------------------------------------------
        // Tabela A.4 da Dissertação.
        Map<String, Map<String, List<Gravacao>>> dadosPorMusica_Algoritmo_1e3 = getDadosPorMusica(usuariosAlgoritmo_1e3);
        caption = "\\caption[Lista de músicas ordenadas pelo percentual de acerto em cada uma das 10 primeiras posições do \\textit{ranking} excluindo os resultados do Algoritmo 2.]{Lista de músicas ordenadas pelo percentual de acerto em cada uma das 10 primeiras posições do \\textit{ranking} excluindo os resultados do Algoritmo 2.}"; 
        List<KeyValue<String, double[]>> musicasMaisEncontradas_Algoritmo_1e3 = criaTabela_2(dadosPorMusica_Algoritmo_1e3, "tabela_A4.tex", "tab_A4", caption);
        //----------------------------------------------------------------------
        // Figura A.5 da Dissertação.
        criaFigura_5(dadosPorMusica, musicasMaisEncontradas_Algoritmo_1e3);
        //----------------------------------------------------------------------
        // Figura A.6 da Dissertação.
        criaFigura_6(dadosPorMusica, musicasMaisEncontradas_Algoritmo_1e3);
        //----------------------------------------------------------------------
        // Qual tipo de gravação de solfejo favorece 
        criaFigura_7(dadosPorMusica);
        //----------------------------------------------------------------------
        // Qual os valores de MRR para os tipos de gravação?
        caption = "Tabela comparativa dos valores na medida MRR levando em consideração o tipo de gravação e o algoritmo utilizado.";
        criaTabela_3(dadosPorMusica, "tabela_A5.tex", "tab:MRR_AlgoritmoPorTipoGravacao", caption);
        //----------------------------------------------------------------------
        // Quais os valores para os tipos de gravação?
        criaFigura_8(dadosPorMusica, gravacoesPorMusica_e_Tipo, true, "figCurvaROC_Tipos_a");
        criaFigura_8(dadosPorMusica, gravacoesPorMusica_e_Tipo, false, "figCurvaROC_Tipos_b");
        //----------------------------------------------------------------------
        // Levando em consideração o tipo de gravação de solfejo e o algoritmo 
        // de comparação de melodias adotado, qual a probabilidade de acerto 
        // pelo sistema nas dez primeiras posições do ranque?
        caption = "Probabilidade de acerto em porcentagem para tipo de gravação e algoritmo de comparação de melodias considerando as dez primeiras posições do ranque.";
        criaTabela_4(dadosPorMusica, "tabela_A6.tex", "tab:ProbabilidadeDeAcerto_Algoritmo_e_TipoGravacao", caption);
        //----------------------------------------------------------------------
        criaFigura_9(dadosPorMusica, gravacoesPorMusica_e_Tipo);
        //----------------------------------------------------------------------
    }
    
    // Para as dez músicas com maior número de gravações, qual foi a 
    // probabilidade de acerto considerando separadamente os algoritmos de 
    // comparação de melodias e os tipos de gravações em todas as posições do ranque?
    public static void criaFigura_9(Map<String, Map<String, List<Gravacao>>> dadosPorMusica,
                                    // Título de música >> tipo de gravação >> quantidade
                                    List<Entry<String, Map<Integer, Integer>>> gravacoesPorMusica_e_Tipo) throws IOException {
        // codMusica >> tipo e algoritmo >> rank.
        // dadosPorMusica
        boolean debug = true;
        
        int posicoes = getTitles().length;
        int limiteMusicas = 10;
        int limitePosicoes = posicoes;
                
        for (int iMusica = 0; iMusica < limiteMusicas; iMusica += 1) {
            Entry<String, Map<Integer, Integer>> musica = gravacoesPorMusica_e_Tipo.get(iMusica);
            String codMusica = musica.getKey();
            String titulo = getTituloByCodigo(codMusica);

            Map<String, List<Gravacao>> musicaTipo = dadosPorMusica.get(codMusica);
            
            int[][] tipo = new int[3][limitePosicoes];
            int[]   qtdTipo = new int[3];

            int[][] algoritmo = new int[3][limitePosicoes];
            int[]   qtdAlgoritmo = new int[3];

            for(Entry<String, List<Gravacao>> itemGravacao : musicaTipo.entrySet()) {
                String tipo_e_algoritmo = itemGravacao.getKey();
                int iTipoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(0));
                int iAlgoritmoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(1));

                for(Gravacao gravacao : itemGravacao.getValue()) {                    
                    int posicao = (int)gravacao.getPosicao();

                    qtdTipo[iTipoGravacao-1] += 1;
                    qtdAlgoritmo[iAlgoritmoGravacao-1] += 1;
                    
                    if (posicao > limitePosicoes) continue;
                    
                    if (iAlgoritmoGravacao == 1 || iAlgoritmoGravacao == 3)
                    tipo[iTipoGravacao-1][posicao-1] += (posicao <= limitePosicoes) ? 1 : 0;
                    
                    algoritmo[iAlgoritmoGravacao-1][posicao-1] += (posicao <= limitePosicoes) ? 1 : 0;
                }
            }
            
            //------------------------------------------------------------------
            DefaultCategoryDataset dataSetTipo = new DefaultCategoryDataset();
            DefaultCategoryDataset dataSetAlgoritmo = new DefaultCategoryDataset();
            //------------------------------------------------------------------
            for (int iTipoGravacao = 2; iTipoGravacao >= 0; iTipoGravacao -= 1) {                
                String tipoGravacao = String.format("Tipo: %d", iTipoGravacao + 1);
                                
                for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
                    double valor = qtdTipo[iTipoGravacao] == 0 ? 0 : (double)tipo[iTipoGravacao][iPosicao] / qtdTipo[iTipoGravacao];
                    
                    String categoria = String.format("%d", iPosicao + 1);
                    dataSetTipo.addValue(valor, tipoGravacao, categoria);
                }
            }
            
            for (int iAlgoritmo = 2; iAlgoritmo >= 0; iAlgoritmo -= 1) {
                String algoritmoGravacao = String.format("Algoritmo: %d", iAlgoritmo + 1);
                                
                for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
                    double valor = qtdAlgoritmo[iAlgoritmo] == 0 ? 0 : (double)algoritmo[iAlgoritmo][iPosicao] / qtdAlgoritmo[iAlgoritmo];
                    
                    String categoria = String.format("%d", iPosicao + 1);
                    dataSetAlgoritmo.addValue(valor, algoritmoGravacao, categoria);
                }
            }
            //------------------------------------------------------------------
            if (debug) {
                //------------------------------------------------------------------
                Figure.saveBarChart(String.format("[%s] - %s", codMusica, titulo), "posição do ranque", "qtd. normalizada",
                                true,
                                dataSetTipo,
                                Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                                null,
                                null, null,
                                new Figure(1300, 300),
                                Util.getDirExecution(String.format("Musica_%d_%s_tipo.pdf", 1+iMusica, codMusica)),
                                Util.getDirExecution(String.format("Musica_%d_%s_tipo.png", 1+iMusica, codMusica)));
                
                Figure.saveBarChart(String.format("[%s] - %s", codMusica, titulo), "posição do ranque", "qtd. normalizada",
                                true,
                                dataSetAlgoritmo,
                                Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                                null,
                                null, null,
                                new Figure(1300, 300),
                                Util.getDirExecution(String.format("Musica_%d_%s_algoritmo.pdf", 1+iMusica, codMusica)),
                                Util.getDirExecution(String.format("Musica_%d_%s_algoritmo.png", 1+iMusica, codMusica)));
                //------------------------------------------------------------------
            }
        }
    }
    
    public static void criaTabela_4(Map<String, Map<String, List<Gravacao>>> dadosPorMusica, 
                                    String fileName, 
                                    String label,
                                    String caption) throws IOException {

        //int posicoes = getTitles().length;
        int limitePosicoes = 10;
        
        double[][][] dados = new double[3][3][1 + limitePosicoes]; // [iAlgoritmo][iTipoGravacao][0-qtd posição, 1-total]

        for(Entry<String, Map<String, List<Gravacao>>> musica : dadosPorMusica.entrySet()) {
            Map<String, List<Gravacao>> musicaTipo = musica.getValue();
            
            for(Entry<String, List<Gravacao>> itemGravacao : musicaTipo.entrySet()) {
                String      tipo_e_algoritmo    = itemGravacao.getKey();
                int         iTipoGravacao       = Convert.toInteger(tipo_e_algoritmo.charAt(0));
                int         iAlgoritmoGravacao  = Convert.toInteger(tipo_e_algoritmo.charAt(1));
                
                for(Gravacao gravacao : itemGravacao.getValue()) {
                    int     posicao             = (int)gravacao.getPosicao();
                    
                    dados[iAlgoritmoGravacao-1][iTipoGravacao-1][limitePosicoes] += 1;
                    if (posicao > limitePosicoes) continue;
                    
                    dados[iAlgoritmoGravacao-1][iTipoGravacao-1][posicao-1] += 1;
                }
            }
        }

        for (int iAlgoritmo = 0; iAlgoritmo < 3; iAlgoritmo +=1) {
            for (int iTipo = 0; iTipo < 3; iTipo +=1) {
                
                for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
                    double numerador = dados[iAlgoritmo][iTipo][iPosicao];
                    double denominador = dados[iAlgoritmo][iTipo][limitePosicoes];
                    
                    dados[iAlgoritmo][iTipo][iPosicao] = (denominador == 0) 
                                                            ? 0.0
                                                            : 100 * (double) numerador / denominador;
                }
            }
        }

        //----------------------------------------------------------------------
        PrintWriter out = new PrintWriter(new FileWriter(Util.getDirExecution(fileName)));
        //----------------------------------------------------------------------
        out.println("\\begin{table}[H]");
        out.println(String.format("\t\\caption{%s}", caption));
        out.println(String.format("\t\\label{%s}", label));
        out.println();
        out.println("\t\\begin{center}");
        out.print("\t\t\\begin{tabular}{|c|c|");
        for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
            out.print("c|");
        }
        out.println("} \\hline");
        out.println("\t\t\t\\textbf{Tipo} & ");
        out.println("\t\t\t\\textbf{Algoritmo} & ");
        
        for (int iPosicao = 1; iPosicao < limitePosicoes; iPosicao += 1) {
            out.println(String.format("\t\t\t\\textbf{%d\\textordfeminine} & ", iPosicao));
        }
        out.println(String.format("\t\t\t\\textbf{%d\\textordfeminine} \\\\ \\hline \\hline ", limitePosicoes));
        out.println();

        for (int iTipo = 0; iTipo < 3; iTipo +=1) {
            out.println(String.format("\t\t\t\\multirow{3}{*}{%d}", 1+iTipo));
            
            for (int iAlgoritmo = 0; iAlgoritmo < 3; iAlgoritmo +=1) {
                out.print(String.format("\t\t\t\t & %d", 1+iAlgoritmo));
                
                for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
                    out.print(String.format(" & %.2f", dados[iAlgoritmo][iTipo][iPosicao]));
                }
                
                if (iAlgoritmo + 1 == 3) {
                    out.println(" \\\\ \\hline \\hline");
                } else {
                    out.println(" \\\\ \\cline{2-12}");
                }
            }
        }
        
        out.println();
        out.println("\t\t\\end{tabular}");
        out.println("\t\\end{center}");
        out.println("\\end{table}");
        out.println();
        //----------------------------------------------------------------------
        out.close();
    }

    public static void criaFigura_8(Map<String, Map<String, List<Gravacao>>> dadosPorMusica,
                                    // Título de música >> tipo de gravação >> quantidade
                                    List<Entry<String, Map<Integer, Integer>>> gravacoesPorMusica_e_Tipo,
                                    boolean todosOsAlgoritmos,
                                    String fileName) throws IOException {
        // codMusica >> tipo e algoritmo >> rank.
        // dadosPorMusica
        boolean debug = true;
        
        int posicoes = getTitles().length;
        int limiteMusicas = 10;
        int limitePosicoes = posicoes;
        
        int[][] tipo = new int[3][limitePosicoes];
        int[] qtdTipo = new int[3];
        
        for (int iMusica = 0; iMusica < limiteMusicas; iMusica += 1) {
            Entry<String, Map<Integer, Integer>> musica = gravacoesPorMusica_e_Tipo.get(iMusica);
            String codMusica = musica.getKey();

            Map<String, List<Gravacao>> musicaTipo = dadosPorMusica.get(codMusica);

            for(Entry<String, List<Gravacao>> itemGravacao : musicaTipo.entrySet()) {
                String tipo_e_algoritmo = itemGravacao.getKey();
                int iTipoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(0));
                int iAlgoritmoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(1));

                if (!todosOsAlgoritmos && iAlgoritmoGravacao == 2) continue;
                for(Gravacao gravacao : itemGravacao.getValue()) {                    
                    int posicao = (int)gravacao.getPosicao();

                    qtdTipo[iTipoGravacao-1] += 1;
                    
                    if (posicao > limitePosicoes) continue;
                    tipo[iTipoGravacao-1][posicao-1] += (posicao <= limitePosicoes) ? 1 : 0;
                }
            }
        }
        
        if (debug) {
            XYSeries[] series = new XYSeries[3];
            
            for (int iTipoGravacao = 0; iTipoGravacao < 3; iTipoGravacao += 1) {
                
                XYSeries serie = new XYSeries(String.format("Tipo %d", iTipoGravacao + 1));
                series[iTipoGravacao] = serie;
                
                double valorAcumulado = 0;
                serie.add(0, valorAcumulado);
                
                for (int iPosicao = 0; iPosicao < limitePosicoes; iPosicao += 1) {
                    double valor = qtdTipo[iTipoGravacao] == 0 ? 0 : (double)tipo[iTipoGravacao][iPosicao] / qtdTipo[iTipoGravacao];
                    valorAcumulado += valor;
                    serie.add(iPosicao + 1, valorAcumulado);
                }
            }
            
            Figure.save("", "posição do ranque", "qtd. normalizada",
                        true,
                        series,
                        Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                        null,
                        null, null,
                        new Figure(650, 300),
                        Util.getDirExecution(String.format("%s.pdf", fileName)),
                        Util.getDirExecution(String.format("%s.png", fileName))
            );
        }
    }

    // Qual os valores de MRR para os tipos de gravação?
    public static void criaTabela_3(Map<String, Map<String, List<Gravacao>>> dadosPorMusica, 
                                    String fileName, 
                                    String label,
                                    String caption) throws IOException {

        double[][][] dados = new double[4][3][3]; // [iTipoGravacao][iAlgoritmo][0-Soma harmônica, 1-Qtd, 2-MRR]

        for(Entry<String, Map<String, List<Gravacao>>> musica : dadosPorMusica.entrySet()) {
            Map<String, List<Gravacao>> musicaTipo = musica.getValue();
            
            for(Entry<String, List<Gravacao>> itemGravacao : musicaTipo.entrySet()) {

                String  tipo_e_algoritmo    = itemGravacao.getKey();
                int     iTipoGravacao       = Convert.toInteger(tipo_e_algoritmo.charAt(0));
                int     iAlgoritmoGravacao  = Convert.toInteger(tipo_e_algoritmo.charAt(1));
                
                for(Gravacao gravacao : itemGravacao.getValue()) {
                    int     posicao         = (int)gravacao.getPosicao();
                    double  valorHarmonico  = (double) 1 / posicao;
                    
                    dados[iTipoGravacao-1][iAlgoritmoGravacao-1][0] += valorHarmonico;
                    dados[iTipoGravacao-1][iAlgoritmoGravacao-1][1] += 1;

                    dados[3][iAlgoritmoGravacao-1][0] += valorHarmonico;
                    dados[3][iAlgoritmoGravacao-1][1] += 1;
                }
            }
        }

        for (int i = 0; i < 4; i +=1) {
            for (int j = 0; j < 3; j +=1) {
                double denominador = dados[i][j][1];
                dados[i][j][2] = denominador == 0 ? 0 : dados[i][j][0] / denominador;
            }
        }

        //----------------------------------------------------------------------
        PrintWriter out = new PrintWriter(new FileWriter(Util.getDirExecution(fileName)));
        //----------------------------------------------------------------------
        out.println("\\begin{table}[H]");
        out.println(String.format("\t\\caption{%s}", caption));
        out.println(String.format("\t\\label{%s}", label));
        out.println();
        out.println("\t\\begin{center}");
        out.println("\t\t\\begin{tabular}{|c|c|c|c|} \\hline");

        out.println("\t\t\t\\textbf{Tipo} & ");
        out.println("\t\t\t\\textbf{MRR Algoritmo 1} & ");
        out.println("\t\t\t\\textbf{MRR Algoritmo 2} & ");
        out.println("\t\t\t\\textbf{MRR Algoritmo 3} \\\\ \\hline \\hline");
        out.println();

        for (int iTipo = 0; iTipo < 4; iTipo +=1) {
            out.print(String.format("\t\t\t %s", iTipo == 3 ? " " : (1 + iTipo)));

            for (int iAlgoritmo = 0; iAlgoritmo < 3; iAlgoritmo +=1) {
                double mrr = dados[iTipo][iAlgoritmo][2];

                out.print(String.format(" & %.4f", mrr));
            }

            out.println(" \\\\ \\hline \\hline");
        }
        
        out.println();
        out.println("\t\t\\end{tabular}");
        out.println("\t\\end{center}");
        out.println("\\end{table}");
        out.println();
        //----------------------------------------------------------------------
        out.close();
    }
    
    public static void criaFigura_7(Map<String, Map<String, List<Gravacao>>> dadosPorMusica) throws IOException {
        // codMusica >> tipo e algoritmo >> rank.
        // dadosPorMusica
        boolean debug = true;
        
        int limite = 53;
        int[][] algoritmo = new int[3][limite];
            
        for(Entry<String, Map<String, List<Gravacao>>> musica : dadosPorMusica.entrySet()) {
            Map<String, List<Gravacao>> musicaTipo = musica.getValue();
            
            for(Entry<String, List<Gravacao>> itemGravacao : musicaTipo.entrySet()) {
                String tipo_e_algoritmo = itemGravacao.getKey();
                int iTipoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(0));
                int iAlgoritmoGravacao = Convert.toInteger(tipo_e_algoritmo.charAt(1));
                
                for(Gravacao gravacao : itemGravacao.getValue()) {
                    /*
                    int iTipoGravacao = gravacao.getTipo().equalsIgnoreCase("Tipo1")
                                                    ? 1 : gravacao.getTipo().equalsIgnoreCase("Tipo2")
                                                    ? 2 : gravacao.getTipo().equalsIgnoreCase("Tipo3")
                                                    ? 3 : 0;
                    */
                    int posicao = (int)gravacao.getPosicao();
                    
                    if (posicao > limite) continue;
                    algoritmo[iAlgoritmoGravacao-1][posicao-1] += (posicao <= limite) ? 1 : 0;
                }
            }
        }
        
        if (debug) {
            XYSeries[] series = new XYSeries[3];
            
            for (int iAlgoritmo = 0; iAlgoritmo < 3; iAlgoritmo += 1) {
                
                double[][] posicoes = new double[limite][2];
                XYSeries serie = new XYSeries(String.format("Algoritmo %s", iAlgoritmo + 1));
                series[iAlgoritmo] = serie;
                
                double valorAcumulado = 0;
                serie.add(0, valorAcumulado);
                
                for (int iPosicao = 0; iPosicao < limite; iPosicao += 1) {
                    valorAcumulado += algoritmo[iAlgoritmo][iPosicao];
                    serie.add(iPosicao + 1, valorAcumulado);
                }
            }
            
            Figure.save("", "posição do ranque", "qtd. acumulada",
                        true,
                        series,
                        Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                        null,
                        null, null,
                        new Figure(650, 300),
                        Util.getDirExecution("figCurvaROC_Algoritmos.pdf"),
                        Util.getDirExecution("figCurvaROC_Algoritmos.png")
            );
        }
    }
    
    public static void criaFigura_6(Map<String, Map<String, List<Gravacao>>> dadosPorMusica,
                                    List<KeyValue<String, double[]>> musicasMaisEncontradas) throws IOException {
        
        boolean debug = false;
        int posicoes = getTitles().length;
        int limite = posicoes;
        List<KeyValue<String, double[]>> result_rank = new ArrayList<KeyValue<String, double[]>>();
                
        if (debug)
        System.out.println("Listagem: ");
        for(int index = 0; index < limite; index += 1) {
            KeyValue<String, double[]> musica = musicasMaisEncontradas.get(index);
            String codMusica = musica.getKey();
            String titulo = getTituloByCodigo(codMusica);
            
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            
            if (debug)
            System.out.println(String.format("Musica: [%s] - %s", codMusica, titulo));
            
            Map<String, List<Gravacao>> dadosMusica = dadosPorMusica.get(codMusica);
            for (int iAlgoritmo = 1; iAlgoritmo <= 3; iAlgoritmo += 1) {
            
                int[]       rankAcumulado = new int[posicoes];
                //double[]    rankAcumulado_Percentual = new double[posicoes];
                int         qtdTotalTipo = 0;
                        
                for (int iTipoGravacao = 1; iTipoGravacao <= 3; iTipoGravacao += 1) {
                    String strTipo_e_Algoritmo = String.format("%d%d", iTipoGravacao, iAlgoritmo);
                    
                    if (!dadosMusica.containsKey(strTipo_e_Algoritmo)) continue;
                    
                    List<Gravacao> gravacoes = dadosMusica.get(strTipo_e_Algoritmo);
                    for (Gravacao gravacao : gravacoes) {
                        int iPosicao = (int)gravacao.getPosicao();
                        rankAcumulado[iPosicao - 1] += 1;
                        qtdTotalTipo += 1;
                    }
                }
                
                for (int iPosicao = 0; iPosicao < posicoes; iPosicao += 1) {
                    double value = (qtdTotalTipo == 0) ? 0.0 : (double) 100 * rankAcumulado[iPosicao] / qtdTotalTipo;
                    //rankAcumulado_Percentual[iPosicao] = value;
                    
                    if (iPosicao == 0 && debug) System.out.print(String.format("\tAlgoritmo:%d", iAlgoritmo));
                    
                    if (debug)
                    System.out.print(String.format(";%.2f", value));
                    
                    String tipoGravacao = String.format("Algoritmo:%d", iAlgoritmo);
                    String categoria = String.format("%dº", iPosicao + 1);
                    
                    dataSet.addValue(value, tipoGravacao, categoria);
                }
                if (debug)
                System.out.println();
            }
            if (debug)
            System.out.println();
            
            if (debug)
            Figure.saveBarChart(String.format("[%s] - %s", codMusica, titulo), "Ranking", "Percentual de acerto.", 
                                true,
                                dataSet,
                                Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                                null,
                                null, null,
                                new Figure(2500, 300),
                                Util.getDirExecution(String.format("FiguraPorAlgoritmo_%d_%s.pdf", 1+ index, codMusica)),
                                Util.getDirExecution(String.format("FiguraPorAlgoritmo_%d_%s.png", 1+ index, codMusica))
            );
        }
    }
    
    // Música >> valores por rank.
    public static void criaFigura_5(Map<String, Map<String, List<Gravacao>>> dadosPorMusica,
                                    List<KeyValue<String, double[]>> musicasMaisEncontradas) throws IOException {
        
        boolean debug = false;
        int posicoes = getTitles().length;
        int limite = 10;//posicoes;
        List<KeyValue<String, double[]>> result_rank = new ArrayList<KeyValue<String, double[]>>();
                
        if (debug)
            System.out.println("Listagem: ");
        
        for(int index = 0; index < limite; index += 1) {
            
            KeyValue<String, double[]> musica = musicasMaisEncontradas.get(index);
            String codMusica = musica.getKey();
            String titulo = getTituloByCodigo(codMusica);
            
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            
            if (debug)
            System.out.println(String.format("Musica: [%s] - %s", codMusica, titulo));
            
            Map<String, List<Gravacao>> dadosMusica = dadosPorMusica.get(codMusica);
            for (int iTipoGravacao = 1; iTipoGravacao <= 3; iTipoGravacao += 1) {
                int[]       rankAcumulado = new int[posicoes];
                //double[]    rankAcumulado_Percentual = new double[posicoes];
                int         qtdTotalTipo = 0;
                        
                for (int iAlgoritmo = 1; iAlgoritmo <= 3; iAlgoritmo += 1) {
                    String strTipo_e_Algoritmo = String.format("%d%d", iTipoGravacao, iAlgoritmo);
                    
                    if (!dadosMusica.containsKey(strTipo_e_Algoritmo)) continue;
                    
                    List<Gravacao> gravacoes = dadosMusica.get(strTipo_e_Algoritmo);
                    for (Gravacao gravacao : gravacoes) {
                        int iPosicao = (int)gravacao.getPosicao();
                        rankAcumulado[iPosicao - 1] += 1;
                        qtdTotalTipo += 1;
                    }
                }
                
                for (int iPosicao = 0; iPosicao < posicoes; iPosicao += 1) {
                    double value = (qtdTotalTipo == 0) ? 0.0 : (double) 100 * rankAcumulado[iPosicao] / qtdTotalTipo;
                    //rankAcumulado_Percentual[iPosicao] = value;
                    
                    if (iPosicao == 0 && debug) System.out.print(String.format("\tTipo:%d", iTipoGravacao));
                    
                    if (debug)
                    System.out.print(String.format(";%.2f", value));
                    
                    String tipoGravacao = String.format("Tipo gravação: %d", iTipoGravacao);
                    String categoria = String.format("%d", iPosicao + 1);
                    
                    dataSet.addValue(value, tipoGravacao, categoria);
                }
                if (debug)
                System.out.println();
            }
            if (debug)
            System.out.println();
            
            if (debug)
            Figure.saveBarChart(String.format("[%s] - %s", codMusica, titulo), "posição do ranque", "%.", 
                                true,
                                dataSet,
                                Util.createArray(Color.BLUE, Color.RED, Color.GREEN),
                                null,
                                null, null,
                                new Figure(2500, 300),
                                Util.getDirExecution(String.format("FiguraPorTipo_%d_%s.pdf", 1+ index, codMusica)),
                                Util.getDirExecution(String.format("FiguraPorTipo_%d_%s.png", 1+ index, codMusica))
            );
        }
    }
    
    // Música >> valores por rank.
    public static List<KeyValue<String, double[]>> criaTabela_2(Map<String, Map<String, List<Gravacao>>> dadosPorMusica,
                                    String fileName, 
                                    String label,
                                    String caption) throws IOException {
        
        int posicoes = getTitles().length;
        List<KeyValue<String, double[]>> result_rank = new ArrayList<KeyValue<String, double[]>>();
        
        for(String codMusica : dadosPorMusica.keySet()) {
            Map<String, List<Gravacao>> mapMusica = dadosPorMusica.get(codMusica);
            int                         qtdGravacoes = 0;
            
            int[]                       rankAcumulado = new int[getTitles().length];
            double[]                    rankAcumuladoPercent = new double[getTitles().length];
            
            for(String tipo_e_algoritmo : mapMusica.keySet()) {
                List<Gravacao> listGravacoes = mapMusica.get(tipo_e_algoritmo);
                
                for(Gravacao gravacao : listGravacoes) {
                    int iPosicao = (int)gravacao.getPosicao();
                    
                    rankAcumulado[iPosicao - 1] += 1;
                    qtdGravacoes += 1;
                }
            }
            
            for (int iPosicao = 0; iPosicao < rankAcumulado.length; iPosicao += 1) {
                double value = (qtdGravacoes == 0) ? 0.0 : (double)100 * rankAcumulado[iPosicao] / qtdGravacoes;
                rankAcumuladoPercent[iPosicao] = value;
            }
            
            result_rank.add(new KeyValue<String, double[]>(codMusica, rankAcumuladoPercent));
        }
        
        for (int i = 0; i < result_rank.size() - 1; i += 1) {
            for (int j = i + 1; j < result_rank.size(); j += 1) {
                KeyValue keyValue_i = result_rank.get(i);
                KeyValue keyValue_j = result_rank.get(j);
                KeyValue keyValue_aux;
                
                double[]    rank_i = (double[])keyValue_i.getValue();
                double[]    rank_j = (double[])keyValue_j.getValue();
                
                int index = 0;
                while(index < posicoes) {
                    double value_i = rank_i[index];
                    double value_j = rank_j[index];

                    if (value_j > value_i) {
                        keyValue_aux = keyValue_i.clone();

                        keyValue_i.setKey(keyValue_j.getKey());
                        keyValue_i.setValue(((double[])keyValue_j.getValue()).clone());

                        keyValue_j.setKey(keyValue_aux.getKey());
                        keyValue_j.setValue(((double[])keyValue_aux.getValue()).clone());

                        break;
                    } else if (value_j == value_i) {
                        index += 1;
                    } else break;
                }
            }
        }
        
        //----------------------------------------------------------------------
        PrintWriter out = new PrintWriter(new FileWriter(Util.getDirExecution(fileName)));
        //----------------------------------------------------------------------        
        out.println("\\begin{longtable}{|c|l|c|c|c|c|c|c|c|c|c|c|}");
        out.println(caption);
        out.println(String.format("\\label{%s} \\\\", label));
        out.println();
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Código}} & ");
        out.println("\\multicolumn{1}{|l|}{\\textbf{Título}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{1\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{2\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{3\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{4\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{5\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{6\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{7\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{8\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{9\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{10\\textordmasculine}} \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endfirsthead");
        out.println("\\multicolumn{12}{c}%");
        out.println("{{\\bfseries \\tablename\\ \\thetable{} -- Continuação da página anterior.}} \\\\");
        out.println();
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Código}} & ");
        out.println("\\multicolumn{1}{|l|}{\\textbf{Título}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{1\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{2\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{3\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{4\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{5\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{6\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{7\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{8\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{9\\textordmasculine}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{10\\textordmasculine}} \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endhead");
        out.println("\\hline \\multicolumn{12}{|r|}{{Continua na próxima página}} \\\\ \\hline");
        out.println("\\endfoot");
        out.println("\\hline\\hline");
        out.println("\\endlastfoot");
        out.println();
        
        for(KeyValue<String, double[]> item : result_rank) {
            String      codMusica = item.getKey();
            String      titulo = getTituloByCodigo(codMusica);
            double[]    rank = item.getValue();
            int limite  = 10; //item.getValue().length;
            
            out.print(String.format("%s & %s", codMusica, titulo));
            for (int index = 0; index < limite; index += 1) {
                out.print(String.format(" & %.2f", rank[index]));
            }
            
            out.println(" \\\\ \\hline");
        }
        
        out.print("\\end{longtable}");
        //----------------------------------------------------------------------
        out.close();
        //----------------------------------------------------------------------
        return result_rank;
        //----------------------------------------------------------------------
    }
    
    public static void criaTabela_1(List<Entry<String, Map<Integer, Integer>>> gravacoesPorMusica_e_Tipo, 
                                    String fileName, 
                                    String label,
                                    String caption) throws IOException {
        //----------------------------------------------------------------------
        PrintWriter out = new PrintWriter(new FileWriter(Util.getDirExecution(fileName)));
        //----------------------------------------------------------------------        
        out.println("\\begin{longtable}{|c|l|c|c|c|c|}");
        out.println(caption);
        out.println(String.format("\\label{%s} \\\\", label));
        out.println();
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Código}} & ");
        out.println("\\multicolumn{1}{|l|}{\\textbf{Título}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Tipo 1}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Tipo 2}} & ");
        out.println("\\multicolumn{1}{c|} {\\textbf{Tipo 3}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Total}} \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endfirsthead");
        out.println("\\multicolumn{6}{c}%");
        out.println("{{\\bfseries \\tablename\\ \\thetable{} -- Continuação da página anterior.}} \\\\");
        out.println();
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Código}} & ");
        out.println("\\multicolumn{1}{|l|}{\\textbf{Título}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Tipo 1}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Tipo 2}} & ");
        out.println("\\multicolumn{1}{c|} {\\textbf{Tipo 3}} & ");
        out.println("\\multicolumn{1}{|c|}{\\textbf{Total}} \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endhead");
        out.println("\\hline \\multicolumn{6}{|r|}{{Continua na próxima página}} \\\\ \\hline");
        out.println("\\endfoot");
        out.println("\\hline\\hline");
        out.println("\\endlastfoot");
        out.println();
        
        for(Entry<String, Map<Integer, Integer>> nivel1 : gravacoesPorMusica_e_Tipo) {
            String  codMusica = nivel1.getKey();
            String  titulo = getTituloByCodigo(codMusica);
            
            int[]   qtdTipo = new int[3];
            int     qtdTotal = 0;
            
            for(int iTipo = 1; iTipo <= 3; iTipo += 1)
            {
                for(Entry<Integer, Integer> nivel2 : nivel1.getValue().entrySet()) {
                    int tipo = nivel2.getKey();
                    int qtd = nivel2.getValue();
                    
                    qtdTipo[iTipo-1] += (tipo == iTipo) ? qtd : 0;
                }
                qtdTotal += qtdTipo[iTipo-1];
            }
            
            out.println(String.format("%s & %s & %d & %d & %d & %d \\\\ \\hline", codMusica, titulo, qtdTipo[0], qtdTipo[1], qtdTipo[2], qtdTotal));
        }
        
        out.print("\\end{longtable}");
        //----------------------------------------------------------------------
        out.close();
        //----------------------------------------------------------------------
    }
    
    // Título de música >> tipo de gravação >> quantidade
    public static List<Entry<String, Map<Integer, Integer>>> calculaQtdGravacoes(String dirDB, boolean somenteSaturadas) {
        File raiz = new File(dirDB);
        String[] titles = getTitles();
        
        Map<String, Map<Integer, Integer>> dicionario = new HashMap<String, Map<Integer, Integer>>();
        
        for(String title : titles) {
            String codMusica = getCodidoByTitulo(title);
            int[]  qtdPorTipo = new int[3];
            
            for(File fileNivel1: raiz.listFiles()) {  
                if(fileNivel1.isDirectory() && fileNivel1.getName().contains("Usuario_")) {
                    //String usuarioNome = fileNivel1.getName().trim();

                    for(File fileNivel2: fileNivel1.listFiles()) {
                        String tipo = fileNivel2.getName();
                        if ("Tipo1|Tipo2|Tipo3".contains(tipo)) {
                            for(File fileNivel3: fileNivel2.listFiles()) {  
                                if (fileNivel3.getName().contains(".wav")) {
                                    if (somenteSaturadas && !fileNivel3.getName().contains("_s.wav")) continue;
                                    
                                    String musicaEsperada = getTituloByArquivo(fileNivel3.getName().trim());
                                    if (!musicaEsperada.equalsIgnoreCase(title)) continue;

                                    int iTipoGravacao = tipo.equalsIgnoreCase("Tipo1") ? 1 : 
                                                            tipo.equalsIgnoreCase("Tipo2") ? 2 : 
                                                                tipo.equalsIgnoreCase("Tipo3") ? 3 : 0;

                                    qtdPorTipo[iTipoGravacao - 1] += 1;
                                }
                            }
                        }
                    }
                }
            }
            
            Map<Integer, Integer> dicQtdPorTipo = new HashMap<Integer, Integer>();
            dicQtdPorTipo.put(1, qtdPorTipo[0]);
            dicQtdPorTipo.put(2, qtdPorTipo[1]);
            dicQtdPorTipo.put(3, qtdPorTipo[2]);
            dicQtdPorTipo.put(4, qtdPorTipo[0] + qtdPorTipo[1] + qtdPorTipo[2]); // Total.
            
            dicionario.put(codMusica, dicQtdPorTipo);
        }
        
        List<Entry<String, Map<Integer, Integer>>> treeSorted = new ArrayList<Entry<String, Map<Integer, Integer>>>(dicionario.entrySet());
        
        Collections.sort(treeSorted, new Comparator<Entry<String, Map<Integer, Integer>>>() {
            public int compare(Entry<String, Map<Integer, Integer>> e1, Entry<String, Map<Integer, Integer>> e2) {
                return e2.getValue().get(4).compareTo(e1.getValue().get(4));
            }
        });
        
        return treeSorted;
    }
    
    public static void criaTabela_2(Map<String, Map<String, List<Usuario>>> dadosPorMusica, String fileName, String label) throws IOException {
        //----------------------------------------------------------------------
        PrintWriter out = new PrintWriter(new FileWriter(Util.getDirExecution(fileName)));
        //----------------------------------------------------------------------        
        out.println("\\begin{longtable}{|c|l|c|c|c|c|}");
        out.println("\\caption[Lista de músicas da base de dados com a quantidade de gravações por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento)]{Lista de músicas da base de dados com a quantidade de gravações por tipo (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento.}"); 
        out.println(String.format("\\label{%s} \\\\", label));
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{ \\multicolumn{1}{|c|}{\\textbf{Código}} & \\multicolumn{1}{l|}{\\textbf{Título} & \\textbf{Tipo 1}} & \\multicolumn{1}{|c|}{\\textbf{Tipo 2}} & \\multicolumn{1}{|c|}{\\textbf{Tipo 3}} & \\multicolumn{1}{|c|}{\\textbf{Total}} } \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endfirsthead");
        out.println("\\multicolumn{6}{c}%");
        out.println("{{\\bfseries \\tablename\\ \\thetable{} -- Continuação da página anterior.}} \\\\");
        out.println("\\hline");
        out.println("\\multicolumn{1}{|c|}{ \\multicolumn{1}{|c|}{\\textbf{Código}} & \\multicolumn{1}{l|}{\\textbf{Título} & \\textbf{Tipo 1}} & \\multicolumn{1}{|c|}{\\textbf{Tipo 2}} & \\multicolumn{1}{|c|}{\\textbf{Tipo 3}} & \\multicolumn{1}{|c|}{\\textbf{Total}} } \\\\ ");
        out.println("\\hline\\hline");
        out.println("\\endhead");
        out.println("\\hline \\multicolumn{6}{|r|}{{Continua na próxima página}} \\\\ \\hline");
        out.println("\\endfoot");
        out.println("\\hline\\hline");
        out.println("\\endlastfoot");
        
        for(Entry<String, Map<String, List<Usuario>>> nivel1 : dadosPorMusica.entrySet()) {
            String  codMusica = nivel1.getKey();
            String  titulo = getTituloByCodigo(codMusica);
            int[]   qtdTipo = new int[3];
            int     qtdTotal = 0;
            
            for(int iTipo = 0; iTipo < 3; iTipo += 1)
            {
                for(Entry<String, List<Usuario>> nivel2 : nivel1.getValue().entrySet()) {
                    String chave = nivel2.getKey();
                    int tipo = Integer.parseInt(chave.substring(0, 1)) - 1;
                    int nGravacoes = nivel2.getValue().size();
                    
                    qtdTipo[iTipo] += (tipo == iTipo) ? nGravacoes : 0;
                }
                qtdTotal += qtdTipo[iTipo];
            }
            
            out.println(String.format("%s\t%s\t%d\t%d\t%d\t%d", codMusica, titulo, qtdTipo[0], qtdTipo[1], qtdTipo[2], qtdTotal));
        }
        
        out.println("\\end{longtable}");
        //----------------------------------------------------------------------
        out.close();
        //----------------------------------------------------------------------
    }
    
    // codMusica >> tipo e algoritmo >> rank.
    public static Map<String, Map<String, List<Gravacao>>> getDadosPorMusica(Map<Integer, List<Usuario>> usuarios) {
        Map<String, Map<String, List<Gravacao>>> result = new HashMap<String, Map<String, List<Gravacao>>>();
        String[] titles = getTitles();
        
        for (int iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String tituloRef = titles[iMusica];
            String codMusicaRef = getCodidoByTitulo(tituloRef);
                        
            if (!result.containsKey(codMusicaRef)) result.put(codMusicaRef, new HashMap<String, List<Gravacao>>());
            Map<String, List<Gravacao>> musica = result.get(codMusicaRef);
            
            for(Entry<Integer, List<Usuario>> item : usuarios.entrySet()) {
                int iAlgoritmo = item.getKey();
                
                for(Usuario usuario : item.getValue()) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        
                        String titulo = gravacao.getNome();
                        if (!titulo.equalsIgnoreCase(tituloRef)) continue;
                        
                        int iTipoGravacao = gravacao.getTipo().equalsIgnoreCase("Tipo1")
                                                    ? 1 : gravacao.getTipo().equalsIgnoreCase("Tipo2")
                                                    ? 2 : gravacao.getTipo().equalsIgnoreCase("Tipo3")
                                                    ? 3 : 0;
                        
                        String strTipo_e_Algoritmo = String.format("%d%d", iTipoGravacao, iAlgoritmo);
                        if (!musica.containsKey(strTipo_e_Algoritmo)) musica.put(strTipo_e_Algoritmo, new ArrayList<Gravacao>());
                        
                        musica.get(strTipo_e_Algoritmo).add(gravacao);
                    }
                }
            }
        }
        
        return result;
    }
    
    /*
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
        
        String[] tipos = Util.createArray("Tipo1", "Tipo2", "Tipo3");
                
        Map<Integer, List<Usuario>> usuarios = new HashMap<Integer, List<Usuario>>();
        usuarios.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuarios.put(2, GetFromXML(path2));  // DTW_RelativePitch
        usuarios.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        
        Map<Integer, List<Usuario>> usuarios1e3 = new HashMap<Integer, List<Usuario>>();
        usuarios1e3.put(1, GetFromXML(path1));  // LevenshteinDistance
        usuarios1e3.put(3, GetFromXML(path3));  // DTW_AbsolutePitch
        
        int iMusica, iMedicao;
        //----------------------------------------------------------------------
        PrintWriter[] out = new PrintWriter[7];
        PrintWriter[] outTex = new PrintWriter[7];
        
        for(iMedicao = 0; iMedicao < 7; iMedicao += 1) {
            out[iMedicao] = new PrintWriter(new FileWriter(Util.getDirExecution(String.format("DadosEstatisticosDB_M%d.txt", 1 + iMedicao))));
            outTex[iMedicao] = new PrintWriter(new FileWriter(Util.getDirExecution(String.format("DadosEstatisticosDB_M%d.tex", 1 + iMedicao))));
        }
        //----------------------------------------------------------------------        
        // Medição 1:
        iMedicao = 0;
        
        System.out.print("Medição 1: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("Medição 1: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("\tT: Simboliza o tipo de gravação (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento).");
        out[iMedicao].println("\tA: Simboliza o algoritmo (1 - Distância de Levenshtein com código de Parson, 2 - DTW com pitch absoluto e 3 - DTW com pitch relativo).");
        
        outTex[iMedicao].print("");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            String codMusica = getCode(musica);
            
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
                            if (1 == registros) out[iMedicao].println(String.format("\n[código: %s] - %s.", codMusica, musica));
                            
                            out[iMedicao].print(String.format("\tT:%d;A:%d;[", iTipoGravacao, iAlgoritmo));
                            int[] curva = item.getValue();
                            for (int index = 0; index < curva.length-1; index += 1) {
                                out[iMedicao].print(String.format("%d;", curva[index]));
                            }
                            out[iMedicao].println(String.format("%d]", curva[curva.length-1]));
                        }
                    } // end for item
                } // end for iAlgoritmo
            } // end for iTipo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 2:
        iMedicao = 1;
        
        System.out.print("Medição 2: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("Medição 2: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("\tT: Simboliza o tipo de gravação (1 - com acompanhamento de piano, 2 - com acompanhamento de música e 3 - sem acompanhamento).");
        for (String musica : titles) {
            String codMusica = getCode(musica);
            
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
                        if (1 == registros) out[iMedicao].println(String.format("\n[código: %s] - %s.", codMusica, musica));

                        out[iMedicao].print(String.format("\tT:%d;[", iTipoGravacao));
                        int[] curva = item.getValue();
                        for (int index = 0; index < curva.length-1; index += 1) {
                            out[iMedicao].print(String.format("%d;", curva[index]));
                        }
                        out[iMedicao].println(String.format("%d]", curva[curva.length-1]));
                    }
                } // end for item
            } // end for iTipo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 3:
        iMedicao = 2;
        
        System.out.print("Medição 3: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("Medição 3: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("\tA: Simboliza o algoritmo (1 - Distância de Levenshtein com código de Parson, 2 - DTW com pitch absoluto e 3 - DTW com pitch relativo).");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            String codMusica = getCode(musica);
            
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
                        if (1 == registros) out[iMedicao].println(String.format("\n[código: %s] - %s.", codMusica, musica));

                        out[iMedicao].print(String.format("\tA:%d;[", iAlgoritmo));
                        int[] curva = item.getValue();
                        for (int index = 0; index < curva.length-1; index += 1) {
                            out[iMedicao].print(String.format("%d;", curva[index]));
                        }
                        out[iMedicao].println(String.format("%d]", curva[curva.length-1]));
                    }
                } // end for item
            } // end for iAlgoritmo
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 4:
        iMedicao = 3;
                
        System.out.print("Medição 4: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("Medição 4: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        for (iMusica = 0; iMusica < titles.length; iMusica += 1) {
            String musica = titles[iMusica];
            String codMusica = getCode(musica);
            
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
            
            out[iMedicao].println(String.format("\n[código: %s] - %s.", codMusica, musica));
            out[iMedicao].print("\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out[iMedicao].print(String.format("%d;", curva[index]));
            }
            out[iMedicao].println(String.format("%d]", curva[curva.length-1]));
            
        } // end for musica
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 5:
        iMedicao = 4;
        
        TreeMap<String, Integer> dicionario5 = new TreeMap<String, Integer>();
        System.out.println("Medição 5: ");
        out[iMedicao].println("Medição 5: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("\tObs.: Músicas ordenadas decrescentemente pelo número de gravações.");
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
            String codMusica = getCode(musica);
            
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

            out[iMedicao].println(String.format("\n[código: %s] - %s. Total de gravações: %d.", codMusica, musica, entry.getValue()));
            out[iMedicao].print("\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out[iMedicao].print(String.format("%d;", curva[index]));
            }
            out[iMedicao].println(String.format("%d]", curva[curva.length-1]));

            System.out.println(String.format("%d", iMusica));
        }
                
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 6:
        iMedicao = 5;
        
        TreeMap<String, Integer> dicionario6 = new TreeMap<String, Integer>();
        System.out.println("Medição 6: ");
        out[iMedicao].println("Medição 6: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº].");
        out[iMedicao].println("\tObs.: Músicas ordenadas decrescentemente pelo número de gravações.");
        out[iMedicao].println("\tObs.: Algoritmos considerados: A1 e A3.");
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
            String codMusica = getCode(musica);
            
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

            out[iMedicao].println(String.format("\n[código: %s] - %s. Total de gravações: %d.", codMusica, musica, entry.getValue()));
            out[iMedicao].print("\t[");
            for (int index = 0; index < curva.length-1; index += 1) {
                out[iMedicao].print(String.format("%d;", curva[index]));
            }
            out[iMedicao].println(String.format("%d]", curva[curva.length-1]));

            System.out.println(String.format("%d", iMusica));
        }
                
        System.out.println(String.format("%d", iMusica));
        //----------------------------------------------------------------------
        // Medição 7:
        iMedicao = 6;
        
        TreeMap<String, Integer> dicionario7 = new TreeMap<String, Integer>();
        System.out.println("Medição 7: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº] de acordo com tipo de gravação e algoritmo.");
        out[iMedicao].println("Medição 7: Contagem de encontro de músicas em posições do ranque: [1º, 2º, ..., Nº] de acordo com tipo de gravação e algoritmo.\n");
        for (int iTipo = 1; iTipo <= 3; iTipo += 1) {
            String tipo = tipos[iTipo-1];
            
            for (int iAlgoritmo = 1; iAlgoritmo <= 3; iAlgoritmo += 1) {                
                int[] curva = new int[titles.length];
                
                List<Usuario> lista = usuarios.get(iAlgoritmo);
                
                for(Usuario usuario : lista) {
                    for(Gravacao gravacao : usuario.getGravacoes()) {
                        if (tipo.equalsIgnoreCase(gravacao.getTipo())) {
                            int posicao = (int)gravacao.getPosicao();
                            curva[posicao-1] += 1;
                        } // end if.
                    } // end for.
                } // end for.
                
                out[iMedicao].print(String.format("T:%d;A:%d;[", iTipo, iAlgoritmo));
                for (int index = 0; index < curva.length-1; index += 1) {
                    out[iMedicao].print(String.format("%d;", curva[index]));
                }
                out[iMedicao].println(String.format("%d]", curva[curva.length-1]));
                
            } // end for iAlgoritmo.
        } // end for iTipo.
        //----------------------------------------------------------------------
        for(iMedicao = 0; iMedicao < 7; iMedicao += 1) {
            out[iMedicao].close();
        }
        //----------------------------------------------------------------------
    }
    */
    
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
                                musicaEsperada = getTituloByArquivo(fileNivel3.getName().trim());
                                
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