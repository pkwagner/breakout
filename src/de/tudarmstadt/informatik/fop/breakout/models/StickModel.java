package de.tudarmstadt.informatik.fop.breakout.models;

import eea.engine.entity.Entity;

import org.lwjgl.util.vector.Vector;
import org.newdawn.slick.geom.Vector2f;

public class StickModel extends Entity {

    private static final int HEIGHT = 5;

    private Vector velocity;

    public StickModel(String entityID) {
        super(entityID);
    }

    public void setWidth(int width) {
        setSize(new Vector2f(width, HEIGHT));
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
