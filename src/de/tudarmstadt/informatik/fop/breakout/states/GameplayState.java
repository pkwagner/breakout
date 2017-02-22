package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.PauseToggleAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.BallController;
import de.tudarmstadt.informatik.fop.breakout.controllers.ClockController;
import de.tudarmstadt.informatik.fop.breakout.controllers.MapController;
import de.tudarmstadt.informatik.fop.breakout.controllers.StickController;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.models.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.ClockModel;
import de.tudarmstadt.informatik.fop.breakout.models.StickModel;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.BallRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.ClockRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.StickRenderComponent;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;

    public GameplayState(int id) {
        this.stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (Breakout.getDebug()) {
            return;
        }

        StickModel stickModel = new StickModel(GameParameters.STICK_ID);
        StickController stickController = new StickController("stickController");
        stickModel.addComponent(stickController);
        stickModel.addComponent(new StickRenderComponent());

        BallModel ballModel = new BallModel(GameParameters.BALL_ID);
        BallController ballController = new BallController("ball_controller");
        ballModel.addComponent(ballController);
        ballModel.addComponent(new BallRenderComponent());

        ClockModel clockModel = new ClockModel(GameParameters.STOP_WATCH_ID);
        ClockController clockController = new ClockController("clock_controller");
        clockModel.addComponent(clockController);
        ClockRenderComponent clockView = new ClockRenderComponent("clock_view");
        clockModel.addComponent(clockView);
        
        ballController.init(stateBasedGame);
        stickController.init(stateBasedGame);
        clockController.init(stateBasedGame);
        clockView.init();

        addEntity(stickModel);
        addEntity(ballModel);

        addBorders(gameContainer);
        addPauseEntity(gameContainer);
        
        MapController mapController = new MapController(gameContainer, this);

        mapController.loadMap();
        
        addEntity(clockModel);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
            throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    }

    public void addEntity(Entity entity) {
        entityManager.addEntity(stateId, entity);
    }

    public void removeEntity(Entity entity) {
        entityManager.removeEntity(stateId, entity);
    }

    @Override
    public int getID() {
        return stateId;
    }

    private void addBorders(GameContainer gameContainer) {
        Entity leftBorder = new BorderFactory(GameParameters.BorderType.LEFT).createEntity();
        Entity rightBorder = new BorderFactory(GameParameters.BorderType.RIGHT).createEntity();
        Entity topBorder = new BorderFactory(GameParameters.BorderType.TOP).createEntity();

        addEntity(leftBorder);
        addEntity(rightBorder);
        addEntity(topBorder);
    }

    /**
     * Adds an entity for pausing the game.
     * <p>
     * It will toggle the pause state on keyboard input and will show up an pause-image on pause.
     *
     * @param gameContainer game instance container
     * @throws SlickException if the pause cannot be found
     */
    private void addPauseEntity(GameContainer gameContainer) throws SlickException {
        Entity pauseEntity = new Entity(GameParameters.PAUSE_ID);

        //default hides the entity and make it passable so it won't effect the gameplay
        pauseEntity.setVisible(false);
        pauseEntity.setPassable(true);

        //center the entity
        pauseEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));

        //view component
        pauseEntity.addComponent(new ImageRenderComponent(new Image(GameParameters.PAUSE_IMAGE)));

        //key listener
        KeyPressedEvent escapeKeyEvent = new KeyPressedEvent(Input.KEY_ESCAPE);
        escapeKeyEvent.addAction(new PauseToggleAction());
        pauseEntity.addComponent(escapeKeyEvent);

        addEntity(pauseEntity);
    }
}
