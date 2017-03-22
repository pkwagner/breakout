package de.tudarmstadt.informatik.fop.breakout.views.gui;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Draws a vertical line between two KeyBindings to group them
 */
public class SeparatorRenderComponent extends RenderComponent {

    public SeparatorRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        graphicsContext.setLineWidth(3);

        Shape shape = getOwnerEntity().getShape();
        graphicsContext.drawLine(shape.getCenterX(), shape.getMinY(), shape.getCenterX(), shape.getMaxY());

        //restore old line width
        graphicsContext.resetLineWidth();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
