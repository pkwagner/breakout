package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * View component for rendering the title in the highscore state
 */
public class HighScoreTitleRenderComponent extends RenderComponent {

    private static final String TITLE_TEXT = "Highscores";

    private final TrueTypeFont titleFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);

    private final int textWidth = titleFont.getWidth(TITLE_TEXT);
    private final int textHeight = titleFont.getHeight(TITLE_TEXT);

    public HighScoreTitleRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(textWidth, textHeight);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setFont(titleFont);

        //start painting from the top left point of the in order to draw the components only inside it
        Shape ownerShape = getOwnerEntity().getShape();
        graphics.drawString(TITLE_TEXT, ownerShape.getMinX(), ownerShape.getMinY());

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore it's a constant title
    }
}
