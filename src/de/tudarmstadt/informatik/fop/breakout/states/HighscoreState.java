package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.exceptions.IllegalHighscoreFormat;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.HighScoreEntryRenderComponent;

import de.tudarmstadt.informatik.fop.breakout.views.gui.ButtonView;
import eea.engine.entity.Entity;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State showing the top ten high scores
 */
public class HighscoreState extends AbstractMenuState {

    private final Logger logger = LogManager.getLogger();

    private ButtonView buttonResetHighscore;

    public HighscoreState(int id) throws SlickException {
        super(id, new Image(GameParameters.HIGHSCORE_BACKGROUND_PATH), GameParameters.HIGHSCORE_TITLE_TEXT);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        if (isTesting()) {
            return;
        }

        Breakout breakout = (Breakout) game;
        HighScoreController highScoreController = breakout.getHighScoreController();

        // Load highscore from file
        try {
            highScoreController.loadFromFile();
        } catch (IOException | IllegalHighscoreFormat e) {
            logger.error("Some error occurred while loading highscore: {}", e);
        }

        //add all score entries
        List<IHighscoreEntry> highscores = highScoreController.getHighscores();
        for (int index = 0; index < highscores.size(); index++) {
            IHighscoreEntry entry = highscores.get(index);
            addScoreEntries(container.getWidth() / 2, index, entry);
        }

        Image buttonImage = new Image(GameParameters.HIGHSCORE_RESET_IMAGE).getScaledCopy(GameParameters.HIGHSCORE_RESET_SIZE);
        Image buttonOverImage = new Image(GameParameters.HIGHSCORE_RESET_OVER_IMAGE).getScaledCopy(GameParameters.HIGHSCORE_RESET_SIZE);

        buttonResetHighscore = new ButtonView(container,buttonImage,40,520,"");
        buttonResetHighscore.setMouseOverImage(buttonOverImage);
        buttonResetHighscore.addListener(source -> {
            // Reset highscore and save changes to disk
            highScoreController.reset();
            try {
                highScoreController.saveToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        super.render(gameContainer, stateBasedGame, graphics);
        buttonResetHighscore.render(gameContainer, graphics);
    }

    /**
     * Adds another highscore entry
     *
     * @param midX the middle x position of this screen
     * @param rank the rank of this entry (starting with 1)
     * @param entry the actual entry that should be added
     *
     * @throws SlickException on missing entry image
     */
    private void addScoreEntries(float midX, int rank, IHighscoreEntry entry) throws SlickException {
        Entity entryEntity = new Entity("entry_" + rank);
        //resize the entity to vertically fit inside the background part
        entryEntity.setScale(0.45F);

        HighScoreEntryRenderComponent renderComponent = new HighScoreEntryRenderComponent(rank, entry);
        entryEntity.addComponent(renderComponent);

        int imageHeight = (int) renderComponent.getSize().getY();
        int posY = GameParameters.HIGHSCORE_ENTRY_START_Y
                //the gap before and after the entry
                + GameParameters.HIGHSCORE_ENTRY_GAP * rank
                //all previous entries
                + imageHeight * (rank - 1)
                //the position is always the center - so divide the to-draw-content
                + imageHeight / 2;
        entryEntity.setPosition(new Vector2f(midX, posY));
        addEntity(entryEntity);
    }
}
