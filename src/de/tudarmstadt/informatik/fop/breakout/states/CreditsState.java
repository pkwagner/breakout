package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.models.BackButton;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.CreditsRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreditsState extends BasicGameState {

    private final Logger logger = LogManager.getLogger();
    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();

    private final int stateId;

    public CreditsState(int id) {
        this.stateId = id;
    }

    @Override
    public int getID() {
        return stateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        logger.info("Initialize credits state");
        if (Breakout.getDebug()) {
            return;
        }

        addBackground(gameContainer);
        addText(gameContainer);

        entityManager.addEntity(stateId, new BackButton());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    }

    private void addBackground(GameContainer gameContainer) throws SlickException {
        Entity backgroundEntity = new Entity(GameParameters.BACKGROUND_ID);

        //center the image to be displayed on the complete window
        backgroundEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
        backgroundEntity.addComponent(new ImageRenderComponent(new Image(GameParameters.CREDITS_BACKGROUND_PATH)));

        entityManager.addEntity(stateId, backgroundEntity);
    }

    private void addText(GameContainer gameContainer) {
        Entity titleEntity = new Entity(GameParameters.CREDITS_TITLE_ID);

        titleEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, GameParameters.CREDITS_TITLE_START_Y));
        titleEntity.addComponent(new CreditsRenderComponent(GameParameters.CREDITS_TITLE_ID + GameParameters.EXT_VIEW));

        entityManager.addEntity(stateId, titleEntity);
    }
}
