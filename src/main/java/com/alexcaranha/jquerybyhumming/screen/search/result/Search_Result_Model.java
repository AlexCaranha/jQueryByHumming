package com.alexcaranha.jquerybyhumming.screen.search.result;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author alexcaranha
 */
public class Search_Result_Model implements IModel {

    private String type;
    private Map<String, Object> variables;

    public Search_Result_Model() {
        this.type = "songs";

        this.variables = new HashMap<String, Object>();
        this.variables.put("title", "");
        this.variables.put("author", "");
        this.variables.put("midiFile", null);
        this.variables.put("midiFileSimplified", null);
    }

    public String getType() {
        return type;
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

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public MelodyRepresentation getMidiFileSimplified() throws IOException, ClassNotFoundException {
        /*
        byte[] buffer = (byte[]) this.variables.get("midiFileSimplified");

        return Convert.deserialize(buffer);
        */
        return null;
    }

    public void setMidiFile(String path) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        /*
        this.variables.put("midiFile", Convert.serializeFile(path));

        MelodyRepresentation midiSimplified = new MelodyRepresentation(path);
        this.variables.put("midiFileSimplified", Convert.serialize(midiSimplified));
        */
    }

    /*
    public void setMidiFile(String path) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        MelodyRepresentation melodySimplified = new MelodyRepresentation(path);
        this.variables.put("melodySimplified", melodySimplified);

        byte[] buffer = IOUtils.toByteArray(new FileInputStream(path));
        this.variables.put("midiFile", buffer);

        File tempFile = File.createTempFile("midiFile", ".tmp");
        System.out.println("path: " + tempFile.getAbsolutePath());
        System.out.println("canWrite: " + tempFile.canWrite());

        byte[] bufferZip = Snappy.compress(buffer);
        buffer = Snappy.uncompress(bufferZip);

        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(buffer);
        fos.close();

        MelodyRepresentation melodySimplified2 = new MelodyRepresentation(tempFile.getAbsolutePath());

        System.out.println(String.format("%s\n\n%s", melodySimplified.toString(), melodySimplified2.toString()));

        System.out.println("deleted: " + tempFile.delete());
        //----------------------------------------------------------------------
    }
    */
}
