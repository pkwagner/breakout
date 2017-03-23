package de.tudarmstadt.informatik.fop.breakout.views.game;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.game.ItemModel;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ItemView extends RenderComponent {

	private SpriteSheet spritesheet;
	private Animation animation;
	private ItemModel itemModel;
	private boolean isPaused = false;

    public ItemView(String id, ItemModel itemModel, GameParameters.ItemType itemType) throws SlickException {
        super(id);
        this.itemModel = itemModel;

        Image image = new Image(itemType.getImagePath());
        int imageWidth =  (int)(GameParameters.ITEM_IMAGE_SIZE * image.getHeight());

        spritesheet = new SpriteSheet(image.getScaledCopy(GameParameters.ITEM_IMAGE_SIZE),imageWidth,imageWidth);

        animation 	= new Animation(spritesheet,600/spritesheet.getHorizontalCount());
    }

	@Override
	public Vector2f getSize() {
		int width = spritesheet.getWidth() / spritesheet.getHorizontalCount();
		int height = spritesheet.getHeight();
		return new Vector2f(width, height);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame game, Graphics graphics) {
		Vector2f position = itemModel.getPosition().copy();
		animation.draw(position.getX(), position.getY());
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame game, int delta) {
		if (gameContainer.isPaused() && !isPaused) {
			isPaused = true;
			animation.stop();
		} else if (!gameContainer.isPaused() && isPaused) {
			isPaused = false;
			animation.start();
		}
	}
}
