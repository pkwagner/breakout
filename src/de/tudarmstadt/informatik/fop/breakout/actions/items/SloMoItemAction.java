package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import org.newdawn.slick.state.StateBasedGame;

public class SloMoItemAction extends AbstractItemAction {

    private GameplayState gameplayState;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        this.gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);
    }

    @Override
    public void onEnable() {
        gameplayState.setGameSpeedFactor(GameParameters.ITEM_SLOMO_SPEED_FACTOR);
    }

    @Override
    public void onDisable() {
        gameplayState.setGameSpeedFactor(1);
    }
}
