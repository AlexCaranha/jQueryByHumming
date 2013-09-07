package com.alexcaranha.jquerybyhumming.model;

import com.alexcaranha.jquerybyhumming.components.XYGraphSignal;
import com.alexcaranha.lib.com.musicg.dsp.Resampler;
import com.alexcaranha.lib.com.musicg.wave.Wave;
import com.alexcaranha.lib.com.musicg.wave.WaveFileManager;
import com.alexcaranha.lib.com.musicg.wave.WaveHeader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author alexcaranha
 */
public class WavSignal extends Signal {

    public WavSignal() {
        super("signal");
    }

    /**
     * Load signal from wav file.
     *
     * @param path path of file.
     * @param notifyObservers indicates whether observers will be notified.
     * @throws Exception
     */
    //public void loadFromWavFile(String path, boolean notifyObservers, String alias) throws Exception {
    public void loadFromWavFile(Map<String, Object> args) throws Exception {
        //----------------------------------------------------------------------
        _state      = Signal.BasicStates.LOCKED;

        _signalXY   = resampleWavSignalFromInputStream((InputStream)(args.get("inputStream")), 8000);

        if (args.containsKey("notifyObservers") && Convert.toBoolean(args.get("notifyObservers"))) {
            this.setChanged();
            this.notifyObservers(Util.createArray(new KeyValue("CLASS", null), new KeyValue("LOAD", true)));
            
            Thread.sleep(250);
            
            this.setChanged();
            this.notifyObservers(Util.createArray(new KeyValue("CLASS", XYGraphSignal.class),
                                                  new KeyValue("ALIAS", Convert.toString(args.get("alias"))),
                                                  new KeyValue("DATASET", Util.createArray(
                                                                                            new KeyValue("DATASET", convertToXYDataSet()),
                                                                                            new KeyValue("TITLE_X", "tempo (s)"),
                                                                                            new KeyValue("TITLE_Y", "amplitude")
                                                                                          ))));
        }
        _state = Signal.BasicStates.FREE;
        //----------------------------------------------------------------------
    }

    public static Map<String, Object> loadBufferAudio(String path,
                                                      AudioFormat audioFormat) throws UnsupportedAudioFileException, IOException {
        File                file = new File(path);
        List<KeyValue>      bufferAudio = new ArrayList<KeyValue>();
        Map<String, Object> result = new HashMap<String, Object>();

        AudioInputStream    audioInputStream = AudioSystem.getAudioInputStream(file);
        bufferAudio.clear();
        while (true) {
            byte[]  abData = new byte[64];
            int     nBytesRead = audioInputStream.read(abData, 0, abData.length);

            if (nBytesRead == -1) {
                break;
            }

            bufferAudio.add(new KeyValue(abData, nBytesRead));
        }

        audioFormat = audioInputStream.getFormat();
        long        audioFileLength = file.length();
        int         frameSize = audioFormat.getFrameSize();
        float       frameRate = audioFormat.getFrameRate();

        double      durationInSeconds = (double) (audioFileLength / (frameSize * frameRate));

        result.put("bufferAudio", bufferAudio);
        result.put("audioFormat", audioFormat);
        result.put("durationInSeconds", durationInSeconds);

        return result;
    }
    
    public static SignalXY resampleWavSignalFromPath(String path, int sampleRate) throws IOException, Exception {
        return resampleWavSignal(new Wave(path), sampleRate);
    }

    public static SignalXY resampleWavSignalFromInputStream(InputStream inputStream, int sampleRate) throws IOException, Exception {
        return resampleWavSignal(new Wave(inputStream), sampleRate);
    }

    private static SignalXY resampleWavSignal(Wave wave, int sampleRate) throws IOException, Exception {
        // Resample to
        Resampler resampler = new Resampler();
        int sourceRate = wave.getWaveHeader().getSampleRate();
        byte[] resampledWaveData = resampler.reSample(wave.getBytes(), wave.getWaveHeader().getBitsPerSample(), sourceRate, sampleRate);

        // Update the wave header
        WaveHeader resampledWaveHeader = wave.getWaveHeader();
        resampledWaveHeader.setSampleRate(sampleRate);

        // Make resampled wave
        Wave resampledWave = new Wave(resampledWaveHeader, resampledWaveData);
        double[] amplitudes = resampledWave.getNormalizedAmplitudes();

        WaveFileManager wfm = new WaveFileManager(resampledWave);
        wfm.saveWaveAsFile(Constants.PATH_TMP_WAVE_FILE_SEARCH);

        SignalXY signalXY = new SignalXY(Util.divide(Util.createArithmeticProgressionSequence((double) 0.0, (double) 1.0, (double) (amplitudes.length - 1)), (double) sampleRate),
                                         amplitudes);

        return signalXY;
    }
}
