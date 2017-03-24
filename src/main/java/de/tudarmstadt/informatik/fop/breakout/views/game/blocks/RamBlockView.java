package de.tudarmstadt.informatik.fop.breakout.views.game.blocks;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class RamBlockView extends AbstractBlockView {

    public RamBlockView(String id) throws SlickException {
        super(id);
        spritesheet = new SpriteSheet(GameParameters.RAM_BLOCK_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
        deathSpritesheet = new SpriteSheet(GameParameters.RAM_BLOCK_DEATH_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
    }

}
