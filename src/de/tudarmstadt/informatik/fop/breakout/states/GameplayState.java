package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.PauseToggleAction;
import de.tudarmstadt.informatik.fop.breakout.actions.StartGameAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.*;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.models.*;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.BallRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.ClockRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.StartGameRenderComponent;
import de.tudarmstadt.informatik.fop.breakout.views.StickRenderComponent;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;

import org.newdawn.slick.*;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;
    private SpriteSheet backgroundSpriteSheet;
    private Animation backgroundAnimation;
    private RamBlockMovementController ramBlockMovementController;

    private final SoundController soundController = new SoundController();

    public GameplayState(int id) {
        this.stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (Breakout.getDebug()) {
            return;
        }

        //load sound effects
        soundController.load(SoundType.BLOCK_HIT, SoundType.ITEM_PICKUP, SoundType.STICK_HIT, SoundType.BACKGROUND_MUSIC);
        soundController.playMusic(SoundType.BACKGROUND_MUSIC);

        backgroundSpriteSheet = new SpriteSheet(GameParameters.BACKGROUND_SPRITESHEET, GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
        backgroundAnimation = new Animation(backgroundSpriteSheet,70);

        StickModel stickModel = new StickModel(GameParameters.STICK_ID);
        StickController stickController = new StickController(GameParameters.STICK_ID + GameParameters.EXT_CONTROLLER);
        stickModel.addComponent(stickController);
        stickModel.addComponent(new StickRenderComponent());

        BallModel ballModel = new BallModel(GameParameters.BALL_ID);
        BallController ballController = new BallController(GameParameters.BALL_ID + GameParameters.EXT_CONTROLLER);
        ballModel.addComponent(ballController);
        BallRenderComponent ballView = new BallRenderComponent(GameParameters.BALL_ID + GameParameters.EXT_VIEW);
        ballModel.addComponent(ballView);
        ballView.init();

        ClockModel clockModel = new ClockModel(GameParameters.STOP_WATCH_ID);
        ClockController clockController = new ClockController(GameParameters.STOP_WATCH_ID + GameParameters.EXT_CONTROLLER);
        clockModel.addComponent(clockController);
        ClockRenderComponent clockView = new ClockRenderComponent(GameParameters.STOP_WATCH_ID + GameParameters.EXT_VIEW);
        clockModel.addComponent(clockView);

        ballController.init(stateBasedGame);
        stickController.init(stateBasedGame);
        clockController.init(stateBasedGame);
        clockView.init();

        addEntity(stickModel);
        addEntity(ballModel);

        addStartGameEntity(gameContainer);
        ramBlockMovementController = new RamBlockMovementController();
        MapController mapController = new MapController(stateBasedGame, this);

        mapController.loadMap();

        //add these entities at the end in order to overdraw the other components if visible
        addBorders(gameContainer);
        addEntity(clockModel);
        addPauseEntities(gameContainer);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
            throws SlickException {
        entityManager.updateEntities(gameContainer, stateBasedGame, delta);
        ramBlockMovementController.update(delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        backgroundAnimation.draw(0,0);
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

    private void addStartGameEntity(GameContainer gameContainer) {
        Entity startGameEntity = new Entity(GameParameters.GAMESTART_ENTITY_ID);
        //center text
        startGameEntity.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
        startGameEntity.setSize(new Vector2f(100, 100));
        startGameEntity.addComponent(new StartGameRenderComponent());

        KeyPressedEvent startGameEvent = new KeyPressedEvent(Input.KEY_SPACE);
        startGameEvent.addAction(new StartGameAction());
        startGameEntity.addComponent(startGameEvent);

        addEntity(startGameEntity);
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
    private void addPauseEntities(GameContainer gameContainer) throws SlickException {
        //show the back to main menu too on pausing the game
        BackButton backButton = new BackButton();
        backButton.setVisible(false);

        Entity pauseImage = new Entity(GameParameters.PAUSE_IMAGE_ID);

        //default hides the entity and make it passable so it won't effect the gameplay
        pauseImage.setVisible(false);
        pauseImage.setPassable(true);

        //center the entity
        pauseImage.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));

        //view component
        pauseImage.addComponent(new ImageRenderComponent(new Image(GameParameters.PAUSE_IMAGE)));

        //key listener
        Entity pauseEntity = new Entity(GameParameters.PAUSE_ID);
        KeyPressedEvent escapeKeyEvent = new KeyPressedEvent(Input.KEY_ESCAPE);
        escapeKeyEvent.addAction(new PauseToggleAction(backButton, pauseImage));
        pauseEntity.addComponent(escapeKeyEvent);

        addEntity(pauseEntity);
        addEntity(pauseImage);
        addEntity(backButton);
    }

    public RamBlockMovementController getRBMC(){
    	return ramBlockMovementController;
    }

    public SoundController getSoundController() {
        return soundController;
    }
}
