package com.alexcaranha.jquerybyhumming.model;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.screen.main.Main_Presenter;
import com.alexcaranha.jquerybyhumming.screen.search.main.Search_Main_Presenter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author alexcaranha
 */
public class Microphone extends Thread {
    private TargetDataLine        m_line = null;
    private AudioFileFormat.Type  m_targetType;
    private AudioInputStream      m_audioInputStream;
    private File                  m_outputFile;

    private DataLine.Info         info;
    private AudioFormat           audioFormat;

    private final int SAMPLE_RATE = 8000; // Hz
    private final int RESOLUTION  = 16;   // BITS
    private final int CHANELS     = 1;    // MONO

    public Microphone(String pathFile){
        audioFormat = new AudioFormat(SAMPLE_RATE,
                                      RESOLUTION,
                                      CHANELS,
                                      true,
                                      true);
        
        m_outputFile = new File(pathFile);
        
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
    }
    
    public void startRecord() throws LineUnavailableException{
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
        m_line = (TargetDataLine) AudioSystem.getLine(info);
        m_line.open(audioFormat);
        
        m_audioInputStream = new AudioInputStream(m_line);
        m_targetType       = AudioFileFormat.Type.WAVE;
        m_line.start();
        
        super.start();
    }

    public void stopRecord(){
        if (m_line != null){
            m_line.stop();
            m_line.close();
            m_line = null;
        }
    }
    
    @Override
    public void run(){
        try{
            AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
        }catch (IOException ex){
            Logger.getLogger(Microphone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isSupported() {
        boolean result = AudioSystem.isLineSupported(Port.Info.MICROPHONE);
        
        if (false == result) {
            App.resetMicrophone();
        }
        //----------------------------------------------------------------------
        Search_Main_Presenter presenter = (Search_Main_Presenter) App.getObject("Search_Main_Presenter");        
        if (presenter != null)
            presenter.enabledRecordHumming(result);
        //----------------------------------------------------------------------
        return result;
    }
}
