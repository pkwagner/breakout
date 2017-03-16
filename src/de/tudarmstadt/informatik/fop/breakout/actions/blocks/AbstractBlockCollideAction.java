package de.tudarmstadt.informatik.fop.breakout.actions.blocks;

import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractBlockCollideAction {

    private AbstractBlockModel blockModel;
    private BallModel ballModel;
    private GameplayState gameplayState;

    public AbstractBlockCollideAction(AbstractBlockModel blockModel, BallModel ballModel, GameplayState gameplayState) {
        this.blockModel = blockModel;
        this.ballModel = ballModel;
        this.gameplayState = gameplayState;
    }

    public void onCollision() {
        // Decrease remaining block hits by the balls hit points, afterwards check for remaining hit points
        blockModel.decreaseRemainingHits(ballModel.getHitPoints());
        if (!blockModel.hasHitsLeft())
            destroy();
    }

    private void destroy() {
        // Call the state to remove this block
        gameplayState.removeEntity(blockModel);
    }

    public Vector2f calculateCollisionVelocity(Vector2f previousVelocity) {
        // Calculate ball to block distance
        // NOTE: If the ball bumps against the left/right border of the block, the Y-distance must be equal or lower than the X-distance.
        //       Otherwise it's the same case with the top/bottom border. It's magic.
        float ballToBlockDistanceX = Math.abs(ballModel.getPosition().getX() - blockModel.getPosition().getX());
        float ballToBlockDistanceY = Math.abs(ballModel.getPosition().getY() - blockModel.getPosition().getY());

        // Check against left/right border bump
        if (ballToBlockDistanceX > ballToBlockDistanceY)
            return new Vector2f(-previousVelocity.getX(), previousVelocity.getY());

        // Seemed to be a bump against the top/bottom block border
        return new Vector2f(previousVelocity.getX(), -previousVelocity.getY());
    }
}
