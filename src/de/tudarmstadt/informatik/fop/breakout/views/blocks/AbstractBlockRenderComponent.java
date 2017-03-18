package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AbstractBlockRenderComponent extends RenderComponent {
    SpriteSheet spritesheet;
    private int hitsTaken = 0;

    public AbstractBlockRenderComponent(String id) throws SlickException {
        super(id);
        spritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_1_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
    }

    public void updateImage(int hitsTaken) {
        this.hitsTaken = hitsTaken;
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
        Vector2f position = getOwnerEntity().getPosition().copy().add(new Vector2f(GameParameters.BLOCK_WIDTH / (-2), GameParameters.BLOCK_HEIGHT / (-2)));
        spritesheet.getSubImage(hitsTaken, 0).draw(position.getX(), position.getY());
    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}
