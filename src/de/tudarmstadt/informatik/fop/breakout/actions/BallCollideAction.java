package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters.Direction;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Arrays;

/**
 * Handles ball collision with another entity
 */
public class BallCollideAction implements Action {

    private final Logger logger = LogManager.getLogger();
    private final BallModel ballModel;

    /**
     * Will be called if the connected ball collides with anything else. Even another ball.
     *
     * @param ballModel the ball that collided with another entity
     */
    public BallCollideAction(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        // Get collision event &
        CollisionEvent collisionEvent = (CollisionEvent) component;
        GameplayState state = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

        // Gain some information about the collided entity
        Entity collidedEntity = collisionEvent.getCollidedEntity();
        String collidedId = collidedEntity.getID();

        // Instantly abort the collision handling if the other item is not passable! Total disaster.
        if (collidedEntity.isPassable()) return;

        logger.debug("Ball collision with {}", collidedId);

        // Do some magical stuff to ensure the collision was real.
        Shape collidedShape = collidedEntity.getShape();
        boolean collisionValid = checkCollision(collidedShape);

        if (collisionValid) {
            handleCollision(collidedEntity, state, (Breakout) stateBasedGame);
            increaseBallSpeed();
        }
    }

    /**
     * Increases the ball speed by a given value. Should be called when a ball hits a block.
     */
    private void increaseBallSpeed() {
        Vector2f oldSpeed = ballModel.getVelocity();

        Vector2f speedup = oldSpeed.copy().normalise().scale(GameParameters.SPEEDUP_VALUE);
        ballModel.setVelocity(oldSpeed.copy().add(speedup));
        logger.debug("Ball speed increases on hit. Old speed {}, New speed: {}", oldSpeed, ballModel.getVelocity());
    }

    /**
     * Handles the collision by updating ball position and velocity
     *
     * @param collidedEntity the entity the ball collided with
     * @param state the gameplay state
     */
    private void handleCollision(Entity collidedEntity, GameplayState state, Breakout breakout) {
        Shape ballShape = ballModel.getShape();
        Shape collidedShape = collidedEntity.getShape();

        // Decide if the collided entity is a stick (multiplayer -> plural) or a block
        if (collidedEntity instanceof StickModel) {
            breakout.getSoundController().playEffect(SoundType.STICK_HIT);

            ((StickModel) collidedEntity).setThrust(true);

            // Set ball's controlling player
            ballModel.setControllingPlayer(((StickModel) collidedEntity).getOwner());
        } else if (collidedEntity instanceof AbstractBlockModel) {
            // Do the fancy stuff an a dedicated function
            onBlockCollide(breakout, (AbstractBlockModel) collidedEntity);
        }

        // The default collision handling method
        updateBallPositionAndVelocity(getCollisionDirection(ballShape, collidedShape), collidedShape, collidedEntity);
    }


    /**
     * The default method for handling a collision by updating the balls position and velocity
     *
     * @param collisionDirection the direction the ball bumped against the entity from
     * @param collidedShape the shape of the entity the ball collided with
     * @param collidedEntity the collided entity itself
     */
    private void updateBallPositionAndVelocity(Direction collisionDirection, Shape collidedShape, Entity collidedEntity) {
        // Abort bumping on blocks if the ball is in smash mode
    	if (ballModel.isSmashMode() && (collidedEntity instanceof AbstractBlockModel)) return;

    	// Obvious.
    	boolean isStick = (collidedEntity instanceof StickModel);

    	// Get current
        Vector2f position = ballModel.getPosition();
        Vector2f velocity = ballModel.getVelocity();

        // Set the ball a little bit back.
        position.add(velocity.copy().scale(-1));

        // Do the fancy stuff.
        switch (collisionDirection) {
            case UP:
                if (isStick)
                    velocity.set(getStickBumpVelocity((StickModel) collidedEntity, velocity));
                else
                    velocity.set(velocity.getX(), -velocity.getY());
                if (checkCollision(collidedShape))
                    position.set(position.getX(), collidedShape.getMinY() - ballModel.getShape().getHeight() / 2 - 1); //it's checked whether the objects are still colliding (due to non ball movement)
                break;
            case DOWN:
                // NOTICE: Downside stick check collision check is only relevant for multiplayer
                if (isStick)
                    velocity.set(getStickBumpVelocity((StickModel) collidedEntity, velocity));
                else
                    velocity.set(velocity.getX(), -velocity.getY());
                if (checkCollision(collidedShape))
                    position.set(position.getX(), collidedShape.getMaxY() + ballModel.getShape().getHeight() / 2 + 1);
                break;
            case LEFT:
                // Stick collisions on the sides should been handled normally to avoid bugs.
                velocity.set(-velocity.getX(), velocity.getY());
                if (checkCollision(collidedShape))
                    position.set(collidedShape.getMinX() - ballModel.getShape().getWidth() / 2 - 1, position.getY());
                break;
            case RIGHT:
                // Stick collisions on the sides should been handled normally to avoid bugs.
                velocity.set(-velocity.getX(), velocity.getY());
                if (checkCollision(collidedShape))
                    position.set(collidedShape.getMaxX() + ballModel.getShape().getWidth() / 2 + 1, position.getY());
                break;
        }
    }


