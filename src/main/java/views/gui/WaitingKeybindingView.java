package views.gui;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shows a different text if a KeyBinding is waiting for a new key to set.
 *
 * @see models.KeyBinding
 */
public class WaitingKeybindingView extends RenderComponent {

    private static final String WAITING_TEXT = "Click any key";

    public WaitingKeybindingView(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics graphicsContext) {
        Vector2f pos = getOwnerEntity().getPosition();

        Font font = graphicsContext.getFont();

        int textHeight = font.getHeight(WAITING_TEXT);
        int textWidth = font.getWidth(WAITING_TEXT);
        graphicsContext.drawString(WAITING_TEXT, pos.getX() - textWidth / 2, pos.getY() - textHeight / 2);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
