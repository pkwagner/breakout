package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.BlockType;
import eea.engine.component.render.ImageRenderComponent;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BlockRenderComponent extends ImageRenderComponent {

    public BlockRenderComponent(BlockType blockType) throws SlickException {
        super(new Image(getBlockImage(blockType)));
    }

    private static String getBlockImage(BlockType blockType) {
        // TODO Try to replace by switch/case construction
        if (blockType == BlockType.SIMPLE)
            return GameParameters.BLOCK_1_IMAGE;

        return "";
    }
}
