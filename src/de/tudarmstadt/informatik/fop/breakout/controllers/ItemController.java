package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ItemController extends Component {

    private final Logger logger = LogManager.getLogger();

    private final GameParameters.ItemType itemType;

    public ItemController(String componentID, GameParameters.ItemType itemType) {
        super(componentID);

        this.itemType = itemType;
    }

    public void init() {
        ItemModel itemModel = (ItemModel) getOwnerEntity();

        //destroyed the entity if the player couldn't pick it up
        LeavingScreenEvent leaveEvent = new LeavingScreenEvent();
        itemModel.addComponent(leaveEvent);
        leaveEvent.addAction(((gc, sb, delta, event) -> {
            StateBasedEntityManager.getInstance().removeEntity(GameParameters.GAMEPLAY_STATE, itemModel);
        }));

        // Add collision event listener
        CollisionEvent collisionEvent = new CollisionEvent();
        try {
            // Fetch the right action handler for this type of block
            collisionEvent.addAction((Action) itemType.getActionHandler().newInstance());
            itemModel.addComponent(collisionEvent);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Some error occurred during the instantiation of the collision event handler of item '" + itemModel.getID() + "': " + e);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        ItemModel item = (ItemModel) getOwnerEntity();

        // Update position
        Vector2f oldPosition = item.getPosition();
        Vector2f newPosition = oldPosition.add(item.getVelocity().copy().scale(delta));
        item.setPosition(newPosition);
    }
}
