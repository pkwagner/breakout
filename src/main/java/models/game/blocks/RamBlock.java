package models.game.blocks;

import controllers.game.blocks.RamBlockMovementController;
import models.BlockType;
import models.Direction;

public class RamBlock extends AbstractBlockModel {

    private Direction direction;
    private int distance;
    RamBlockMovementController rbmc;

    public RamBlock(String entityID, Direction direction, int distance, RamBlockMovementController rbmc) {
        super(entityID);
        this.direction = direction;
        this.distance = distance;
        this.rbmc = rbmc;
        setInitialHits(2);
    }

    @Override
    public BlockType getType() {
        return BlockType.RAM;
    }

    public float getRamPosition() {
        return rbmc.getPosition();
    }

    public int getDistance() {
        return distance;
    }

    public Direction getDirection() {
        return direction;
    }

}
