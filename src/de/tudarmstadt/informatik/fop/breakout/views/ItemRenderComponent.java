package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.blocks.BlockType;
import eea.engine.component.render.ImageRenderComponent;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class ItemRenderComponent extends ImageRenderComponent {

    public ItemRenderComponent(GameParameters.ItemType itemType) throws SlickException {
        super(new Image(itemType.getImagePath()));
    }
}
