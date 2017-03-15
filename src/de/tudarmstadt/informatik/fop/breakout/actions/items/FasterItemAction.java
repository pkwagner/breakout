package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;

import eea.engine.entity.StateBasedEntityManager;

import org.newdawn.slick.geom.Vector2f;

public class FasterItemAction extends AbstractItemAction {

    private BallModel ball;

    public FasterItemAction() {
        super();

        ball = (BallModel) StateBasedEntityManager.getInstance().getEntity(GameParameters.GAMEPLAY_STATE, GameParameters.BALL_ID);
    }

    @Override
    public void onEnable() {
        Vector2f oldVelocity = ball.getVelocity();
        ball.setVelocity(oldVelocity.scale(GameParameters.ITEM_FASTER_SPEEDUP_VALUE));
    }

    @Override
    public void onDisable() {

    }
}
