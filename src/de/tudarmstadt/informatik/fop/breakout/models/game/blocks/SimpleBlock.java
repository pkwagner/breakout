package de.tudarmstadt.informatik.fop.breakout.models.game.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import java.util.Arrays;

public class SimpleBlock extends AbstractBlockModel {

    public SimpleBlock(String entityID, int initialHits) {
        super(entityID);
        this.setInitialHits(initialHits);

        // Set all items as default
        droppableItems = Arrays.copyOf(GameParameters.ItemType.values(), GameParameters.ItemType.values().length);
    }

    @Override
    public GameParameters.BlockType getType() {
        return GameParameters.BlockType.SIMPLE;
    }
}
