package models.game;

import org.newdawn.slick.geom.Vector2f;

import constants.GameParameters;
import eea.engine.entity.Entity;

public class ClockModel extends Entity {

    private float seconds = 0;

    public ClockModel(String entityID) {
        super(entityID);
    }

    public float getSeconds() {
        return seconds;
    }

    public void addSeconds(float s) {
        seconds += s;
    }

    public Vector2f getSize() {
        return new Vector2f(GameParameters.STOP_WATCH_WIDTH, GameParameters.STOP_WATCH_HEIGHT);
    }

}
