package de.tudarmstadt.informatik.fop.breakout.models;

import eea.engine.entity.Entity;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class BallModel extends Entity {

    private Vector2f velocity;
    private float radius = 12.5F;
    private int hitPoints = 1;
    private Vector2f[] outline;
    private int numberOfVertices = 30;
    
    public BallModel(String entityID) {
        super(entityID);
        outline = new Vector2f[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i++){
        	double phase = i / numberOfVertices * Math.PI * 2;
        	float x = (float) Math.sin(phase);
        	float y = (float) Math.cos(phase);
        	outline[i] = (new Vector2f(x,y)).normalise().scale(radius);
        }
    }

    public float getRadius() {
        return radius;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public Shape getShape() {
        return new Circle(getPosition().getX(), getPosition().getY(), radius);
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
    
    public Vector2f[] getOutline(){
    	return outline;
    }
}
