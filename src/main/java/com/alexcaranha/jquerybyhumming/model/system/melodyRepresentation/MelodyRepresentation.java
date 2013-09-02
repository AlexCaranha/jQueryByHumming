package com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.google.common.primitives.Doubles;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author acaranha
 */
public class MelodyRepresentation extends Configuration implements IExecutable, Serializable {
    private double                 tempoBPM;   // in BPM.
    private double                 tempoMPQ;   // in MPQ
    private double                 duration;   // in seconds.
    private double                 resolution;
    private Map<Double, MelodyRepresentationNote>  midiNotes;

    public MelodyRepresentation() {
        this.tempoBPM    = 0.0;
        this.tempoMPQ    = 0.0;
        this.duration    = 0.0;
        this.resolution  = 0.0;
        this.midiNotes = new HashMap<Double, MelodyRepresentationNote>();
    }
    
    public MelodyRepresentation(InputStream inputStream) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        this.tempoBPM    = 0.0;
        this.tempoMPQ    = 0.0;
        this.duration    = 0.0;
        this.resolution  = 0.0;

        this.midiNotes = this.readFromMidiFile(inputStream);
    }
    
    public Map<Double, MelodyRepresentationNote> getMidiNotes() {
        return this.midiNotes;
    }
            
    public void createWaveFile(String path) throws IOException, WavFileException {
        SignalXY signalXY = convertToSignalXY(true);
        Util.createWavFileFromSynthesis(signalXY, 8000, path);
    }

    public MelodyRepresentationNote getMidiNoteFromTime(double timeInSeconds) {
        if (timeInSeconds > 0.0)
        for (MelodyRepresentationNote midiNote : midiNotes.values()) {
            if (Util.valueIsOnItervalInclusive(timeInSeconds, midiNote.getOnset(), midiNote.getOffset())) {
                return midiNote;
            }
        }
        return null;
    }
    /*
    public void fillSignalXYFromAxisX(SignalXY signal) {
        MelodyRepresentationNote midiNote;
        double time, frequency;

        for (int index = 0; index < signal.getLength(); index += 1) {
            time = signal.getX(index);
            midiNote = getMidiNoteFromTime(time);
            if (midiNote != null) {
                frequency = midiNote.getFreqInHz();
                signal.changeValueY(index, frequency);
            }
        }
    }
    */
    public List<Marker> convertToOnsetMarks(Color color) {
        List<Marker>    markers = new ArrayList<Marker>();
        ValueMarker     marker;
        double          start;

        for (MelodyRepresentationNote midiNote : midiNotes.values()) {
            start = midiNote.getOnset();

            marker = new ValueMarker(start);
            marker.setPaint(color);
            marker.setLabel(Convert.toString(midiNote.getPitchInMidi()));
            marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
            marker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            markers.add(marker);
        }
        return markers;
    }
    
    public SignalXY convertToSignalXY(boolean inMidiFormat) {
        XYSeries series = convertToXYSeries("signalXY", inMidiFormat);
        int qtd = series.getItemCount();
        double[] x = new double[qtd];
        double[] y = new double[qtd];

        for (int i = 0; i < qtd; i += 1) {
            XYDataItem item = series.getDataItem(i);

            x[i] = item.getXValue();
            y[i] = DSPFunctions.midiToHertz(item.getYValue());
        }

        return new SignalXY(x, y);
    }
    
    public XYSeries convertToXYSeries(String name, boolean inMidiFormat) {
        XYSeries signal = new XYSeries(name);
        double endBefore;
        double start, end;
        MelodyRepresentationNote lastMidiNote = null;

        //System.out.println(this.toString());

        end = 0.0;
        for (MelodyRepresentationNote midiNote : midiNotes.values()) {
            endBefore = end;

            start = midiNote.getOnset();
            end   = midiNote.getOffset();

            if ((start - endBefore) > 0.02) {
                signal.add(endBefore + 0.01, 0.0);
                signal.add(start - 0.01, 0.0);
            }
            
            double pitch = (inMidiFormat) 
                                ? midiNote.getPitchInMidi() 
                                : midiNote.getPitchInHertz();

            signal.add(start, pitch);
            signal.add(end, pitch);

            lastMidiNote = midiNote;
        }

        if (lastMidiNote != null){
            double pitchLastNote = (inMidiFormat) 
                                        ? lastMidiNote.getPitchInMidi() 
                                        : lastMidiNote.getPitchInHertz();
            
            if (pitchLastNote != 0) {
                signal.add(lastMidiNote.getOffset(), 0.0);
                signal.add(lastMidiNote.getOffset() + 1.0, 0.0);
            }
        }
        return signal;
    }
    /*
    public void createSignalXYOfOnsetMarkers(SignalXY signal) {
        double time;

        for (MelodyRepresentationNote midiNote : midiNotes) {
            double   onset = midiNote.getOnset();
            double[] differences = new double[signal.getLength()];

            for (int index = 0; index < signal.getLength(); index += 1) {
                time = signal.getX(index);
                differences[index] = Math.abs(onset - time);
            }

            KeyValue min = new KeyValue(0, 0.0);
            SignalFunctions.min(differences, min);

            signal.changeValueY(Convert.toInteger(min.getKey()), 1.0);
        }
    }
    */

    /*
    public List<Marker> createOnsetMarkers(SignalXY signal, Color color) {
        List<Marker> markers = new ArrayList<Marker>();
        Marker marker;
        double time;

        for (MelodyRepresentationNote midiNote : midiNotes) {
            double   onset = midiNote.getOnset();
            double[] differences = new double[signal.getLength()];

            for (int index = 0; index < signal.getLength(); index += 1) {
                time = signal.getX(index);
                differences[index] = Math.abs(onset - time);
            }

            KeyValue min = new KeyValue(0, 0.0);
            SignalFunctions.min(differences, min);

            marker = new ValueMarker(signal.getX(Convert.toInteger(min.getKey())));
            marker.setPaint(color);
            markers.add(marker);
        }
        return markers;
    }
    */
    @Override
    public String toString() {
        String result = "";

        result = String.format("\nDuration: %s seconds", this.duration);

        for (MelodyRepresentationNote midiNote : midiNotes.values()) {
            result += "\n" + midiNote.toString();
        }

        return result;
    }

    private Map<Double, MelodyRepresentationNote> readFromMidiFile(InputStream inputStream) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        MidiFile midiFile = new MidiFile(inputStream);

        resolution = midiFile.getResolution();
        return processing(getNotesFromMidiFile(midiFile));
    }

    private List<MelodyRepresentationNote> getNotesFromMidiFile(MidiFile midiFile) {
        double time, factor = 0;
        Map<Integer, MelodyRepresentationNote>  newNotes = new HashMap<Integer, MelodyRepresentationNote>();
        List<MelodyRepresentationNote>          notes = new ArrayList<MelodyRepresentationNote>();
        MelodyRepresentationNote                note;

        for (MidiTrack track : midiFile.getTracks()) {
            Iterator<com.leff.midi.event.MidiEvent> it = track.getEvents().iterator();
            while(it.hasNext()) {
                MidiEvent event = it.next();

                if (event instanceof Tempo) {
                    Tempo noteTempo = (Tempo) event;

                    //this.tempoBPM = noteTempo.getBpm();
                    this.tempoMPQ = noteTempo.getMpqn();

                    factor = this.tempoMPQ / (resolution * 1000000);
                    continue;
                }

                if (event instanceof NoteOn) {
                    NoteOn  noteOn = (NoteOn) event;
                    long    tick = event.getTick();
                    int     key = noteOn.getNoteValue();

                    time = tick * factor;
                    note = new MelodyRepresentationNote(key, time, noteOn.getVelocity());
                    newNotes.put(key, note);

                    continue;
                }

                if (event instanceof NoteOff) {
                    NoteOff noteOff = (NoteOff) event;
                    long    tick = event.getTick();
                    int     key = noteOff.getNoteValue();

                    time = tick * factor;
                    note = newNotes.get(key);
                    note.setOffset(time);
                    newNotes.remove(note);
                    notes.add(note);

                    this.duration = (time > this.duration) ? time : this.duration;
                    continue;
                }
            }
        }

        return notes;
    }

    // Inspired on skyline algorithm from 'MusicDB: A Query by Humming System'.
    private Map<Double, MelodyRepresentationNote> processing(List<MelodyRepresentationNote> notes) {
        Map<Double, MelodyRepresentationNote> localNotes = new HashMap<Double, MelodyRepresentationNote>();
        MelodyRepresentationNote noteWithHighestKey;

        for (MelodyRepresentationNote note : notes) {
            noteWithHighestKey = hasSomeOverlap(note, notes);
            if (!localNotes.containsKey(noteWithHighestKey)) {
                localNotes.put(noteWithHighestKey.getOnset(), noteWithHighestKey);
            }
        }

        return new TreeMap<Double, MelodyRepresentationNote>(localNotes);
    }
    
    private MelodyRepresentationNote hasSomeOverlap(MelodyRepresentationNote noteReference, List<MelodyRepresentationNote> notes) {
        MelodyRepresentationNote    noteWithHighestKey = noteReference;

        for (MelodyRepresentationNote note : notes) {
            if (Util.valueIsOnItervalExclusive(note.getOnset(), noteReference.getOnset(), noteReference.getOffset()) ||
                Util.valueIsOnItervalExclusive(note.getOffset(), noteReference.getOnset(), noteReference.getOffset())) {

                if (note.getPitchInMidi() > noteWithHighestKey.getPitchInMidi()) {
                    noteWithHighestKey = note;
                }
            }
        }
        return noteWithHighestKey;
    }

    private void generateTableLaTeX(String title, String pathFileLaTeX) {
        //----------------------------------------------------------------------
        StringBuilder template = new StringBuilder();

        template.append("\\begin{table}[H]");
        template.append("\n\t\\scalefont{0.75}");        
        template.append("\n\t\\centering");
        template.append("\n\t\\caption{Texto.}");        
        template.append("\n\t\\label{tab:chap06_ProcessamentoEx1}");
        template.append("\n\t\\begin{tabular}{|c|c|c|}");
        template.append("\n\t\t\\hline");
        template.append("\n\t\t\\multicolumn{3}{|c|}{\\textbf{{title}}} \\\\");
        template.append("\n\t\t\\hline\\hline");
        template.append("\n\t\t\\textbf{\\textit{Onset}} & \\textbf{\\textit{Offset}} & \\textbf{\\textit{Pitch}} \\\\");
        
        for (MelodyRepresentationNote note : this.midiNotes.values()) {
            template.append(
                String.format("\n\t\t\\hline\\hline" +
                              "\n\t\t{%.4f} & {%.4f} & {%.4f} \\\\",
                              note.getOnset(), note.getOffset(), note.getPitchInMidi())
                );
        }
        
        template.append("\n\t\t\\hline\\hline");
        template.append("\n\t\\end{tabular}");
        template.append("\n\t\\label{tab:chap06_label}");
        template.append("\n\\end{table}");
        
        String phrase = template.toString();
        phrase = phrase.replace("{title}", title);
        //----------------------------------------------------------------------
        try {
            File arquivo = new File(pathFileLaTeX);

            FileOutputStream fos = new FileOutputStream(arquivo);
            fos.write(phrase.getBytes());
            fos.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        //----------------------------------------------------------------------
    }

    @Override
    public String getTitle() {
        return "MELODY REPRESENTATION";
    }

    @Override
    public String getAlias() {
        return "MR";
    }

    public void execute(Map<String, Object> params) throws Exception {
        SignalXY        pitchTracking = (SignalXY) params.get("pitchTracking");
        List<KeyValue>  onsetDetection = (List<KeyValue>) params.get("onsetDetection");
                
        int N = onsetDetection.size();
        
        midiNotes.clear();
        for(int n = 1; n <= N; n += 1) {
            KeyValue keyValueActual = onsetDetection.get(n - 1);
            KeyValue keyValueNext   = (n < N) ? onsetDetection.get(n) : null;
            
            int      iStart = Convert.toInteger(keyValueActual.getKey());
            int      iEnd   = iStart;
            
            if (keyValueNext != null) {
                iEnd = Convert.toInteger(keyValueNext.getKey());
            } else {
                while (true) {                    
                    if (iEnd + 1 < pitchTracking.getLength() && pitchTracking.getY(iEnd + 1) > 0.0) {
                        iEnd += 1;
                    } else break;
                }
            }
            
            //double   tempo     = Convert.toDouble(keyValueActual.getValue());            
            double   amplitudeHz = amplitudeInPitchTracking(iStart, iEnd, pitchTracking);
            double   midiNote = DSPFunctions.hertzToMidi(amplitudeHz);
            
            double   inicio  = pitchTracking.getX(iStart);
            double   fim     = pitchTracking.getX(iEnd);
            double   duracao = fim - inicio; // in seconds.
            
            this.midiNotes.put(inicio, new MelodyRepresentationNote(midiNote, inicio, duracao));
        }
        
        this.midiNotes = new TreeMap<Double, MelodyRepresentationNote>(this.midiNotes);
    }
    
    private double amplitudeInPitchTracking(int iStart, int iEnd, SignalXY pitchTracking) {
        List<Double> listAmplitudes = new ArrayList();
        
        for (int index = iStart; index <= iEnd; index += 1) {
            double amplitude = pitchTracking.getY(index);
            
            if (amplitude > 0.0) listAmplitudes.add(amplitude);
        }
                
        return listAmplitudes.size() > 0 ? Util.median(Doubles.toArray(listAmplitudes)) : 0.0;
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, WavFileException, InvalidMidiDataException, MidiUnavailableException {
        ApplicationContext context = new ClassPathXmlApplicationContext("configuration/spring-config.xml");
        File fileAux = context.getResource("classpath:samples/midi/signal3.mid").getFile();

        String pathMidi = fileAux.getAbsolutePath();
        MelodyRepresentation file = new MelodyRepresentation(new FileInputStream(pathMidi));

        System.out.println(file);
        String path = Util.getDirExecution("tmpMelodyRepresentation.txt");
        file.generateTableLaTeX("Parabéns a você", path);
    }
}
