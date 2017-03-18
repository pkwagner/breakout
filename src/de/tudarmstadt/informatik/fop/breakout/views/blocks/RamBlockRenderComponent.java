package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class RamBlockRenderComponent extends AbstractBlockRenderComponent {

    public RamBlockRenderComponent(String id) throws SlickException {
        super(id);
        spritesheet = new SpriteSheet(GameParameters.RAM_BLOCK_SPRITESHEET, GameParameters.BLOCK_WIDTH, GameParameters.BLOCK_HEIGHT);
    }

}
