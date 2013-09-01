package com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation;

/**
 *
 * @author acaranha
 */
public class MelodyRepresentationNote {
    // private final String[] NOTE_NAMES = {"C", "C#|Db", "D", "D#|Eb", "E", "F", "F#|Gb", "G", "G#|Ab", "A", "A#|Bb", "B"};

    private double pitch;
    private double onset;    // in seconds.
    private double offset;   // in seconds.

    public MelodyRepresentationNote(double pitch, double onset) {
        this.pitch    = pitch;
        this.onset    = onset;
        this.offset   = -1;
    }

    public MelodyRepresentationNote(double pitch, double onset, double duration) {
        this.pitch    = pitch;
        this.onset    = onset;
        this.offset   = onset + duration;
    }

    @Override
    public String toString() {
        return String.format("onset: %.6f, offset: %.6f, note: %.2f", this.onset, this.offset, this.pitch);
    }

    public double getPitch(boolean midiFormat) {
        return midiFormat ? this.pitch : 440.0 * Math.pow(2.0, (this.pitch - 69) / 12);
    }

    public double getOnset() {
        return onset;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getDuration() {
        return (this.offset == -1) ? 0.0 : this.offset - this.onset;
    }
}