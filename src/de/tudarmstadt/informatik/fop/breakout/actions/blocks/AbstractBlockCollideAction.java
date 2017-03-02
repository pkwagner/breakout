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
        // TODO Calculate correct angle!!!
        /*float blockY = blockModel.getPosition().getY();
        float blockBottomY = blockY + blockModel.getSize().getY() / 2;
        float blockTopY = blockY - blockModel.getSize().getY() / 2;

        if (blockY <= blockTopY || blockY >= blockBottomY)
            return new Vector2f(previousVelocity.getX(), -previousVelocity.getY());*/

        return new Vector2f(previousVelocity.getX(), -previousVelocity.getY());
    }
}
