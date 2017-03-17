package de.tudarmstadt.informatik.fop.breakout.models;

import eea.engine.entity.Entity;

import org.newdawn.slick.geom.Vector2f;

public class BallModel extends Entity {

    private Vector2f velocity;
    private float radius = 12.5F;
    private int hitPoints = 1;

    public BallModel(String entityID) {
        super(entityID);
    }

    public float getRadius() {
        return radius;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
}
