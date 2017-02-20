package de.tudarmstadt.informatik.fop.breakout.models.blocks;

public class SimpleBlock extends AbstractBlockModel {

    public SimpleBlock(String entityID) {
        super(entityID);
    }

    @Override
    public BlockType getType() {
        return BlockType.SIMPLE;
    }

    @Override
    public BlockType transformToBlockType() {
        return null;
    }
}
