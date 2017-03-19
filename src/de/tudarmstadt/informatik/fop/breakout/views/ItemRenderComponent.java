package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.component.RenderComponent;
import eea.engine.component.render.ImageRenderComponent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ItemRenderComponent extends RenderComponent {
	private GameParameters.ItemType itemType;
	private SpriteSheet spritesheet;
	private Animation animation;
	
    public ItemRenderComponent(String id,GameParameters.ItemType itemType) throws SlickException {
        super(id);
        this.itemType = itemType;
        switch(itemType){
        case Bigger
        
        }
    }

	@Override
	public Vector2f getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
