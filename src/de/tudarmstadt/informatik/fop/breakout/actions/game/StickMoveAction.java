package de.tudarmstadt.informatik.fop.breakout.actions.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.game.StickModel;

import eea.engine.action.Action;
import eea.engine.component.Component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Will be called when the player tries to move the stick
 */
public class StickMoveAction implements Action {

    private final GameParameters.Direction moveDirection;
    private final StickModel stickModel;

    public StickMoveAction(GameParameters.Direction moveDirection, StickModel stickModel) {
        this.moveDirection = moveDirection;
        this.stickModel = stickModel;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        Vector2f frameVelocity = stickModel.getVelocity().copy().scale(delta);
        if (moveDirection == GameParameters.Direction.LEFT)
            frameVelocity.scale(-1);

        Vector2f oldPosition = stickModel.getPosition();
        Vector2f newPosition = oldPosition.add(frameVelocity);

        float boundLeft = stickModel.getSize().getX() / 2;
        float boundRight = gameContainer.getWidth() - stickModel.getSize().getX() / 2;
        if (newPosition.getX() < boundLeft) {
            stickModel.setPosition(new Vector2f(boundLeft, oldPosition.getY()));
        } else if (newPosition.getX() > boundRight) {
            stickModel.setPosition(new Vector2f(boundRight, oldPosition.getY()));
        } else {
            stickModel.setPosition(newPosition);
        }
    }
}
