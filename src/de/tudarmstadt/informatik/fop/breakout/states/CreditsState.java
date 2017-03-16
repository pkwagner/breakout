package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.CreditsRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
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

        addBackEvent(stateBasedGame);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    }

    private void addBackEvent(StateBasedGame stateBasedGame) {
        Entity backEntity = new Entity("back");

        KeyPressedEvent escapeEvent = new KeyPressedEvent(Input.KEY_ESCAPE);
        escapeEvent.addAction((cont, game, delta, comp) -> stateBasedGame.enterState(GameParameters.MAINMENU_STATE));
        backEntity.addComponent(escapeEvent);

        entityManager.addEntity(stateId, backEntity);
    }

    private void addBackground(GameContainer gameContainer) throws SlickException {
        Entity backgroundEntity = new Entity(GameParameters.BACKGROUND_ID);

        //center the image to be displayed on the complete window
        backgroundEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
        backgroundEntity.addComponent(new ImageRenderComponent(new Image("images/menu_blank.png")));

        entityManager.addEntity(stateId, backgroundEntity);
    }

    private void addText(GameContainer gameContainer) {
        Entity titleEntity = new Entity("title");

        titleEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, GameParameters.CREDITS_TITLE_START_Y));
        titleEntity.addComponent(new CreditsRenderComponent("title_view"));

        entityManager.addEntity(stateId, titleEntity);
    }
}
