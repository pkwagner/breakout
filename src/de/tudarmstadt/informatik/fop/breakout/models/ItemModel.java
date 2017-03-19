package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;
import org.newdawn.slick.geom.Vector2f;

public class ItemModel extends Entity {
    private float duration = 0;
    private Vector2f velocity;

    public ItemModel(String entityID, float duration) {
        super(entityID);

        // duration=0 means that this item is permanent
        this.duration = duration;
        this.setScale(0.4f);

        velocity = new Vector2f(0, GameParameters.ITEM_FALL_SPEED);
    }

    public float getDuration() {
        return duration;
    }

    public Vector2f getVelocity() {
        return velocity;
    }
}
