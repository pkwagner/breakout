package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

import eea.engine.component.render.ImageRenderComponent;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * View component for rendering each score entry inside the highscore state
 */
public class HighScoreEntryRenderComponent extends ImageRenderComponent {

    private final TrueTypeFont textFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 16), true);

    private final String entryText;
    private final int textWidth;
    private final int textHeight;

    /**
     * Create a new entry view component
     *
     * @param rank the ranking place starting from 1. to 10.
     * @param entry the score model
     * @throws SlickException if the image wasn't found
     */
    public HighScoreEntryRenderComponent(int rank, IHighscoreEntry entry) throws SlickException {
        super(new Image("images/entry.png"));

        this.entryText = entry.getPlayerName() + ' ' + entry.getPoints();
        this.textWidth = textFont.getWidth(entryText);
        this.textHeight = textFont.getHeight(entryText);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        //draw the background image first and then overdraw it with our text on the foreground
        super.render(gameContainer, stateBasedGame, graphics);

        graphics.draw(getOwnerEntity().getShape());

        graphics.setFont(textFont);

        //center the text inside the image
        Vector2f ownerPos = getOwnerEntity().getPosition();
        graphics.drawString(entryText, ownerPos.getX() - textWidth / 2, ownerPos.getY() - textHeight / 2);

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();
    }
}
