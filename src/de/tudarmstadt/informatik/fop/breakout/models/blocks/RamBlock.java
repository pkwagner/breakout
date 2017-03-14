package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import de.tudarmstadt.informatik.fop.breakout.controllers.RamBlockMovementController;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class RamBlock extends AbstractBlockModel {

	private GameParameters.Direction direction;
	private int distance;
	RamBlockMovementController rbmc;

	public RamBlock(String entityID, GameParameters.Direction direction, int distance, RamBlockMovementController rbmc) {
		super(entityID);
		this.direction	= direction;
		this.distance	= distance;
		this.rbmc		= rbmc;
	}

	@Override
	public GameParameters.BlockType getType() {
		return GameParameters.BlockType.RAM;
	}

	@Override
	public GameParameters.BlockType transformToBlockType() {
		return null;
	}
	
	public float getRamPosition(){
		return rbmc.getPosition();
	}

	public int getDistance() {
		return distance;
	}

	public GameParameters.Direction getDirection() {
		return direction;
	}

}
