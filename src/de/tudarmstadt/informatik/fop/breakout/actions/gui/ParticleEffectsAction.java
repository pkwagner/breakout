package de.tudarmstadt.informatik.fop.breakout.actions.gui;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

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
