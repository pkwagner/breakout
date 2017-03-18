package de.tudarmstadt.informatik.fop.breakout.models;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;

import org.lwjgl.util.vector.Vector;
import org.newdawn.slick.geom.Vector2f;

public class StickModel extends Entity {

    private Vector velocity;
    private final PlayerModel owner;

    public StickModel(String entityID, PlayerModel owner) {
        super(entityID);

        this.owner = owner;
        this.setSize(new Vector2f(GameParameters.STICK_WIDTH, GameParameters.STICK_HEIGHT));
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
}
