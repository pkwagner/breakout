package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;

import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.geom.Vector2f;

/**
 * Lets the ball move slower per second on pickup
 */
public class SlowerItemAction extends AbstractItemAction {

    private final BallModel ball;

    public SlowerItemAction() {
        super();

        ball = (BallModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.BALL_ID);
    }

    @Override
    public void onEnable() {
        Vector2f oldVelocity = ball.getVelocity();
        ball.setVelocity(oldVelocity.scale(GameParameters.ITEM_SLOWER_SPEED_VALUE));
    }

    @Override
    public void onDisable() {

    }
}
