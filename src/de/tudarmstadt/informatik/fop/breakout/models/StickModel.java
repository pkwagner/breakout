package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.entity.Entity;

import org.lwjgl.util.vector.Vector;
import org.newdawn.slick.geom.Vector2f;

public class StickModel extends Entity {

    private Vector velocity;
    private final PlayerModel owner;

    private boolean thrust;

    public StickModel(String stickId, PlayerModel owner) {
        super(stickId);

        this.owner = owner;
        this.setSize(new Vector2f(GameParameters.STICK_WIDTH, GameParameters.STICK_HEIGHT));
        setPassable(false);
    }

    public void setWidth(float width) {
        setSize(new Vector2f(width, GameParameters.STICK_HEIGHT));
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public PlayerModel getOwner() {
        return owner;
    }

    public boolean isThrust() {
        return thrust;
    }

    /**
     * Push more particles out of the particle system if true
     *
     * @param thrust
     */
    public void setThrust(boolean thrust) {
        this.thrust = thrust;
    }
}
