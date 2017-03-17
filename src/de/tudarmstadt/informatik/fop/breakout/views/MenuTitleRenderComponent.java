package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * View component for rendering the title on certain states
 */
public class MenuTitleRenderComponent extends RenderComponent {

    private final TrueTypeFont titleFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);

    private final String text;
    private final int textWidth;
    private final int textHeight;

    public MenuTitleRenderComponent(String id, String text) {
        super(id);

        this.text = text;
        this.textWidth = titleFont.getWidth(text);
        this.textHeight = titleFont.getHeight(text);
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(textWidth, textHeight);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setFont(titleFont);

        //start painting from the top left point of the in order to draw the components only inside it
        int startX = gameContainer.getWidth() / 2 - textWidth / 2;
        graphics.drawString(text, startX, GameParameters.HIGHSCORE_TITLE_START_Y);

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore it's a constant title
    }
}
