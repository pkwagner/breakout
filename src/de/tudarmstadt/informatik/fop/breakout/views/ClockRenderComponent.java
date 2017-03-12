package de.tudarmstadt.informatik.fop.breakout.views;

import java.math.BigDecimal;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import eea.engine.component.RenderComponent;

public class ClockRenderComponent extends RenderComponent {

	ClockModel clock;
	
	public ClockRenderComponent(String id) {
		super(id);
	}

    @Override
    public ClockModel getOwnerEntity() {
        return (ClockModel) super.getOwnerEntity();
    }
    
    public void setOwnerEntity(ClockModel owningEntity) {
        super.setOwnerEntity(owningEntity);
    }
    
    public void init(){
    	clock = getOwnerEntity();
    }
	
	@Override
	public Vector2f getSize() {
		return clock.getSize();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) {
		g.drawString(Float.toString(round(clock.getSeconds(),2)), clock.getPosition().getX(),clock.getPosition().getY());
		
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd.floatValue();
    }

}