    /**
     * Method for handling collision with blocks specifically
     *
     * @param breakout the game instance
     * @param block the block the ball collided with
     */
    private void onBlockCollide(Breakout breakout, AbstractBlockModel block) {
        // Trigger the specific block action
        new BlockCollideAction(block, ballModel, breakout).onCollision();
    }


    /**
     * Calculates where the collided shape has been hit.
     * (pretty complicated, very math)
     *
     * @param ballShape the shape of the ball
     * @param collidedShape the shape of the collided entity
     * @return the direction the has been hit
     */
    private Direction getCollisionDirection(Shape ballShape, Shape collidedShape) {
        // Define block borders
        float lBorder = collidedShape.getMinX() - ballShape.getWidth() / 2;
        float tBorder = collidedShape.getMinY() - ballShape.getHeight() / 2;
        float rBorder = collidedShape.getMaxX() + ballShape.getWidth() / 2;
        float bBorder = collidedShape.getMaxY() + ballShape.getHeight() / 2;

        // Re-calculate ball attributes
        Vector2f position = ballModel.getPosition().copy();
        Vector2f velocity = ballModel.getVelocity().copy();
        // Adding the velocity is necessary because the ball was set back a few lines above
        Vector2f oldPosition = position.copy().add(velocity.copy().scale(-1));
        Vector2f direction = velocity.copy().normalise();

        float lScalingDistance = (lBorder - oldPosition.getX()) / direction.getX();
        Vector2f positionOnLeftBorder = oldPosition.copy().add(direction.copy().scale(lScalingDistance));

        float rScalingDistance = (rBorder - oldPosition.getX()) / direction.getX();
        Vector2f positionOnRightBorder = oldPosition.copy().add(direction.copy().scale(rScalingDistance));

        float tScalingDistance = (tBorder - oldPosition.getY()) / direction.getY();
        Vector2f positionOnTopBorder = oldPosition.copy().add(direction.copy().scale(tScalingDistance));

        float bScalingDistance = (bBorder - oldPosition.getY()) / direction.getY();
        Vector2f positionOnBottomBorder = oldPosition.copy().add(direction.copy().scale(bScalingDistance));


        logger.debug("===========================================================");
        logger.debug(position.toString());
        logger.debug("l: " + lBorder + " r: " + rBorder + " t: " + tBorder + " b:" + bBorder);
        logger.debug("-----------------------------------------------------------");
        logger.debug(positionOnLeftBorder.toString() + oldPosition.distance(positionOnLeftBorder));
        logger.debug(positionOnRightBorder.toString() + oldPosition.distance(positionOnRightBorder));
        logger.debug(positionOnTopBorder.toString() + oldPosition.distance(positionOnTopBorder));
        logger.debug(positionOnBottomBorder.toString() + oldPosition.distance(positionOnBottomBorder));


        boolean[] isBorder = {true, true, true, true}; //left, right, bottom, top

        if (positionOnLeftBorder.getY() > bBorder || positionOnLeftBorder.getY() < tBorder) isBorder[0] = false;
        if (positionOnRightBorder.getY() > bBorder || positionOnRightBorder.getY() < tBorder) isBorder[1] = false;
        if (positionOnTopBorder.getX() > rBorder || positionOnTopBorder.getX() < lBorder) isBorder[2] = false;
        if (positionOnBottomBorder.getX() > rBorder || positionOnBottomBorder.getX() < lBorder) isBorder[3] = false;

        logger.debug(Arrays.toString(isBorder));

        Direction reboundDirection = null;

        float currentMinDistance = Float.POSITIVE_INFINITY;

        if (isBorder[0])
            if (oldPosition.distance(positionOnLeftBorder) < currentMinDistance) {
                reboundDirection = Direction.LEFT;
                currentMinDistance = oldPosition.distance(positionOnLeftBorder);
            }

        if (isBorder[1])
            if (oldPosition.distance(positionOnRightBorder) < currentMinDistance) {
                reboundDirection = Direction.RIGHT;
                currentMinDistance = oldPosition.distance(positionOnRightBorder);
            }

        if (isBorder[2])
            if (oldPosition.distance(positionOnTopBorder) < currentMinDistance) {
                reboundDirection = Direction.UP;
                currentMinDistance = oldPosition.distance(positionOnTopBorder);
            }

        if (isBorder[3])
            if (oldPosition.distance(positionOnBottomBorder) < currentMinDistance) {
                reboundDirection = Direction.DOWN;
                currentMinDistance = oldPosition.distance(positionOnBottomBorder);
            }

        if (reboundDirection == null) {
            logger.error("Something went wrong during calculation of the rebound direction");
            reboundDirection = Direction.LEFT;
        }

        logger.debug(reboundDirection);
        return reboundDirection;
    }

