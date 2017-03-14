package de.tudarmstadt.informatik.fop.breakout.models.blocks;
import de.tudarmstadt.informatik.fop.breakout.models.*;

public class RamBlock extends AbstractBlockModel {

	private Direction direction;
	private int distance;
	
	public RamBlock(String entityID, Direction direction, int distance) {
		super(entityID);
		this.direction	= direction;
		this.distance	= distance;
	}

	@Override
	public BlockType getType() {
		return BlockType.RAM;
	}

	@Override
	public BlockType transformToBlockType() {
		return null;
	}

}
