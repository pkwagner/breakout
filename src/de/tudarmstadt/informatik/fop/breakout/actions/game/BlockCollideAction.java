package de.tudarmstadt.informatik.fop.breakout.actions.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.ItemController;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.MapController;
import de.tudarmstadt.informatik.fop.breakout.models.ItemType;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.models.game.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.ItemModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.game.ItemView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.SlickException;

import java.util.stream.Stream;

/**
 * Will be called if a ball collides with a block
 */
public class BlockCollideAction {

    private final Logger logger = LogManager.getLogger();

    private final AbstractBlockModel blockModel;
    private final BallModel ballModel;
    private final Breakout breakout;

    /**
     * Will be called if a ball collides with a block
     *
     * @param blockModel the block the ball collided with
     * @param ballModel the ball that collided with the block
     * @param breakout the game instance
     */
    BlockCollideAction(AbstractBlockModel blockModel, BallModel ballModel, Breakout breakout) {
        this.blockModel = blockModel;
        this.ballModel = ballModel;
        this.breakout = breakout;
    }

    /**
     * Will be called when the collision occurs
     */
    public void onCollision() {
        // Decrease remaining block hits by the balls hit points, afterwards check for remaining hit points (and smash mode ;)
        blockModel.decreaseRemainingHits(ballModel.getHitPoints());
        if (!blockModel.hasHitsLeft() || ballModel.isSmashMode()) {
            destroy();
        } else {
            // Change render component based on remaining hits
        	blockModel.getView().updateImage(blockModel.getInitialHits()-blockModel.getHitsLeft());
        }

        // Play hit-stick-sound
        breakout.getSoundController().playEffect(SoundType.BLOCK_HIT);
    }

    /**
     * Will be called if the block ran out of hit points and should been destroyed
     */
    private void destroy() {
        // Get gameplay state
        GameplayState gameplayState = (GameplayState) breakout.getState(GameParameters.GAMEPLAY_STATE);

        // Call the state to remove this block
        blockModel.destroy();

        // Remove block model in smash mode instantly (pass animation) to avoid border collision glitches
        if (ballModel.isSmashMode())
            gameplayState.removeEntity(blockModel);

        // Drop an item if wanted (check total possibility) [possibly not in smash mode to avoid too much items]
        if (!ballModel.isSmashMode() || GameParameters.ITEM_DROP_IN_SMASH_MODE)
            dropItem();

        // Add score points to the ball-controlling player (if there is one)
        PlayerModel player = ballModel.getControllingPlayer();
        player.destroyBlock(blockModel.getScorePoints());

        // Check if this was the last block, then call 'nextLevel' in GameplayState
        MapController mapController = gameplayState.getMapController();
        mapController.removeBlock(blockModel);
        if (mapController.isEmpty())
            gameplayState.nextLevel();
    }

    /**
     * Will be probably called while block destruction and possibly drops an item
     */
    private void dropItem() {
        // Check if an item should be dropped
        if (Math.random() <= GameParameters.ITEM_DROP_POSSIBILITY) {
            // Get gameplay state
            GameplayState gameplayState = (GameplayState) breakout.getState(GameParameters.GAMEPLAY_STATE);

            // Get random item based on each item possibility
            ItemType possibleItemTypes[] = blockModel.getDroppableItems();
            ItemType itemType = getRandomItem(possibleItemTypes);

            // Determinate null pointer exceptions
            if (itemType == null)
                return;

            // Generate item id based on the selected item type
            String itemId = "item_" + itemType.name();

            // Generate item with it's controller and render component (the usual stuff)
            ItemModel item = new ItemModel(itemId, itemType.getDuration());
            ItemController itemController = new ItemController(itemId + GameParameters.EXT_CONTROLLER, itemType, ballModel.getControllingPlayer());
            item.addComponent(itemController);
            itemController.init();
            try {
                item.addComponent(new ItemView(itemId+GameParameters.EXT_VIEW,item,itemType));
                item.setPosition(blockModel.getPosition().copy());
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
    private ItemType getRandomItem(ItemType items[]) {
        // Calculate sum of all given droppable items to norm probability
        double totalPossibility = Stream.of(items)
                .mapToDouble(ItemType::getPossibility)
                .sum();

        // Some fancy stuff to calculate a random item based on each items probability
        double random = Math.random() * totalPossibility;
        double cumulativeProbability = 0.0;
        for (ItemType item : items) {
            cumulativeProbability += item.getPossibility();
            if (random <= cumulativeProbability)
                return item;
        }

        // If this happens, something went terribly wrong...
        return null;
    }
}
