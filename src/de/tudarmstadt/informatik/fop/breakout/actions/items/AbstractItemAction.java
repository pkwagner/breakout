package de.tudarmstadt.informatik.fop.breakout.actions.items;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.Timeout;
import de.tudarmstadt.informatik.fop.breakout.models.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;

import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.event.basicevents.CollisionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractItemAction implements Action {

    private Logger logger = LogManager.getLogger();

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta, Component component) {
        CollisionEvent collisionEvent = (CollisionEvent) component;

        // Check if the ball collided with a stick
        if (collisionEvent.getCollidedEntity() instanceof StickModel) {
            logger.info("Item pickup {}", component.getOwnerEntity().getID());

            // Get item & player who caught the item
            ItemModel item = (ItemModel) collisionEvent.getOwnerEntity();
            PlayerModel catchingPlayer = ((StickModel) collisionEvent.getCollidedEntity()).getOwner();

            // Initialize with catching player
            this.init(stateBasedGame, catchingPlayer);

            // Play sound
            ((Breakout) stateBasedGame).getSoundController().playEffect(SoundType.ITEM_PICKUP);

            // Trigger onEnable listener
            onEnable();

            logger.info("Item pickup {}", item.getID());

            // Remove item from state
            ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE)).removeEntity(collisionEvent.getOwnerEntity());

            // If this is an temporary item, add an item timer to disable it later
            if (item.getDuration() != 0) {
                ((GameplayState) stateBasedGame.getState(GameParameters.GAMEPLAY_STATE))
                        .getClockController()
                        .addTimeout(new Timeout(item.getDuration(), wakeupTime -> onDisable()));
            }
        }
    }

    protected abstract void init(StateBasedGame stateBasedGame, PlayerModel catchingPlayer);

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
