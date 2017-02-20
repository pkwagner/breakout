package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import eea.engine.entity.Entity;

public abstract class AbstractBlockModel extends Entity {

    protected int maxHits = 1;
    protected int remHits = maxHits;

    public AbstractBlockModel(String entityID) {
        super(entityID);
    }

    public abstract BlockType getType();

    public abstract BlockType transformToBlockType();
}
