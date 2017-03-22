package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

/**
 * Lets all balls in game move slower on pickup
 */
public class SlowerItemAction extends AbstractItemAction {

    private List<BallModel> allBalls;

    @Override
    protected void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer) {
        // Get all balls from state
        allBalls = ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).getBalls();
    }

    @Override
    public void onEnable() {
        // Slow down all balls
        allBalls.forEach(ball -> {
            Vector2f oldVelocity = ball.getVelocity();
            ball.setVelocity(oldVelocity.scale(GameParameters.ITEM_SLOWER_SPEED_VALUE));
        });
    }

    @Override
    public void onDisable() {
        // Hey, Captain Obvious here. You don't see anything cause it's not a temporary item. Obviously.
    }
}
