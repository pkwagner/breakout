package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.controllers.ItemTimer;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.event.basicevents.CollisionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractItemAction implements Action {

    protected Logger logger = LogManager.getLogger();

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        CollisionEvent collisionEvent = (CollisionEvent) component;

        ItemModel item = (ItemModel) collisionEvent.getOwnerEntity();

        // Check if the ball collided with the stick block
        if (collisionEvent.getCollidedEntity().getID().equals(GameParameters.STICK_ID)) {
            logger.info("Item pickup {}", component.getOwnerEntity().getID());

            GameplayState gameplayState = (GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE);

            // Play sound
            gameplayState.getSoundController().playEffect(SoundType.ITEM_PICKUP);

            // Trigger onEnable listener
            onEnable();

            logger.info("Item pickup {}", item.getID());

            // Remove item from state
            ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).removeEntity(collisionEvent.getOwnerEntity());

            // TODO Select another item as counter entity (?)
            // If this is an temporary item, add an item timer to disable it later
            if (item.getDuration() != 0)
                collisionEvent.getCollidedEntity().addComponent(new ItemTimer(item.getID() + "_timer", item.getDuration(), this));
        }
    }

    /**
     * Will be triggered when an item was fetched by the stick
     */
    public abstract void onEnable();

    /**
     * Will be triggered if the booster runs out of time (only for temporarily items / duration != 0)
     * NOTICE: In most cases this function should undo the changes made in onEnable()
     */
    public abstract void onDisable();
}
