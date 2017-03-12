package de.tudarmstadt.informatik.fop.breakout.models.blocks;

public class SimpleBlock extends AbstractBlockModel {

	private int dingens;
	
    public SimpleBlock(String entityID, int dingens) {
        super(entityID);
        this.setDingens(dingens);
    }

    @Override
    public BlockType getType() {
        return BlockType.SIMPLE;
    }

    @Override
    public BlockType transformToBlockType() {
        return null;
    }

	public int getDingens() {
		return dingens;
	}

	public void setDingens(int dingens) {
		this.dingens = dingens;
	}
}
