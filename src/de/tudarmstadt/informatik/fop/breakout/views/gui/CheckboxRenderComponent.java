package de.tudarmstadt.informatik.fop.breakout.views.gui;

import de.tudarmstadt.informatik.fop.breakout.models.gui.Checkbox;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Renders the checkbox as a square and with a X inside if the box is enabled.
 *
 * @see Checkbox
 */
public class CheckboxRenderComponent extends RenderComponent {

    public CheckboxRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        Checkbox checkbox = (Checkbox) getOwnerEntity();
        Shape shape = checkbox.getShape();

        Color oldColor = graphicsContext.getColor();

        graphicsContext.setLineWidth(3);
        graphicsContext.setColor(Color.orange);

        //draw the box
        graphicsContext.drawRect(shape.getMinX(), shape.getMinY(), shape.getWidth(), shape.getHeight());

        //draw the X
        if (checkbox.isEnabled()) {
            //top left corner -> bottom right corner
            graphicsContext.drawLine(shape.getMinX(), shape.getMinY(), shape.getMaxX(), shape.getMaxY());

            //bottom left corner -> top right corner
            graphicsContext.drawLine(shape.getMinX(), shape.getMaxY(), shape.getMaxX(), shape.getMinY());
        }

        //restore the default settings
        graphicsContext.setColor(oldColor);
        graphicsContext.resetLineWidth();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
