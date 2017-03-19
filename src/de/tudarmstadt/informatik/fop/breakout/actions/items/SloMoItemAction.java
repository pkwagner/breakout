package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import org.newdawn.slick.state.StateBasedGame;

public class SloMoItemAction extends AbstractItemAction {

    private GameplayState gameplayState;
    private Breakout breakout;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        this.gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);
        this.breakout = (Breakout) stateBasedGame;
    }

    @Override
    public void onEnable() {
        gameplayState.setGameSpeedFactor(GameParameters.ITEM_SLOMO_SPEED_FACTOR);
        breakout.getSoundController().setPitch(GameParameters.ITEM_SLOMO_SPEED_FACTOR);
    }

    @Override
    public void onDisable() {
        gameplayState.setGameSpeedFactor(1);
        breakout.getSoundController().setPitch(1f);
    }
}
