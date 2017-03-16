package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import static de.tudarmstadt.informatik.fop.breakout.constants.GameParameters.*;

/**
 * View component for rendering the whole credit state
 */
public class CreditsRenderComponent extends RenderComponent {

    private final TrueTypeFont titleFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);
    private final TrueTypeFont creditsFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 25), true);

    private final int textWidth = titleFont.getWidth(CREDITS_TITLE_TEXT);
    private final int textHeight = titleFont.getHeight(CREDITS_TITLE_TEXT);

    public CreditsRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        // TODO Should been updated
        return new Vector2f(textWidth, textHeight);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setFont(titleFont);

        //start painting from the top left point of the in order to draw the components only inside it
        Shape ownerShape = getOwnerEntity().getShape();
        graphics.drawString(CREDITS_TITLE_TEXT, ownerShape.getMinX(), ownerShape.getMinY());

        // Print the developers
        graphics.setFont(creditsFont);
        graphics.drawString(CREDITS_DEV1_TEXT, CREDITS_DEVS_START_X, CREDITS_DEVS_START_Y);
        graphics.drawString(CREDITS_DEV2_TEXT, CREDITS_DEVS_START_X, CREDITS_DEVS_START_Y + 1 * CREDITS_DEVS_SPACE_Y);
        graphics.drawString(CREDITS_DEV3_TEXT, CREDITS_DEVS_START_X, CREDITS_DEVS_START_Y + 2 * CREDITS_DEVS_SPACE_Y);
        graphics.drawString(CREDITS_DEV4_TEXT, CREDITS_DEVS_START_X, CREDITS_DEVS_START_Y + 3 * CREDITS_DEVS_SPACE_Y);

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        //ignore it's a constant title
    }
}