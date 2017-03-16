package de.tudarmstadt.informatik.fop.breakout.models.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import eea.engine.entity.Entity;

public abstract class AbstractBlockModel extends Entity implements IHitable {

    private int initialHits = 1;
    private int remainingHits = initialHits;
    GameParameters.ItemType droppableItems[] = {};

    public AbstractBlockModel(String entityID) {
        super(entityID);
    }

    public int getInitialHits() {
        return initialHits;
    }

    void setInitialHits(int initialHits) {
        this.initialHits = initialHits;
        this.remainingHits = initialHits;
    }

    public GameParameters.ItemType[] getDroppableItems() {
        return droppableItems;
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

    public void decreaseRemainingHits(int hits) {
        remainingHits -= (remainingHits > hits) ? hits : remainingHits;
    }


    public abstract GameParameters.BlockType getType();

    public abstract GameParameters.BlockType transformToBlockType();
}
