package de.tudarmstadt.informatik.fop.breakout.states.submenu;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.HighScoreEntryView;
import de.tudarmstadt.informatik.fop.breakout.views.gui.ButtonView;
import eea.engine.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.List;

/**
 * State showing the top ten high scores
 */
public class HighscoreState extends AbstractMenuState {

    private final Logger logger = LogManager.getLogger();

    private ButtonView buttonResetHighscore;

    public HighscoreState(int id) throws SlickException {
        super(id, GameParameters.HIGHSCORE_BACKGROUND_IMAGE);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        if (isTesting()) {
            return;
        }

        Breakout breakout = (Breakout) game;
        HighScoreController highScoreController = breakout.getHighScoreController();

        // Add column identifiers
        addFirstEntry(container);

        //add all score entries
        List<IHighscoreEntry> highscores = highScoreController.getHighscores();
        for (int index = 0; index < highscores.size(); index++) {
            IHighscoreEntry entry = highscores.get(index);
            addScoreEntries(index, entry,container);
        }

        Image buttonImage = new Image(GameParameters.HIGHSCORE_RESET_IMAGE).getScaledCopy(GameParameters.HIGHSCORE_RESET_SIZE);
        Image buttonOverImage = new Image(GameParameters.HIGHSCORE_RESET_OVER_IMAGE).getScaledCopy(GameParameters.HIGHSCORE_RESET_SIZE);

        buttonResetHighscore = new ButtonView(container,buttonImage,40,520,"");
        buttonResetHighscore.setMouseOverImage(buttonOverImage);

        buttonResetHighscore.addListener(source -> {
            // Reset highscore and save changes to disk
            highScoreController.reset();
            removeEntryEntities();
            try {
                highScoreController.saveToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void removeEntryEntities(){
        entityManager.getEntitiesByState(stateId).stream().filter(entity -> entity.getID().contains("entry"))
                .forEach(entity -> removeEntity(entity));
    }

    @Override
    public void enter(GameContainer gameContainer,StateBasedGame stateBasedGame) throws SlickException{
        Breakout breakout = (Breakout) stateBasedGame;
        HighScoreController highScoreController = breakout.getHighScoreController();

        //add all score entries
        removeEntryEntities();
        List<IHighscoreEntry> highscores = highScoreController.getHighscores();
        for (int index = 0; index < highscores.size(); index++) {
            IHighscoreEntry entry = highscores.get(index);
            addScoreEntries(index, entry,gameContainer);
        }
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
     * @param rank the rank of this entry (starting with 1)
     * @param entry the actual entry that should be added
     * @param gameContainer the gamecontainer instance
     *
     * @throws SlickException on missing entry image
     */
    private void addScoreEntries(int rank, IHighscoreEntry entry,GameContainer gameContainer) throws SlickException {
        float midX = gameContainer.getWidth()/2;

        Entity entryEntity = new Entity("entry_" + rank);
        //resize the entity to vertically fit inside the background part
        entryEntity.setScale(0.45F);

        HighScoreEntryView renderComponent = new HighScoreEntryView(rank, entry,gameContainer);
        entryEntity.addComponent(renderComponent);

        int imageHeight = (int) renderComponent.getSize().getY();
        int posY = GameParameters.HIGHSCORE_ENTRY_START_Y
                //the gap before and after the entry
                + GameParameters.HIGHSCORE_ENTRY_GAP * (rank + 1)
                //all previous entries
                + imageHeight * rank
                //the position is always the center - so divide the to-draw-content
                + imageHeight / 2;

        entryEntity.setPosition(new Vector2f(midX, posY));
        addEntity(entryEntity);
    }

    private void addFirstEntry(GameContainer gameContainer){
        Entity entryEntity = new Entity("table_title");
        HighScoreEntryView renderComponent = new HighScoreEntryView(-1,"NAME","BLOCKS","TIME","SCORE",gameContainer);
        entryEntity.addComponent(renderComponent);
        int imageHeight = (int) renderComponent.getSize().getY();
        int posY = GameParameters.HIGHSCORE_ENTRY_START_Y - imageHeight / 2;
        entryEntity.setPosition(new Vector2f(gameContainer.getWidth()/2, posY));
        addEntity(entryEntity);
    }
}
