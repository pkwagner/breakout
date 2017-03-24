package de.tudarmstadt.informatik.fop.breakout.views.game.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import eea.engine.component.RenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class AbstractBlockView extends RenderComponent {

    SpriteSheet spritesheet;
    SpriteSheet deathSpritesheet;
    private int hitsTaken = 0;
    private boolean deathAnimationPlays = false;
    private int currentDeathAnimationTime = 0;

    AbstractBlockView(String id) throws SlickException {
        super(id);
        spritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_1_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
        deathSpritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_1_DEATH_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
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
        Vector2f v = new Vector2f(GameParameters.BLOCK_WIDTH / (-2), GameParameters.BLOCK_HEIGHT / (-2));
        Vector2f position = getOwnerEntity().getPosition().copy().add(v);

        if (deathAnimationPlays) {
            int currentSubImage = (int) (((float) currentDeathAnimationTime / (float) GameParameters.BLOCK_DEATH_ANIMAION_LENGTH) * deathSpritesheet.getHorizontalCount());
            deathSpritesheet.getSubImage(currentSubImage, 0).draw(position.getX(), position.getY());
        } else {
            spritesheet.getSubImage(hitsTaken, 0).draw(position.getX(), position.getY());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame statebasedGame, int delta) {
        if (deathAnimationPlays) {
            currentDeathAnimationTime += delta;
        }

        if (currentDeathAnimationTime >= GameParameters.BLOCK_DEATH_ANIMAION_LENGTH) {
            ((GameplayState) statebasedGame.getCurrentState()).removeEntity(getOwnerEntity());
        }

        if (((AbstractBlockModel) getOwnerEntity()).isDestroyed()) {
            deathAnimationPlays = true;
        }
    }
}
