package com.alexcaranha.jquerybyhumming.mvp;

import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author alexcaranha
 */
public interface IPresenterChild extends IPresenter {
    void update() throws IOException, ClassNotFoundException, WavFileException, UnsupportedAudioFileException;
    void updateButtons();
}