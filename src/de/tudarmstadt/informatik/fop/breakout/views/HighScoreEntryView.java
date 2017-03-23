package de.tudarmstadt.informatik.fop.breakout.views;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import eea.engine.component.RenderComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * View component for rendering each score entry inside the highscore state
 */
public class HighScoreEntryView extends RenderComponent {

    private final TrueTypeFont textFont = new TrueTypeFont(new Font("Poplar", Font.PLAIN, 20), true);

    private final int textHeight;
    private final int alignX1,alignX2,alignX3,alignX4, frameWidth;

    private final int GAP_Y = 5;

    private String nameText,blocksText,timeText,scoreText;

    /**
     * Create a new entry view component
     *
     * @param rank the ranking place starting from 1. to 10.
     * @param entry the score model
     * @throws SlickException if the image wasn't found
     */
    public HighScoreEntryView(int rank, IHighscoreEntry entry, GameContainer gameContainer) throws SlickException {
        this(rank,entry.getPlayerName(),
                ""+entry.getNumberOfDestroyedBlocks(),
                ""+entry.getElapsedTime(),
                ""+entry.getPoints(),gameContainer);
    }

    public HighScoreEntryView(int rank, String name, String blocks, String time, String score, GameContainer gameContainer){
        super("highscore_entry_"+rank);

        frameWidth = gameContainer.getWidth();

        alignX1 = frameWidth*1/6;
        alignX2 = frameWidth*3/6;
        alignX3 = frameWidth*4/6;
        alignX4 = frameWidth*5/6;

        nameText = cropString(name,frameWidth*2/6);
        blocksText = blocks;
        timeText = time;
        scoreText = score;

        textHeight = textFont.getHeight();
    }

    private String cropString(String text,int length){
        StringBuilder sb = new StringBuilder();
        sb.append(text);

        while (textFont.getWidth(sb)>length) {
            sb.delete(sb.length()-6,sb.length());
            sb.append("...");
        }

        return sb.toString();
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f (frameWidth,textHeight+2*GAP_Y);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        //draw the background image first and then overdraw it with our text on the foreground
        Vector2f ownerPos = getOwnerEntity().getPosition();

        //graphics.draw(getOwnerEntity().getShape());

        float stringY = ownerPos.getY()-1/2*textHeight;

        graphics.setFont(textFont);
        graphics.drawString(nameText, alignX1, stringY);
        graphics.drawString(blocksText, alignX2, stringY);
        graphics.drawString(timeText, alignX3, stringY);
        graphics.drawString(scoreText, alignX4, stringY);

        //restore the previous font otherwise it would paint that font for other entities as well
        graphics.resetFont();

        //draw line above the entry
        float y = ownerPos.getY()-textHeight*1/2-GAP_Y;
        //graphics.draw(new Line(0,y, frameWidth,y));
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

    }
}