    /**
     * Checks whether something has actually collided with the ball by checking
     * if any of the balls outline points are contained within the shape
     *
     * @param collidedShape the shape of the collided entity
     * @return true if the collision was ok; else false
     */
    private boolean checkCollision(Shape collidedShape) {
        Vector2f[] outline = ballModel.getOutline();
        Vector2f position = ballModel.getPosition();

        for (Vector2f outlinePoint : outline) {
            Vector2f absolutePosition = outlinePoint.copy().add(position);
            if (absolutePosition.getX() <= collidedShape.getMaxX() &&
                    absolutePosition.getX() >= collidedShape.getMinX() &&
                    absolutePosition.getY() <= collidedShape.getMaxY() &&
                    absolutePosition.getY() >= collidedShape.getMinY()) return true;
        }

        return false;
    }


    /**
     * Returns a velocity vector the ball should have after bumping against a stick entity
     *
     * @param stick the stick the ball collided with
     * @param previousVelocity the velocity the ball had before bumping against the stick
     * @return the new vector the ball should have
     */
    private Vector2f getStickBumpVelocity(StickModel stick, Vector2f previousVelocity) {
        // Check position on stick
        float stickWidth = stick.getSize().getX();
        // NOTICE: FLOAT BETWEEN -1 (LEFT STICK SIDE) AND 1 (RIGHT STICK SIDE)
        float positionOnStick = (2 * (ballModel.getPosition().getX() - (stick.getPosition().getX() - (stickWidth / 2))) / stickWidth) - 1;

        // Invert position on stick for the opposite (player2)
        if (stick.getOwner().isSecondPlayer())
            positionOnStick *= -1;

        // Theta is the anti-clockwise vector-movement in this example
        // NOTICE: Clockwise tilt: theta=(360-tilt); anti-clockwise tilt: theta=tilt
        double theta = (positionOnStick < 0) ? (positionOnStick * -GameParameters.STICK_MAX_BALL_THETA) : (360 - (positionOnStick * GameParameters.STICK_MAX_BALL_THETA));
        logger.debug("Ball hit stick of player {} at relative position {} (calculated theta: {})", stick.getOwner().getDisplayName(), positionOnStick, theta);

        // Calculate the new ball velocity with theta
        Vector2f newVelocity = new Vector2f(previousVelocity.getX(), -previousVelocity.getY()).sub(theta);

        logger.debug("Changed new ball velocity to {}", newVelocity);
        return newVelocity;
    }
}
