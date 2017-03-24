package de.tudarmstadt.informatik.fop.breakout.models.game.blocks;

import de.tudarmstadt.informatik.fop.breakout.models.BlockType;
import de.tudarmstadt.informatik.fop.breakout.models.ItemType;

import java.util.Arrays;

public class SimpleBlock extends AbstractBlockModel {

    public SimpleBlock(String entityID, int initialHits) {
        super(entityID);
        this.setInitialHits(initialHits);

        // Set all items as default
        droppableItems = Arrays.copyOf(ItemType.values(), ItemType.values().length);
    }

    @Override
    public BlockType getType() {
        return BlockType.SIMPLE;
    }
}
