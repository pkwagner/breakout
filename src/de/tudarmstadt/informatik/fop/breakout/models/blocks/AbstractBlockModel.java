package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import eea.engine.entity.Entity;

public abstract class AbstractBlockModel extends Entity implements IHitable {

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

    @Override
    public void setHitsLeft(int hitsLeft) {
        remainingHits = hitsLeft;
    }

    @Override
    public int getHitsLeft() {
        return remainingHits;
    }

    @Override
    public void addHitsLeft(int additionalHitsLeft) {
        remainingHits += additionalHitsLeft;
    }

    @Override
    public boolean hasHitsLeft() {
        return (remainingHits > 0);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void decreaseRemainingHits(int hits) {
        remainingHits -= (remainingHits > hits) ? hits : remainingHits;
    }


    public abstract BlockType getType();

    public abstract BlockType transformToBlockType();
}
