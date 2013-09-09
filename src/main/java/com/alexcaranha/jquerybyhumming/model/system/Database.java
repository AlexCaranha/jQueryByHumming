package com.alexcaranha.jquerybyhumming.model.system;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.database.detail.Database_Detail_Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.elasticsearch.action.index.IndexResponse;

/**
 *
 * @author Lual
 */
public class Database {
    List<Database_Detail_Model> modelo = new ArrayList<Database_Detail_Model>();
    
    public Database() throws IOException, InvalidMidiDataException, MidiUnavailableException, WavFileException {
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/002.mid").getInputStream(), "Águas de março", "Antonio Carlos Jobim"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/003.mid").getInputStream(), "Carinhoso", "Pixinguinha"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/004.mid").getInputStream(), "Asa branca", "Luiz Gonzaga e Humberto Teixeira"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/006.mid").getInputStream(), "Chega de saudade", "Antonio Carlos Jobim"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/008.mid").getInputStream(), "Detalhes", "Roberto Carlos"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/010.mid").getInputStream(), "Alegria, alegria", "Caetano Veloso"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/012.mid").getInputStream(), "Aquarela do Brasil", "Ary Barroso"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/015.mid").getInputStream(), "Trem das onze", "Adoniran Barbosa"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/019.mid").getInputStream(), "Quero que vá tudo pro inferno", "Roberto Carlos e Erasmo Carlos"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/020.mid").getInputStream(), "Preta pretinha", "Os Novos Baianos"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/023.mid").getInputStream(), "Inútil", "Roger Moreira"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/024.mid").getInputStream(), "Eu sei que vou te amar", "Antonio Carlos Jobim"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/025.mid").getInputStream(), "País tropical", "Jorge Ben Jor"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/027.mid").getInputStream(), "Garota de ipanema", "Antonio Carlos Jobim"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/028.mid").getInputStream(), "Pra não dizer que não falei das flores", "Geraldo Vandré"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/031.mid").getInputStream(), "Travessia", "Milton Nascimento"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/038.mid").getInputStream(), "Eu quero é botar meu bloco na rua", "Sergio Sampaio"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/041.mid").getInputStream(), "Manhã de carnaval", "Luiz Bonfá e Elizete Cardoso"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/046.mid").getInputStream(), "Ponteio", "Edu Lobo"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/047.mid").getInputStream(), "Me chama", "Lobão"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/057.mid").getInputStream(), "Conversa de botequim", "Noel Rosa"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/062.mid").getInputStream(), "Luar do sertão", "Catulo da Paixão Cearense"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/063.mid").getInputStream(), "Alagados", "Bi Ribeiro, João Barone e Herbert Vianna"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/064.mid").getInputStream(), "As curvas da estrada de Santos", "Erasmo Carlos, Roberto Carlos"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/067.mid").getInputStream(), "A banda", "Chico Buarque"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/068.mid").getInputStream(), "Comida", "Arnaldo Antunes, Marcelo Fromer e Sérgio Britto"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/070.mid").getInputStream(), "Ronda", "Paulo Vanzolini"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/072.mid").getInputStream(), "Gita", "Raul Seixas"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/074.mid").getInputStream(), "Sentado à beira do caminho", "Roberto Carlos e Erasmo Carlos"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/075.mid").getInputStream(), "Foi um rio que passou em minha vida", "Paulinho da Viola"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/081.mid").getInputStream(), "Que país é este?", "Renato Russo"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/083.mid").getInputStream(), "Ideologia", "Roberto Frejat e Cazuza"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/084.mid").getInputStream(), "Rosa", "Pixinguinha"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/085.mid").getInputStream(), "O barquinho", "Roberto Menescal e Ronaldo Bôscoli"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/087.mid").getInputStream(), "Meu mundo e nada mais", "Guilherme Arantes"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/089.mid").getInputStream(), "A flor e o espinho", "A Flor E O Espinho"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/091.mid").getInputStream(), "Felicidade", "Lupicínio Rodrigues"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/093.mid").getInputStream(), "Casa no campo", "Zé Rodrix, Tavito"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/096.mid").getInputStream(), "Disritmia", "Martinho da Vila"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/097.mid").getInputStream(), "Você não soube me amar", "Ricardo Barreto, Evandro Mesquita, Zeca Mendigo e Guto Barros"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/098.mid").getInputStream(), "A noite de meu bem", "Dolores Duran"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/100.mid").getInputStream(), "Anna Júlia", "Marcelo Camelo"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/101.mid").getInputStream(), "Parabéns a você", "Bertha Celeste"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/102.mid").getInputStream(), "Ciranda cirandinha", "Desconhecido"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/103.mid").getInputStream(), "Escravos de jó", "Desconhecido"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/104.mid").getInputStream(), "Hino nacional", "Francisco Manuel da Silva"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/105.mid").getInputStream(), "Hino da bandeira", "Francisco Braga"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/107.mid").getInputStream(), "Ilariê", "Cid Guerreiro"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/108.mid").getInputStream(), "Fim de Ano", "David Nasser e de Francisco Alves"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/109.mid").getInputStream(), "Mamãe eu quero", "Vicente Paiva"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/111.mid").getInputStream(), "Coelhinho de olhos vermelhos", "Desconhecido"));
        modelo.add(new Database_Detail_Model(App.getContext().getResource("classpath:database/midi/112.mid").getInputStream(), "Chegou a hora da foqueira", "Lamartine Babo"));
    }
    
    public void execute() throws InterruptedException, ExecutionException {
        for (Database_Detail_Model item : this.modelo) {
            IndexResponse response = App.getDB().create(Database_Detail_Model.type, null, item.getVariables());
            System.out.println(String.format("Id: %s, type: %s, index: %s", response.getId(), response.getType(), response.getIndex()));
        }
    }
    
    /*
    public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        App.startApplication();
        
        Database db = new Database();
        db.execute();
        
        App.endApplication();
    }
    */
}
