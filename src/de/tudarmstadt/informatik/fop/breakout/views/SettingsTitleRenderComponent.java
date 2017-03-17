package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * Shows the settings titles example: "Volume"
 */
public class SettingsTitleRenderComponent extends RenderComponent {

    private final TrueTypeFont titleFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 30), true);

    private final String text;
    private final int textWidth;
    private final int textHeight;

    public SettingsTitleRenderComponent(String id, String title) {
        super(id);

        this.text = title;
        this.textWidth = titleFont.getWidth(text);
        this.textHeight = titleFont.getHeight(text);
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(textWidth, textHeight);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame sb, Graphics graphics) {
        graphics.setFont(titleFont);

        //start painting from the top left point of the in order to draw the components only inside it
        float startX = getOwnerEntity().getShape().getMinX();
        float startY = getOwnerEntity().getPosition().getY() - textHeight / 2;
        graphics.drawString(text, startX, startY);

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        //ignore
    }
}
