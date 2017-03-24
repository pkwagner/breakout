package actions.gui;

import constants.GameParameters;
import states.GameplayState;
import ui.Breakout;

/**
 * Toggles particle effects on checkbox change
 */
public class ParticleEffectsAction extends CheckBoxClickAction {

    @Override
    public void onToggle(Breakout game, boolean newVal) {
        GameplayState state = (GameplayState) game.getState(GameParameters.GAMEPLAY_STATE);
        state.setParticleEffectsEnabled(newVal);
    }
}
