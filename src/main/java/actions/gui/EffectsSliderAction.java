package actions.gui;

import controllers.SoundController;
import ui.Breakout;

import org.newdawn.slick.openal.SoundStore;

/**
 * Changes the effects sound volume on slider change
 */
public class EffectsSliderAction extends SliderChangeAction {

    @Override
    protected void onChange(Breakout breakout, float oldValue, float newValue) {
        SoundController soundController = breakout.getSoundController();
        SoundStore soundStore = soundController.getSoundStore();
        soundStore.setSoundVolume(newValue);
    }
}
