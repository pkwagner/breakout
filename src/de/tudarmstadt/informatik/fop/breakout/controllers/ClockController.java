package de.tudarmstadt.informatik.fop.breakout.controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import eea.engine.component.Component;

public class ClockController extends Component {

	private ClockModel clock;
	
	public ClockController(String componentID) {
		super(componentID);
	}
    @Override
    public ClockModel getOwnerEntity() {
        return (ClockModel) super.getOwnerEntity();
    }

    public void setOwnerEntity(ClockModel owningEntity) {
        super.setOwnerEntity(owningEntity);
    }
    
    public void init(StateBasedGame stateBasedGame){
    	clock = getOwnerEntity();
    	int xOffset = 50;
    	int yOffset = 20;
    	clock.setPosition(new Vector2f(stateBasedGame.getContainer().getWidth() - xOffset, yOffset));
    }

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
		if(!clock.isPaused()){
			clock.addSeconds(delta/1000F);
		}		
	}

}
