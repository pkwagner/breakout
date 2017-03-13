package de.tudarmstadt.informatik.fop.breakout.views.blocks;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.component.render.ImageRenderComponent;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AbstractBlockRenderComponent extends ImageRenderComponent {
    public AbstractBlockRenderComponent(Image image) throws SlickException {
        super(image);
    }
}
