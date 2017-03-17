package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.models.gui.BackButton;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.HighScoreEntryRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.MenuTitleRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

/**
 * State showing the top ten high scores
 */
public class HighscoreState extends BasicGameState {

    private final Logger logger = LogManager.getLogger();
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;

    public HighscoreState(int id) {
        this.stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        logger.info("Initialize highscore state");

        if (Breakout.getDebug()) {
            return;
        }

        addBackground(gameContainer);
        addTitle();

        Breakout breakout = (Breakout) stateBasedGame;
        HighScoreController highScoreController = breakout.getHighScoreController();

        //add all score entries
        List<IHighscoreEntry> highscores = highScoreController.getHighscores();
        for (int index = 0; index < highscores.size(); index++) {
            IHighscoreEntry entry = highscores.get(index);
            addScoreEntries(gameContainer, index, entry);
        }

        entityManager.addEntity(stateId, new BackButton());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    @Override
    public int getID() {
        return stateId;
    }

    private void addScoreEntries(GameContainer gameContainer, int index, IHighscoreEntry entry) throws SlickException {
        Entity entryEntity = new Entity("entry_" + index);
        //resize the entity to vertically fit inside the background part
        entryEntity.setScale(0.45F);

        HighScoreEntryRenderComponent renderComponent = new HighScoreEntryRenderComponent(index + 1, entry);
        entryEntity.addComponent(renderComponent);

        int imageHeight = (int) renderComponent.getSize().getY();
        int posY = GameParameters.HIGHSCORE_ENTRY_START_Y
                //the gap before and after the entry
                + GameParameters.HIGHSCORE_ENTRY_GAP * (index + 1)
                //all previous entries
                + imageHeight * index
                //the position is always the center - so divide the to-draw-content
                + imageHeight / 2;
        entryEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, posY));
        entityManager.addEntity(stateId, entryEntity);
    }

    private void addBackground(GameContainer gameContainer) throws SlickException {
        Entity backgroundEntity = new Entity(GameParameters.BACKGROUND_ID);

        //center the image to be displayed on the complete window
        backgroundEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
        backgroundEntity.addComponent(new ImageRenderComponent(new Image(GameParameters.BLANK_BACKGROUND_IMAGE)));

        entityManager.addEntity(stateId, backgroundEntity);
    }

    private void addTitle() {
        Entity titleEntity = new Entity(GameParameters.HIGHSCORE_TITLE_ID);
        titleEntity.addComponent(new MenuTitleRenderComponent("title_view", GameParameters.HIGHSCORE_TITLE_TEXT));
        entityManager.addEntity(stateId, titleEntity);
    }
}
