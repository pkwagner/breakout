package views.gui;

import models.gui.Checkbox;

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
public class CheckboxView extends RenderComponent {
    private final static int CHECKBOX_STROKE_THICKNESS = 4;
    private final static int CHECKBOX_ENABLED_WIDTH = 8;

    public CheckboxView(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
        Checkbox checkbox = (Checkbox) getOwnerEntity();
        Shape shape = checkbox.getShape();
        g.setAntiAlias(true);
        Color oldColor = g.getColor();

        //draw the box
        g.setColor(Color.black);
        g.fillRect(shape.getMinX(), shape.getMinY(), shape.getWidth(), shape.getHeight());

        g.setColor(new Color(255, 250, 211));
        g.fillRect(shape.getMinX() + CHECKBOX_STROKE_THICKNESS, shape.getMinY() + CHECKBOX_STROKE_THICKNESS, shape.getWidth() - CHECKBOX_STROKE_THICKNESS * 2, shape.getHeight() - CHECKBOX_STROKE_THICKNESS * 2);

        //draw the X
        if (checkbox.isEnabled()) {
            g.setColor(Color.black);
            g.fillRect((int) (shape.getCenterX() - CHECKBOX_ENABLED_WIDTH / 2.0), (int) (shape.getCenterY() - CHECKBOX_ENABLED_WIDTH / 2.0), CHECKBOX_ENABLED_WIDTH, CHECKBOX_ENABLED_WIDTH);
        }

        //restore the default settings
        g.setColor(oldColor);
        g.setAntiAlias(false);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
