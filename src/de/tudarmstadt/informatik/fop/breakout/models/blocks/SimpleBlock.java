package de.tudarmstadt.informatik.fop.breakout.models.blocks;

public class SimpleBlock extends AbstractBlockModel {

	private int maxHits;
	
    public SimpleBlock(String entityID, int maxHits) {
        super(entityID);
        this.setMaxHits(maxHits);
    }

    @Override
    public BlockType getType() {
        return BlockType.SIMPLE;
    }

    @Override
    public BlockType transformToBlockType() {
        return null;
    }

	public int getMaxHits() {
		return maxHits;
	}

	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}
}
