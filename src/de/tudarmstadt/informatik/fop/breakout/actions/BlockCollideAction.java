package de.tudarmstadt.informatik.fop.breakout.actions;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Fired if the block was hit by the ball. This decreases the health of that block and destroyed it, if it has no
 * more hit points left.
 */
public class BlockCollideAction implements Action {

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        AbstractBlockModel blockModel = (AbstractBlockModel) component.getOwnerEntity();

        CollisionEvent collisionEvent = (CollisionEvent) component;
        Entity collidedEntity = collisionEvent.getCollidedEntity();
        if (!GameParameters.BALL_ID.equals(collidedEntity.getID())) {
            //only a ball can destroy a block
            return;
        }

        // Decrease remaining blockModel hits, afterwards check for remaining hit points
        blockModel.decreaseRemainingHits(1);
        if (!blockModel.hasHitsLeft()) {
            // Call the state to remove this block
            GameplayState gameplayState = (GameplayState) stateBasedGame.getCurrentState();
            gameplayState.removeEntity(blockModel);
        }
    }
}
