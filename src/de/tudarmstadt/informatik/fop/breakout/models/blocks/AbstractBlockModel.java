package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import eea.engine.entity.Entity;

public abstract class AbstractBlockModel extends Entity {

    private int initialHits = 1;
    private int remainingHits = initialHits;

    private float x, y;

    public AbstractBlockModel(String entityID, float x, float y) {
        super(entityID);

        this.x = x;
        this.y = y;
    }

    public int getInitialHits() {
        return initialHits;
    }

    public int getRemainingHits() {
        return remainingHits;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int decreaseRemainingHits(int hits) {
        remainingHits -= (remainingHits > hits) ? hits : remainingHits;
        return remainingHits;
    }


    public abstract BlockType getType();

    public abstract BlockType transformToBlockType();
}
