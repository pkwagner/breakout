package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.SimpleBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SimpleBlockRenderComponent extends BlockImageRenderComponent {

    private final Logger logger = LogManager.getLogger();

    private final SimpleBlock block;
    private int lastHitsLeftValue;

    public SimpleBlockRenderComponent(String id, SimpleBlock block) throws SlickException {
        super(id, new Image(getImagePath(block.getHitsLeft())));

        this.block = block;
    }

    private static String getImagePath(int remainingHits) {
        switch (remainingHits) {
            case 1:
                return GameParameters.BLOCK_1_IMAGE;
            case 2:
                return GameParameters.BLOCK_2_IMAGE;
            case 3:
                return GameParameters.BLOCK_3_IMAGE;
            default:
                return GameParameters.BLOCK_1_IMAGE;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        super.update(gc, sb, delta);

        if (block.getHitsLeft() != lastHitsLeftValue) {
            try {
                updateImage(new Image(getImagePath(block.getHitsLeft())));
            } catch (SlickException e) {
                logger.error("Some error occurred while creating image for block " + block.getID() + ": " + e);
            }

            lastHitsLeftValue = block.getHitsLeft();
        }
    }
}