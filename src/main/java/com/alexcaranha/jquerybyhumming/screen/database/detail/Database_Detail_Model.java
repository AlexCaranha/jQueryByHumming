package com.alexcaranha.jquerybyhumming.screen.database.detail;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author alexcaranha
 */
public final class Database_Detail_Model implements IModel {

   public static final String type = "songs";
   private Map<String, Object> variables;

   public Database_Detail_Model() {
       this.variables = new HashMap<String, Object>();
       this.variables.put("title", "");
       this.variables.put("author", "");
       this.variables.put("obs", "");
       this.variables.put("melodyRepresentation", null);
   }
   public Database_Detail_Model(String id, Map<String, Object> variables) {
       this.variables = variables;
       this.variables.put("id", id);
   }
   
   public Database_Detail_Model(InputStream inputStream, String title, String author) throws IOException, InvalidMidiDataException, MidiUnavailableException {
       this.variables = new HashMap<String, Object>();
       
       this.setMidiFile(inputStream);
       this.setTitle(title);
       this.setAuthor(author);
       this.setObs("");
   }

   public void clear() {
       this.variables = null;
       this.variables = new HashMap<String, Object>();
       this.variables.put("title", "");
       this.variables.put("author", "");       
       this.variables.put("obs", "");
       this.variables.put("melodyRepresentation", null);
   }
   
   public String getId() {
       if (!this.variables.containsKey("id")) return null;
       return Convert.toString(this.variables.get("id"));
   }
   
   public String getTitle() {
       return Convert.toString(this.variables.get("title"));
   }

   public void setTitle(String title) {
       this.variables.put("title", title);
   }

   public String getAuthor() {
       return Convert.toString(this.variables.get("author"));
   }

   public void setAuthor(String author) {
       this.variables.put("author", author);
   }
   
   public String getObs() {
       String obs = Convert.toString(this.variables.get("obs"));
       return obs == null ? "" : obs;
   }

   public void setObs(String obs) {
       this.variables.put("obs", obs);
   }
   
   public Map<String, Object> getVariables() {
       return this.variables;
   }

   public String getLabelVariable(int index) {
       if (this.variables == null) return null;
       if (this.variables.isEmpty()) return null;
       return Convert.toString(this.variables.keySet().toArray()[index]);
   }
    /*
   public String getValueVariable(int index) {
       if (this.variables == null) return null;
       if (this.variables.isEmpty()) return null;
       return Convert.toString(this.variables.values().toArray()[index]);
   }
   */
   public MelodyRepresentation getMidiFileSimplified() throws IOException, ClassNotFoundException {
       String midiFileSimplified;

       if (this.variables.get("melodyRepresentation") == null) return null;
//
       midiFileSimplified = Convert.toString(this.variables.get("melodyRepresentation"));
       return (MelodyRepresentation) Convert.deserialize(midiFileSimplified);
   }

   public void setMidiFile(InputStream inputStream) throws IOException, InvalidMidiDataException, MidiUnavailableException {
       MelodyRepresentation midiSimplified = new MelodyRepresentation(inputStream);
       this.variables.put("melodyRepresentation", Convert.serialize(midiSimplified));
   }
}
