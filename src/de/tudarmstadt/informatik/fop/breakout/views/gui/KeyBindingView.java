package de.tudarmstadt.informatik.fop.breakout.views.gui;

import de.tudarmstadt.informatik.fop.breakout.models.KeyBinding;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shows the current KeyBinding including a text on how to change it.
 *
 * @see KeyBinding
 */
public class KeyBindingView extends RenderComponent {

    private static final String CHANGE_TEXT = "Click to change";
    private final KeyBinding keyBinding;

    public KeyBindingView(String id, KeyBinding keyBinding) {
        super(id);

        this.keyBinding = keyBinding;
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        Shape shape = getOwnerEntity().getShape();
        String keyName = keyBinding.getKeyName();

        //draw the current key name
        Font font = graphicsContext.getFont();
        int textWidth = font.getWidth(keyName);
        graphicsContext.drawString(keyName, shape.getCenterX() - textWidth / 2, shape.getMinY());

        //draw the text on how to change it
        textWidth = font.getWidth(CHANGE_TEXT);
        int textHeight = font.getHeight(CHANGE_TEXT);
        graphicsContext.drawString(CHANGE_TEXT, shape.getCenterX() - textWidth / 2, shape.getMaxY() - textHeight);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
