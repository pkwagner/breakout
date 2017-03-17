package de.tudarmstadt.informatik.fop.breakout.actions.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.ItemController;
import de.tudarmstadt.informatik.fop.breakout.controllers.blocks.AbstractBlockController;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.views.ItemRenderComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.stream.Stream;

public abstract class AbstractBlockCollideAction {

    private final Logger logger = LogManager.getLogger();

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
        else {
            // Change render component based on remaining hits
            blockModel.removeComponent("ImageRenderComponent");
            blockModel.addComponent(AbstractBlockController.createBlockView(blockModel));
        }
        gameplayState.getSoundController().playEffect(SoundType.BLOCK_HIT);
    }

    private void destroy() {
        // Call the state to remove this block
        gameplayState.removeEntity(blockModel);

        // Drop an item if wanted (check total possibility)
        dropItem();
    }

    private void dropItem() {
        // Check if an item should be dropped
        if (Math.random() <= GameParameters.ITEM_DROP_POSSIBILITY) {
            // Get random item based on each item possibility
            GameParameters.ItemType possibleItemTypes[] = blockModel.getDroppableItems();
            GameParameters.ItemType itemType = getRandomItem(possibleItemTypes);

            // Determinate null pointer exceptions
            if (itemType == null)
                return;

            // Generate item id based on the selected item type
            // TODO ID may not be unique
            String itemId = "item_" + itemType.name();

            // Generate item with it's controller and render component
            ItemModel item = new ItemModel(itemId, itemType.getDuration());
            ItemController itemController = new ItemController(itemId + GameParameters.EXT_CONTROLLER, itemType);
            item.addComponent(itemController);
            itemController.init();
            try {
                item.addComponent(new ItemRenderComponent(itemType));
                item.setPosition(blockModel.getPosition());
                gameplayState.addEntity(item);
            } catch (SlickException e) {
                logger.error("Some error occurred during adding the render component of item '" + itemId + "': " + e);
            }
        }
    }

    /**
     * Returns a random item out of a given list based on their probability
     *
     * @param items a list of ItemTypes
     * @return an item out of the given list
     */
    private GameParameters.ItemType getRandomItem(GameParameters.ItemType items[]) {
        // Calculate sum of all given droppable items to norm probability
        double totalPossibility = Stream.of(items)
                .mapToDouble(GameParameters.ItemType::getPossibility)
                .sum();

        // Some fancy stuff to calculate a random item based on each items probability
        double random = Math.random() * totalPossibility;
        double cumulativeProbability = 0.0;
        for (GameParameters.ItemType item : items) {
            cumulativeProbability += item.getPossibility();
            if (random <= cumulativeProbability)
                return item;
        }

        // If this happens, something went terribly wrong...
        return null;
    }
}
