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
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Handles ball collision with another entity
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

                    Vector2f ballPos = ballModel.getPosition();
                    Circle ballShape = new Circle(ballPos.getX(), ballPos.getY(), ballModel.getRadius());

                    Shape blockShape = blockModel.getShape();
                    if (!ballShape.intersects(blockShape)) {
                        return;
                    }

                    float blockLeftBorder = ballShape.getMaxX() - blockShape.getMinX();
                    float blockTopBorder = ballShape.getMaxY() - blockShape.getMinY();

                    float blockRightBorder = blockShape.getMaxX() - ballShape.getMinX();
                    float blockBottomBorder = blockShape.getMaxY() - ballShape.getMinY();

                    float horizontalDiff = ballModel.getVelocity().getY() > 0 ? blockTopBorder : blockBottomBorder;
                    float verticalMax = ballModel.getVelocity().getX() > 0 ? blockLeftBorder : blockRightBorder;

                    System.out.println("===============================");
                    System.out.println("BALL " + "X:" + ballShape.getMaxX() + " " + ballShape.getMinX() + "Y:" + ballShape.getMaxY() + " " + ballShape.getMinY());
                    System.out.println("BLOCK: " + "X:" + blockShape.getMaxX() + " " + blockShape.getMinX() + "Y:" + blockShape.getMaxY() + " " + blockShape.getMinY());
                    System.out.println("===============================");
                    System.out.println("LEFT:BORDER: " + blockLeftBorder);
                    System.out.println("TOP:BORDER: " + blockTopBorder);
                    System.out.println("RIGHT:BORDER: " + blockRightBorder);
                    System.out.println("BOTTOM:BORDER: " + blockBottomBorder);
                    System.out.println("================================");
                    System.out.println(horizontalDiff + " ::: " + verticalMax);
                    if (horizontalDiff > verticalMax) {
                        if (ballModel.getVelocity().getX() < 0) {
                            //moves left
                            System.out.println("RIGHT BLOCK BORDER");
                            onCollide(collidedEntity.getShape(), Direction.LEFT);
                        } else {
                            //moves right
                            System.out.println("LEFT BLOCK BORDER");
                            onCollide(collidedEntity.getShape(), Direction.RIGHT);
                        }
                    } else {
                        if (ballModel.getVelocity().getY() > 0) {
                            System.out.println("TOP BLOCK BORDER");
                            onCollide(collidedEntity.getShape(), Direction.DOWN);
                        } else {
                            System.out.println("BOTTOM BLOCK BORDER");
                            onCollide(collidedEntity.getShape(), Direction.UP);
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
