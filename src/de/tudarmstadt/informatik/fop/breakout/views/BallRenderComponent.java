package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.component.render.ImageRenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BallRenderComponent extends ImageRenderComponent {

    public BallRenderComponent() throws SlickException {
        super(new Image(GameParameters.BALL_IMAGE));
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        super.render(gc, sb, graphicsContext);

        graphicsContext.draw(getOwnerEntity().getShape());
    }
}
