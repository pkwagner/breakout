package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AdditionalBallItemAction extends AbstractItemAction {

    private StateBasedGame stateBasedGame;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        this.stateBasedGame = stateBasedGame;
    }

    @Override
    public void onEnable() {
        try {
            // TODO Change default position
            ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).addBall(stateBasedGame, new Vector2f(100, 100));
        } catch (SlickException e) {
            logger.error("Some error occurred while adding an additional ball to game: " + e);
        }
    }

    @Override
    public void onDisable() {

    }
}
