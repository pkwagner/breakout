package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import eea.engine.component.RenderComponent;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallRenderComponent extends RenderComponent {
    private SpriteSheet spriteSheet;
    private Animation animation;
    private BallModel ball;
    private boolean isPaused = true;
    
    public BallRenderComponent(String id) throws SlickException {
    	super(id);
    
    	spriteSheet = new SpriteSheet(GameParameters.BALL_SPRITESHEET,50,50);
    	animation = new Animation(spriteSheet,70);
    }

    public void init(){
    	ball = (BallModel) getOwnerEntity();
    	spriteSheet = new SpriteSheet(spriteSheet.getScaledCopy(0.5F),(int)(ball.getRadius()*2),(int)(ball.getRadius()*2));
    	animation = new Animation(spriteSheet,70);
    }
    
	@Override
	public Vector2f getSize() {
		return new Vector2f(ball.getRadius()*2,ball.getRadius()*2);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) {
		if(gameContainer.isPaused() && isPaused == false){
			isPaused = true;
			animation.stop();
		}else if(!gameContainer.isPaused() && isPaused == true){
			isPaused = false;
			animation.start();	
		}			
		
		g.drawAnimation(animation, ball.getPosition().getX()-ball.getRadius(), ball.getPosition().getY()-ball.getRadius());
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
