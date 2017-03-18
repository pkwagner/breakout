package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.PauseToggleAction;
import de.tudarmstadt.informatik.fop.breakout.actions.StartGameAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.*;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.models.*;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.*;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Arrays;

public class GameplayState extends BasicGameState {

    private final Logger logger = LogManager.getLogger();

    private final StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
    private final int stateId;
    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;
    private SpriteSheet backgroundSpriteSheet;
    private Animation backgroundAnimation;
    private RamBlockMovementController ramBlockMovementController;
    private MapController mapController;
    private PlayerModel player;
    private ClockModel clock;

    public GameplayState(int id) {
        this.stateId = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (Breakout.getDebug()) {
            return;
        }

        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        //load sound effects
        Breakout breakout = (Breakout) stateBasedGame;
        SoundController soundController = breakout.getSoundController();
        soundController.load(SoundType.BLOCK_HIT, SoundType.ITEM_PICKUP, SoundType.STICK_HIT);

        backgroundSpriteSheet = new SpriteSheet(GameParameters.BACKGROUND_SPRITESHEET, GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
        backgroundAnimation = new Animation(backgroundSpriteSheet,70);

        ramBlockMovementController = new RamBlockMovementController();

        // Basic player implementation
        // TODO Multiplayer
        String playerId = GameParameters.PLAYER_ID + "_" + 0;
        player = new PlayerModel(playerId, 0, GameParameters.PLAYER_DEFAULT_HEALTHPOINTS);
        PlayerStatsRenderComponent playerView = new PlayerStatsRenderComponent(playerId + GameParameters.EXT_VIEW, false);
        player.addComponent(playerView);
        playerView.init();
    }

    private void loadLevel(int mapId) throws SlickException {
        // TODO Move some parts to 'init(...)' to avoid double calculations
        // Pause game
        gameContainer.setPaused(true);

        // Delete all previous entities
        entityManager.clearEntitiesFromState(stateId);

        StickModel stickModel = new StickModel(GameParameters.STICK_ID, player);
        StickController stickController = new StickController(GameParameters.STICK_ID + GameParameters.EXT_CONTROLLER);
        stickModel.addComponent(stickController);
        stickModel.addComponent(new StickRenderComponent());

        BallModel ballModel = new BallModel(GameParameters.BALL_ID, player);
        BallController ballController = new BallController(GameParameters.BALL_ID + GameParameters.EXT_CONTROLLER);
        ballModel.addComponent(ballController);
        BallRenderComponent ballView = new BallRenderComponent(GameParameters.BALL_ID + GameParameters.EXT_VIEW);
        ballModel.addComponent(ballView);
        ballView.init();

        ballController.init(stateBasedGame);
        stickController.init(stateBasedGame);

        mapController = new MapController(stateBasedGame, this);
        mapController.loadMap(mapId);
        addBorders();

        addEntity(stickModel);
        addEntity(ballModel);

        addEntity(clock);
        addEntity(player);

        addStartGameEntity(gameContainer);
        addPauseEntities(gameContainer);
    }

    private void newGame(PlayerModel player) throws SlickException {
        // Reset player
        player.reset();

        // Initialize clock
        clock = new ClockModel(GameParameters.STOP_WATCH_ID);
        ClockController clockController = new ClockController(GameParameters.STOP_WATCH_ID + GameParameters.EXT_CONTROLLER);
        clock.addComponent(clockController);
        ClockRenderComponent clockView = new ClockRenderComponent(GameParameters.STOP_WATCH_ID + GameParameters.EXT_VIEW);
        clock.addComponent(clockView);
        clockView.init();
        clockController.init(stateBasedGame);

        // Load initial map
        loadLevel(GameParameters.MAP_INITIAL_ID);
    }

    public void nextLevel() {
        int currentMapId = mapController.getMapId();
        if (currentMapId < GameParameters.MAP_COUNT) {
            try {
                loadLevel(++currentMapId);
            } catch (SlickException e) {
                logger.error("Some error occurred while loading map" + currentMapId + ": " + e);
            }
        } else {
            // TODO Victory screen?!
        }
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

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        newGame(player);
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

        startGameEntity.setPassable(true);
        addEntity(startGameEntity);
    }

    private void addBorders() {
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


        //center the entity
        pauseImage.setPosition(new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));

        //view component
        pauseImage.addComponent(new ImageRenderComponent(new Image(GameParameters.PAUSE_IMAGE)));
        pauseImage.setPassable(true);
        //key listener
        Entity pauseEntity = new Entity(GameParameters.PAUSE_ID);
        KeyPressedEvent escapeKeyEvent = new KeyPressedEvent(Input.KEY_ESCAPE);
        escapeKeyEvent.addAction(new PauseToggleAction(backButton, pauseImage));
        pauseEntity.addComponent(escapeKeyEvent);

        pauseEntity.setPassable(true);
        addEntity(pauseEntity);
        addEntity(pauseImage);
        addEntity(backButton);
    }

    public RamBlockMovementController getRBMC(){
    	return ramBlockMovementController;
    }

    //TODO
    public ArrayList<PlayerModel> getPlayers() {
        return new ArrayList<>(Arrays.asList(new PlayerModel[]{player}));
    }

    public MapController getMapController() {
        return mapController;
    }
}
