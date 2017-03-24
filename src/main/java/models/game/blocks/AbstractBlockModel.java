package models.game.blocks;

import constants.GameParameters;
import interfaces.IHitable;
import models.BlockType;
import models.ItemType;
import views.game.blocks.AbstractBlockView;
import eea.engine.entity.Entity;

public abstract class AbstractBlockModel extends Entity implements IHitable {

    private int initialHits = 1;
    private int remainingHits = initialHits;
    private boolean isDestroyed = false;

    private int scorePoints = GameParameters.BLOCK_SCOREPOINTS;
    ItemType droppableItems[] = {};

    private AbstractBlockView view;

    AbstractBlockModel(String entityID) {
        super(entityID);
        setPassable(false);
    }

    public int getInitialHits() {
        return initialHits;
    }

    void setInitialHits(int initialHits) {
        this.initialHits = initialHits;
        this.remainingHits = initialHits;
    }

    public ItemType[] getDroppableItems() {
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

    public void addView(AbstractBlockView view) {
        this.view = view;
    }

    public AbstractBlockView getView() {
        return view;
    }

    public void decreaseRemainingHits(int hits) {
        remainingHits -= (remainingHits > hits) ? hits : remainingHits;
    }

    public abstract BlockType getType();

    public int getScorePoints() {
        return scorePoints;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
