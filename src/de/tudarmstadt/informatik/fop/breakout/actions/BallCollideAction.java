package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.Direction;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

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

    public BallCollideAction(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        CollisionEvent collisionEvent = (CollisionEvent) component;

        Entity collidedEntity = collisionEvent.getCollidedEntity();
        String collidedId = collidedEntity.getID();
        if ("pause".equals(collidedId)) {
            return;
        }

        logger.debug("Ball collision with {}", collidedEntity.getID());
        if (collidedEntity instanceof AbstractBlockModel) {
            onBlockCollide(stateBasedGame, (AbstractBlockModel) collidedEntity);
        }

                
        Shape otherShape = collidedEntity.getShape();
        Shape ballShape = ballModel.getShape();

        //boolean collisionValid = checkCollision()
        
        float blockLeftBorder = ballShape.getMaxX() - otherShape.getMinX();
        float blockTopBorder = ballShape.getMaxY() - otherShape.getMinY();

        float blockRightBorder = otherShape.getMaxX() - otherShape.getMinX();
        float blockBottomBorder = otherShape.getMaxY() - otherShape.getMinY();

        float horizontalDiff = ballModel.getVelocity().getY() > 0 ? blockTopBorder : blockBottomBorder;
        float verticalMax = ballModel.getVelocity().getX() > 0 ? blockLeftBorder : blockRightBorder;

        logger.debug(Arrays.toString(ballShape.subtract(otherShape)));
        logger.debug(Arrays.toString(otherShape.subtract(ballShape)));

        logger.debug("===============================");
        logger.debug("BALL " + "X:" + ballShape.getMaxX() + " " + ballShape.getMinX() + "Y:" + ballShape.getMaxY() + " " + ballShape.getMinY());
        logger.debug("OTHER: " + "X:" + otherShape.getMaxX() + " " + otherShape.getMinX() + "Y:" + otherShape.getMaxY() + " " + otherShape.getMinY());
        logger.debug("===============================");
        logger.debug("LEFT:BORDER: " + blockLeftBorder);
        logger.debug("TOP:BORDER: " + blockTopBorder);
        logger.debug("RIGHT:BORDER: " + blockRightBorder);
        logger.debug("BOTTOM:BORDER: " + blockBottomBorder);
        logger.debug("================================");
        logger.debug(horizontalDiff + " ::: " + verticalMax);
        if (horizontalDiff > verticalMax) {
            if (ballModel.getVelocity().getX() < 0) {
                //moves left
                logger.debug("Collision right bounding box");
                onCollide(collidedEntity.getShape(), Direction.LEFT);
            } else {
                //moves right
                logger.debug("Collision left bounding box");
                onCollide(collidedEntity.getShape(), Direction.RIGHT);
            }
        } else {
            if (ballModel.getVelocity().getY() > 0) {
                logger.debug("Collision top bounding box");
                onCollide(collidedEntity.getShape(), Direction.DOWN);
            } else {
                logger.debug("Collision bottom bounding box");
                onCollide(collidedEntity.getShape(), Direction.UP);
            }
        }
    }

    private void onBlockCollide(StateBasedGame stateBasedGame, AbstractBlockModel blockModel) {
        // Decrease remaining blockModel hits, afterwards check for remaining hit points
        blockModel.decreaseRemainingHits(1);
        if (!blockModel.hasHitsLeft()) {
            logger.info("Block {} destroyed on hit", blockModel.getID());

            // Call the state to remove this block
            GameplayState gameplayState = (GameplayState) stateBasedGame.getCurrentState();
            gameplayState.removeEntity(blockModel);
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
 
    private boolean checkCollision(){
    	
    }
}
