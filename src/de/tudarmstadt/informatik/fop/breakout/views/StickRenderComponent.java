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

    private final Image leftImage;
    private final Image rightImage;
    private final Image middleImage;

    public StickRenderComponent() throws SlickException {
        super(GameParameters.STICK_ID + GameParameters.EXT_VIEW);
        leftImage = new Image(GameParameters.STICK_LEFT_IMAGE);
        middleImage = new Image(GameParameters.STICK_MIDDLE_IMAGE);
        rightImage = new Image(GameParameters.STICK_RIGHT_IMAGE);
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

        int widthOfSide =leftImage.getWidth();
        int widthOfMiddle = (int)owner.getSize().getX() - widthOfSide*2;
        int height = middleImage.getHeight();
        
        
        leftImage.draw(ownerShape.getMinX(), ownerShape.getMinY());
        
        middleImage.draw(ownerShape.getMinX()+widthOfSide, ownerShape.getMinY(),widthOfMiddle,height);
        
        rightImage.draw(ownerShape.getMinX()+widthOfSide+widthOfMiddle, ownerShape.getMinY());
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
