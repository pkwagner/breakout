package de.tudarmstadt.informatik.fop.breakout.views.gui;

import de.tudarmstadt.informatik.fop.breakout.models.gui.Slider;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Draws the slider component. A filled circle located on a line.
 *
 * @see Slider
 */
public class SliderView extends RenderComponent {

    private static final int SLIDER_CIRCLE_RADIUS = 15;
    private static final int SLIDER_CIRCLE_STROKE_THICKNESS = 4;
    private static final int SLIDE_BOX_HEIGHT = 5;

    public SliderView() {
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
     * @param g where to draw to
     * @param percent the percentual value of the slider
     * @param ownerShape the boundingbox of the owning entity
     */
    private void drawCircle(Graphics g, float percent, Shape ownerShape) {
    	float percentPosition = ownerShape.getWidth() * percent;
        float startX = ownerShape.getMinX() + percentPosition;
        g.setAntiAlias(true);
        int x,y;
        //draw outer circle
        g.setColor(Color.black);
        x = (int) (startX-SLIDER_CIRCLE_RADIUS);
        y = (int) (ownerShape.getCenterY()-SLIDER_CIRCLE_RADIUS);

        g.fillOval(x,y, SLIDER_CIRCLE_RADIUS*2, SLIDER_CIRCLE_RADIUS*2);

        //draw inner circle
        g.setColor(new Color(255,250,211));
        x = (int) (startX-(SLIDER_CIRCLE_RADIUS-SLIDER_CIRCLE_STROKE_THICKNESS));
        y = (int) (ownerShape.getCenterY()-(SLIDER_CIRCLE_RADIUS-SLIDER_CIRCLE_STROKE_THICKNESS));

        g.fillOval(x, y, (SLIDER_CIRCLE_RADIUS-SLIDER_CIRCLE_STROKE_THICKNESS)*2, (SLIDER_CIRCLE_RADIUS-SLIDER_CIRCLE_STROKE_THICKNESS)*2);
        g.setAntiAlias(false);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
