package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.ItemModel;
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
	private ItemModel itemModel;
	private boolean isPaused = false;
	
    public ItemRenderComponent(String id,ItemModel itemModel,GameParameters.ItemType itemType) throws SlickException {
        super(id);
        this.itemModel = itemModel;
        
        Image image = new Image(itemType.getImagePath());
        int imageWidth =  (int)(GameParameters.ITEM_IMAGE_SIZE * image.getHeight());
        
        if(itemType == GameParameters.ItemType.BiggerItem||itemType == GameParameters.ItemType.SmallerItem||itemType == GameParameters.ItemType.AdditionalBallItem||itemType == GameParameters.ItemType.HealthPointItem||itemType == GameParameters.ItemType.SlowerItem||itemType == GameParameters.ItemType.FasterItem){        	
        	spritesheet = new SpriteSheet(image.getScaledCopy(GameParameters.ITEM_IMAGE_SIZE),imageWidth,imageWidth);
        }else{
        	spritesheet = new SpriteSheet(image.getScaledCopy(GameParameters.ITEM_IMAGE_SIZE),(int)(image.getWidth()*GameParameters.ITEM_IMAGE_SIZE),(int)(image.getHeight()*GameParameters.ITEM_IMAGE_SIZE));
        }
        
        animation 	= new Animation(spritesheet,600/spritesheet.getHorizontalCount()); 
    }

	@Override
	public Vector2f getSize() {
		int width = spritesheet.getWidth() /spritesheet.getHorizontalCount();
		int height = spritesheet.getHeight();
		return new Vector2f(width,height);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame arg1, Graphics arg2) {
		Vector2f position = itemModel.getPosition().copy();
		if(gameContainer.isPaused() && isPaused == false){
			isPaused = true;
			animation.stop();
		}else if(!gameContainer.isPaused() && isPaused == true){
			isPaused = false;
			animation.start();	
		}
		animation.draw(position.getX(),position.getY());		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
	}
}
