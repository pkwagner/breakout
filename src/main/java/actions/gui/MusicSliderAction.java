package actions.gui;

import controllers.SoundController;
import ui.Breakout;

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
