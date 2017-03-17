package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.state.StateBasedGame;

public class SmashBallItemAction extends AbstractItemAction {

    private BallModel ball;

    @Override
    protected void init(StateBasedGame stateBasedGame) {
        ball = (BallModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.BALL_ID);
    }

    @Override
    public void onEnable() {
        ball.setSmashMode(true);
    }

    @Override
    public void onDisable() {
        // TODO Check if ball is inside a block
        ball.setSmashMode(false);
    }
}
