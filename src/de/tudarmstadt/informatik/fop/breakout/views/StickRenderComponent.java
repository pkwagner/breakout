package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.component.render.ImageRenderComponent;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StickRenderComponent extends ImageRenderComponent {

    public StickRenderComponent() throws SlickException {
        super(new Image(GameParameters.STICK_IMAGE));
    }
}
