package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import eea.engine.component.render.ImageRenderComponent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BallRenderComponent extends ImageRenderComponent {

    public BallRenderComponent() throws SlickException {
        super(new Image(GameParameters.BALL_IMAGE));
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
        super.render(gc, sb, g);

        BallModel ball = (BallModel) getOwnerEntity();
        Vector2f[] outline = ball.getOutline();
        //g.draw(getOwnerEntity().getShape());
    
       	float x = ball.getPosition().getX();
       	float y = ball.getPosition().getY();
        for(int i = 0; i < outline.length;i++){
        	if(i < outline.length -1){
        		g.drawLine(outline[i].getX()+x,outline[i].getY()+y,outline[i+1].getX()+x,outline[i+1].getY()+y);
        	}else{
        		g.drawLine(outline[i].getX()+x,outline[i].getY()+y,outline[0].getX()+x,outline[0].getY()+y);
        	}
        	
        	/*
        	g.setColor(new Color(255,0,0));
        	g.drawRect(outline[i].getX()+x, outline[i].getY()+y, 1, 1);
        	g.setColor(new Color(255,255,255));
        	*/
        }
    }
}
