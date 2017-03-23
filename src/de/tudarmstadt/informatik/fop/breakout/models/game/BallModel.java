package de.tudarmstadt.informatik.fop.breakout.models.game;

import de.tudarmstadt.informatik.fop.breakout.util.Utility;
import eea.engine.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class BallModel extends Entity {

	private static Logger logger = LogManager.getLogger();

    private Vector2f velocity;
    private float radius = 12.5F;
    private int hitPoints = 1;
    private Vector2f[] outline;
    private int numberOfVertices = 30;

    private PlayerModel controllingPlayer;
    private boolean smashMode = false;

    public BallModel(String entityID, PlayerModel initialControllingPlayer) {
        super(entityID);

        this.controllingPlayer = initialControllingPlayer;

        outline = new Vector2f[numberOfVertices];

        for(int i = 0; i < numberOfVertices; i++){
        	double phase =  Utility.map((float)i, 0.0F,(float) numberOfVertices, 0.0F,(float) Math.PI * 2);
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

    public void setVelocityRotation(float angle){
    	float angleRad = Utility.map(angle,0,360,0,(float) Math.PI*2);

    	float x = (float)Math.sin(angleRad);
    	float y = (float)-Math.cos(angleRad);
    	velocity = new Vector2f(x,y).scale(0.5F);
    	logger.debug("angle" + angle);
    	logger.debug( "x" + Utility.round(x,2) + "y: " + Utility.round(y,2));
    }

    public void step(){
    	getPosition().add(velocity);
    }

    public void stepBackwards(){
    	getPosition().add(velocity.copy().scale(-1));
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

    public Vector2f[] getOutline() {
        return outline;
    }

    public boolean isSmashMode() {
        return smashMode;
    }

    public void setSmashMode(boolean smashMode) {
        this.smashMode = smashMode;
    }

    public PlayerModel getControllingPlayer() {
        return controllingPlayer;
    }

    public void setControllingPlayer(PlayerModel controllingPlayer) {
        this.controllingPlayer = controllingPlayer;
    }
}
