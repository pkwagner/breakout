package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SimpleBlockRenderComponent extends AbstractBlockRenderComponent {

    public SimpleBlockRenderComponent(int hits, String id) throws SlickException {
        super(id);
        switch (hits) {
            case 1:
                spritesheet		 = new SpriteSheet(GameParameters.SIMPLE_BLOCK_1_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
                deathSpritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_1_DEATH_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
                break;
            case 2:
                spritesheet		 = new SpriteSheet(GameParameters.SIMPLE_BLOCK_2_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
                deathSpritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_2_DEATH_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
                break;
            case 3:
                spritesheet = new SpriteSheet(GameParameters.SIMPLE_BLOCK_3_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
                break;
        }
    }
}
