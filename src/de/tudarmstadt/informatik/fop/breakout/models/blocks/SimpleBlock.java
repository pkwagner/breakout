package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import java.util.Arrays;

public class SimpleBlock extends AbstractBlockModel {

	private int maxHits;
	
    public SimpleBlock(String entityID, int maxHits) {
        super(entityID);
        this.setMaxHits(maxHits);

        // Set all items as default
        droppableItems = Arrays.copyOf(GameParameters.ItemType.values(), GameParameters.ItemType.values().length);
    }

    @Override
    public GameParameters.BlockType getType() {
        return GameParameters.BlockType.SIMPLE;
    }

    @Override
    public GameParameters.BlockType transformToBlockType() {
        return null;
    }

	public int getMaxHits() {
		return maxHits;
	}

	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}
}
