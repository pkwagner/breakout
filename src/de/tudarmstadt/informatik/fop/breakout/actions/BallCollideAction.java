package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.Direction;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Todo write this
 */
public class BallCollideAction implements Action {

    private final BallModel ballModel;

    public BallCollideAction(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        CollisionEvent collisionEvent = (CollisionEvent) component;

        Entity collidedEntity = collisionEvent.getCollidedEntity();
        String collidedId = collidedEntity.getID();

        switch (collidedId) {
            case GameParameters.TOP_BORDER_ID:
                onCollide(collidedEntity.getShape(), Direction.UP);
                break;
                //todo: side collision
            case GameParameters.STICK_ID:
                onCollide(collidedEntity.getShape(), Direction.DOWN);
                break;
            case GameParameters.LEFT_BORDER_ID:
                onCollide(collidedEntity.getShape(), Direction.LEFT);
                break;
            case GameParameters.RIGHT_BORDER_ID:
                onCollide(collidedEntity.getShape(), Direction.RIGHT);
                break;
            default:
                // Check if the collided entity is really a block
                if (collidedEntity instanceof AbstractBlockModel) {
                    AbstractBlockModel blockModel = (AbstractBlockModel) collidedEntity;

                    // NOTE: If the ball bumps against the left/right border of the block, the Y-distance must be equal or lower than the X-distance.
                    //       Otherwise it's the same case with the top/bottom border. It's magic.
                    float ballToBlockDistanceX = ballModel.getPosition().getX() - blockModel.getPosition().getX();
                    float ballToBlockDistanceY = ballModel.getPosition().getY() - blockModel.getPosition().getY();

                    if (Math.abs(ballToBlockDistanceX) > Math.abs(ballToBlockDistanceY)) {
                        // Check against left/right border bump
                        if (ballToBlockDistanceX > 0) {
                            onCollide(collidedEntity.getShape(), Direction.LEFT);
                        } else {
                            onCollide(collidedEntity.getShape(), Direction.RIGHT);
                        }
                    } else {
                        // Seemed to be a bump against the top/bottom block border
                        if (ballToBlockDistanceY > 0) {
                            onCollide(collidedEntity.getShape(), Direction.UP);
                        } else {
                            onCollide(collidedEntity.getShape(), Direction.DOWN);
                        }
                    }
                }
        }
    }

    /**
     * ToDo: write this
     *
     * @param otherShape
     * @param collisionDirection
     */
    private void onCollide(Shape otherShape, Direction collisionDirection) {
        Vector2f postion = ballModel.getPosition();
        Vector2f velocity = ballModel.getVelocity();
        switch (collisionDirection) {
            case UP:
                velocity.set(velocity.getX(), -velocity.getY());
                postion.set(postion.getX(), otherShape.getMaxY() + ballModel.getSize().getY() / 2);
                break;
            case DOWN:
                velocity.set(velocity.getX(), -velocity.getY());
                postion.set(postion.getX(), otherShape.getMinY() - ballModel.getSize().getY() / 2);
                break;
            case LEFT:
                velocity.set(-velocity.getX(), velocity.getY());
                postion.set(otherShape.getMaxX() + ballModel.getSize().getX() / 2, postion.getY());
                break;
            case RIGHT:
                velocity.set(-velocity.getX(), velocity.getY());
                postion.set(otherShape.getMinX() - ballModel.getSize().getX() / 2, postion.getY());
                break;
        }
    }
}
