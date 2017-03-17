package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.controllers.SoundController;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

import org.newdawn.slick.openal.SoundStore;

/**
 * Changes the background music sound volume on slider change
 */
public class MusicSliderAction extends SliderChangeAction {

    @Override
    protected void onChange(Breakout breakout, float oldValue, float newValue) {
        SoundController soundController = breakout.getSoundController();
        SoundStore soundStore = soundController.getSoundStore();
        soundStore.setMusicVolume(newValue);
    }
}
