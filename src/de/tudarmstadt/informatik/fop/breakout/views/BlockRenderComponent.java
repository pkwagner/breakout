package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.BlockType;
import eea.engine.component.render.ImageRenderComponent;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class BlockRenderComponent extends ImageRenderComponent {

    public BlockRenderComponent(BlockType blockType, Vector2f blockSize) throws SlickException {
        // Uses Math.ceil to avoid black borders around the block if the size is not even (e.g. .3)
        super(new Image(getBlockImage(blockType))
            .getScaledCopy((int) Math.ceil(blockSize.getX()), (int) Math.ceil(blockSize.getY())));
    }

    private static String getBlockImage(BlockType blockType) {
        // TODO Try to replace by switch/case construction
        if (blockType == BlockType.SIMPLE)
            return GameParameters.BLOCK_1_IMAGE;

        return "";
    }
}
