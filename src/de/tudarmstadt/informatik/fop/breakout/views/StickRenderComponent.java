package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Renders the stick including the correct scaled width on bigger/small item pickup
 */
public class StickRenderComponent extends RenderComponent {

    private final Image image;

    public StickRenderComponent() throws SlickException {
        super(GameParameters.STICK_ID + GameParameters.EXT_VIEW);

        this.image = new Image(GameParameters.STICK_IMAGE);
    }

    @Override
    public Vector2f getSize() {
        //the model size will be resized every time so rely on that object
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        // retrieve the underlying entity's position
        Shape ownerShape = owner.getShape();

        // retrieve the underlying entity's size to resize the image
        Vector2f size = owner.getSize();

        //draw from the top left corner with a scaled width
        image.draw(ownerShape.getMinX(), ownerShape.getMinY(), size.getX(), size.getY());
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
