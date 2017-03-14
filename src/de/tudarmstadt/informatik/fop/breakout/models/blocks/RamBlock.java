package de.tudarmstadt.informatik.fop.breakout.models.blocks;
import de.tudarmstadt.informatik.fop.breakout.controllers.RamBlockMovementController;
import de.tudarmstadt.informatik.fop.breakout.models.*;

public class RamBlock extends AbstractBlockModel {

	private Direction direction;
	private int distance;
	RamBlockMovementController rbmc;
	
	public RamBlock(String entityID, Direction direction, int distance, RamBlockMovementController rbmc) {
		super(entityID);
		this.direction	= direction;
		this.distance	= distance;
		this.rbmc		= rbmc;
	}

	@Override
	public BlockType getType() {
		return BlockType.RAM;
	}

	@Override
	public BlockType transformToBlockType() {
		return null;
	}
	
	public float getRamPosition(){
		return rbmc.getPosition();
	}

	public int getDistance() {
		return distance;
	}

	public Direction getDirection() {
		return direction;
	}


}
