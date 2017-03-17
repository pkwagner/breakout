package de.tudarmstadt.informatik.fop.breakout.views.gui;

import de.tudarmstadt.informatik.fop.breakout.models.gui.Slider;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Draws the slider component. A filled circle located on a line.
 *
 * @see Slider
 */
public class SliderRenderComponent extends RenderComponent {

    private static final int SLIDER_CIRCLE_RADIUS = 15;
    private static final int SLIDE_BOX_HEIGHT = 10;

    public SliderRenderComponent() {
        super("slider_view");
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        Slider slider = (Slider) getOwnerEntity();
        Shape ownerShape = getOwnerEntity().getShape();

        Color oldColor = graphicsContext.getColor();

        //draw the line first in order to let the circle overdraw it
        drawLine(graphicsContext, ownerShape);
        drawCircle(graphicsContext, slider.getValue(), ownerShape);

        graphicsContext.setColor(oldColor);
    }

    /**
     * Draw a thick line
     *
     * @param graphicsContext where to draw to
     * @param shape the boundingbox of the owning entity
     */
    private void drawLine(Graphics graphicsContext, Shape shape) {
        graphicsContext.setColor(Color.gray);

        float startY = shape.getCenterY() - SLIDE_BOX_HEIGHT / 2;
        Shape rect = new Rectangle(shape.getMinX(), startY, shape.getWidth(), SLIDE_BOX_HEIGHT);
        graphicsContext.fill(rect);
    }

    /**
     * Draw a filled circle at the correct percent position
     *
     * @param graphicsContext where to draw to
     * @param percent the percentual value of the slider
     * @param ownerShape the boundingbox of the owning entity
     */
    private void drawCircle(Graphics graphicsContext, float percent, Shape ownerShape) {
        graphicsContext.setColor(Color.orange);

        float percentPosition = ownerShape.getWidth() * percent;
        float startX = ownerShape.getMinX() + percentPosition;
        Circle shape = new Circle(startX, ownerShape.getCenterY(), SLIDER_CIRCLE_RADIUS);
        graphicsContext.fill(shape);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
