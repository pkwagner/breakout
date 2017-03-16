package de.tudarmstadt.informatik.fop.breakout.controllers;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

/**
 * A global controller, which controls the movement of all RamBlocks currently on the map
 * 
 * @author Leon
 *
 */
public class RamBlockMovementController {

	private enum State{FORWARD,BACKWARD}
	
	private float position;
	private float velocity;
	private float reboundVelocity;
	private final float acceleration = GameParameters.RAM_BLOCK_ACCELERATION;;
	
	private boolean rebounded;
	
	private State currentState;	
	
	private int timeElapsed;
	
	public RamBlockMovementController() {
		position = 0;
		velocity = 0;
		reboundVelocity = GameParameters.RAM_BLOCK_REBOUND_VELOCITY;
		rebounded = false;
		
		currentState = State.FORWARD;
		
		timeElapsed = 0; 
	}
	
	public float getPosition(){		
		switch(currentState){
			case FORWARD : return position;
			case BACKWARD: return 1 -position;
			default: return position;
		}		
	}
	
	public void update(int delta){
		if(!rebounded){
			position += velocity;
			velocity += acceleration*delta;
			
			if(position > 1){
				rebounded = true;
				position = 1;
			}
			
		}else{
			if(timeElapsed < GameParameters.RAM_BLOCK_REST_TIME){
				timeElapsed += delta;
				
				
				position -= reboundVelocity;
				reboundVelocity -= acceleration*delta;
				
				if(position > 1) position = 1;
			}else{
				position	= 0;
				velocity	= 0;
				timeElapsed = 0;
				rebounded	= false;
				reboundVelocity = GameParameters.RAM_BLOCK_REBOUND_VELOCITY;
				
				switch(currentState){
					case FORWARD:	currentState = State.BACKWARD;
						break;
					case BACKWARD:	currentState = State.FORWARD;
						break;
				}
			}
		}
	}

}
