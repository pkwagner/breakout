package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class RamBlockRenderComponent extends BlockImageRenderComponent {
    public RamBlockRenderComponent(String id) throws SlickException {
        super(id, new Image(GameParameters.RAM_BLOCK_IMAGE));
    }
}