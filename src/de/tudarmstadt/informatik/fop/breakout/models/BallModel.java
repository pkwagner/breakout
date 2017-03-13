package de.tudarmstadt.informatik.fop.breakout.models;

import eea.engine.entity.Entity;

import org.newdawn.slick.geom.Vector2f;

public class BallModel extends Entity {

    private Vector2f velocity;
    private int radius = 1;
    private int hitPoints = 1;

    public BallModel(String entityID) {
        super(entityID);
    }

    public int getRadius() {
        return radius;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
}
