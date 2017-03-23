package de.tudarmstadt.informatik.fop.breakout.models.game;

import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;

public class ClockModel extends Entity {

	private float seconds = 0;
	private boolean isPaused = false;

	public ClockModel(String entityID) {
		super(entityID);
	}

	public float getSeconds() {
		return seconds;
	}

	public void addSeconds(float s){
		seconds += s;
	}

	public void pause(){
		isPaused = true;
	}

	public void unpause(){
		isPaused = false;
	}

	public boolean isPaused(){
		return isPaused;
	}

	public Vector2f getSize(){
		return new Vector2f(GameParameters.STOP_WATCH_WIDTH, GameParameters.STOP_WATCH_HEIGHT);
	}

}
