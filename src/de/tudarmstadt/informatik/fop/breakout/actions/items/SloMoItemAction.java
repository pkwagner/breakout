package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Slow down the whole game. Even the music pitches down slowly. It's insane.
 */
public class SloMoItemAction extends AbstractItemAction {

    private GameplayState gameplayState;

    @Override
    protected void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer) {
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
