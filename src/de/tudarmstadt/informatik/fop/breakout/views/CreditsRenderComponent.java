package de.tudarmstadt.informatik.fop.breakout.views;

import eea.engine.component.RenderComponent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

import static de.tudarmstadt.informatik.fop.breakout.constants.GameParameters.*;

/**
 * View component for rendering the whole credit state
 */
public class CreditsRenderComponent extends RenderComponent {

    private final TrueTypeFont creditsFont = new TrueTypeFont(new Font("Verdana", Font.BOLD, 25), true);

    public CreditsRenderComponent(String id) {
        super(id);
    }

    @Override
    public Vector2f getSize() {
        return getOwnerEntity().getSize();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        // Print the developers
        graphics.setFont(creditsFont);

        //Draw the content
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
