package de.tudarmstadt.informatik.fop.breakout.states;

import de.tudarmstadt.informatik.fop.breakout.actions.PauseToggleAction;
import de.tudarmstadt.informatik.fop.breakout.actions.StartGameAction;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.*;
import de.tudarmstadt.informatik.fop.breakout.events.KeyPressedEvent;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.models.*;
import de.tudarmstadt.informatik.fop.breakout.models.gui.BackButton;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.views.*;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Showing the actual game where you can start playing
 */
public class GameplayState extends AbstractGameState {

    private final Logger logger = LogManager.getLogger();

    private final RamBlockMovementController ramBlockMovementController = new RamBlockMovementController();
    private final List<BallModel> balls = new ArrayList<>();

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private MapController mapController;
    private PlayerModel players[] = {};
    private ClockModel clock;
    private ClockController clockController;

    private float gameSpeedFactor = 1;
    private float gameSpeedFactorGoal = 1;
    private int ballIdCounter = 0;
    private boolean startAsMultiplayer = false, manuallyPaused;
    private final int initialLevelId;

    private boolean particleEffects = true;

    public GameplayState(int id, int initialLevelId) throws SlickException {
        // Load dynamic background
        super(id, new Animation(new SpriteSheet(GameParameters.BACKGROUND_SPRITESHEET
                , GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT), 70));

        this.initialLevelId = initialLevelId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        if (isTesting()) {
            return;
        }

        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        //load sound effects
        Breakout breakout = (Breakout) stateBasedGame;
        SoundController soundController = breakout.getSoundController();
        soundController.load(SoundType.BLOCK_HIT, SoundType.ITEM_PICKUP, SoundType.STICK_HIT);
    }

    /**
     * Load the level with the given id and initialize all related component
     *
     * @param mapId map id
     * @throws SlickException happens if images cannot be loaded
     */
    private void loadLevel(int mapId) throws SlickException {
        if (players.length <= 2) {
            boolean multiplayer = (players.length == 2);

            // Initially pause game and reset toggle
            gameContainer.setPaused(true);
            manuallyPaused = false;

            // Reset speed
            gameSpeedFactorGoal = 1;

            // Delete all previous entities
            clearEntities();

            // Add stick & ball to state
            balls.clear();

            // Add stick & ball to state
            addStick(stateBasedGame, gameContainer.getWidth() / 2, players[0]);
            addBall(stateBasedGame, players[0]);

            if (multiplayer) {
                addStick(stateBasedGame, gameContainer.getWidth() / 2, players[1]);
                addBall(stateBasedGame, players[1]);
            }

            mapController = new MapController(stateBasedGame, this, multiplayer);
            mapController.loadMap(mapId);

            addBorders(multiplayer);
            addEntity(clock);

            for (PlayerModel player : players)
                addEntity(player);

            // Initialize start game entity
            addStartGameEntity(gameContainer.getWidth() / 2);
            addPauseEntities(gameContainer);
        } else {
            logger.error("Some error occurred while initializing a new game: Player count is " + players.length);
        }
    }

    /**
     * Starts a new game.
     *
     * @param multiplayer true if the game should been started in multiplayer mode; false if not
     * @throws SlickException if images cannot be loaded
     */
    private void newGame(boolean multiplayer) throws SlickException {
        // Reset all given players
        // Basic player implementation
        PlayerModel player1 = new PlayerModel(GameParameters.PLAYER_ID, false, GameParameters.PLAYER_DEFAULT_HEALTHPOINTS);
        PlayerStatsRenderComponent player1View = new PlayerStatsRenderComponent(GameParameters.PLAYER_ID + GameParameters.EXT_VIEW, false);
        player1.addComponent(player1View);
        player1View.init();

        if (multiplayer) {
            PlayerModel player2 = new PlayerModel(GameParameters.PLAYER_ID_PLAYER2, true, GameParameters.PLAYER_DEFAULT_HEALTHPOINTS);
            PlayerStatsRenderComponent player2View = new PlayerStatsRenderComponent(GameParameters.PLAYER_ID_PLAYER2 + GameParameters.EXT_VIEW, false);
            player2.addComponent(player2View);
            player2View.init();

            players = new PlayerModel[]{player1, player2};
        } else {
            players = new PlayerModel[]{player1};
        }

        // Initialize clock
        clock = new ClockModel(GameParameters.STOP_WATCH_ID);
        clockController = new ClockController(GameParameters.STOP_WATCH_ID + GameParameters.EXT_CONTROLLER);
        clock.addComponent(clockController);
        ClockRenderComponent clockView = new ClockRenderComponent(GameParameters.STOP_WATCH_ID + GameParameters.EXT_VIEW);
        clock.addComponent(clockView);
        clockController.init(stateBasedGame);

        // Load initial map
        loadLevel(initialLevelId);
    }

    /**
     * Switches to the next level or ends the game if there are no more levels.
     */
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
        super.update(gameContainer, stateBasedGame, delta);

        if(!gameContainer.isPaused())ramBlockMovementController.update(this, delta);

        // Check if game speed fade is needed
        if (gameSpeedFactorGoal != gameSpeedFactor) {
            // Update game speed
            if (gameSpeedFactor < gameSpeedFactorGoal)
                gameSpeedFactor += GameParameters.GAME_SLOMO_ANIMATION_SPEED * delta;
            else
                gameSpeedFactor -= GameParameters.GAME_SLOMO_ANIMATION_SPEED * delta;

            // If gameSpeed is near it's goal, abort the animation
            if (Math.abs(gameSpeedFactor - gameSpeedFactorGoal) <= 0.01)
                gameSpeedFactor = gameSpeedFactorGoal;

            // Update pitch
            ((Breakout) stateBasedGame).getSoundController().setMusicPitch(gameSpeedFactor);
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        newGame(startAsMultiplayer);
    }

    /**
     * Adds a entity showing how to start the game.
     *
     * @param midX the middle x position of the screen
     */
    private void addStartGameEntity(float midX) {
        Entity startGameEntity = new Entity(GameParameters.GAMESTART_ENTITY_ID);
        //center text
        startGameEntity.setPosition(new Vector2f(midX, gameContainer.getHeight() / 2));
        startGameEntity.setSize(new Vector2f(100, 100));
        startGameEntity.addComponent(new StartGameRenderComponent());

        KeyPressedEvent startGameEvent = new KeyPressedEvent(KeyBinding.START_GAME);
        startGameEvent.addAction(new StartGameAction());
        startGameEntity.addComponent(startGameEvent);

        startGameEntity.setPassable(true);
        addEntity(startGameEntity);
    }

    /**
     * Add left, right and top borders (top only in singleplayer).
     *
     * @param multiplayer true if in multiplayer mode (remove top border), false if not
     */
    private void addBorders(boolean multiplayer) {
        Entity leftBorder = new BorderFactory(GameParameters.BorderType.LEFT).createEntity();
        Entity rightBorder = new BorderFactory(GameParameters.BorderType.RIGHT).createEntity();

        addEntity(leftBorder);
        addEntity(rightBorder);

        if (!multiplayer) {
            Entity topBorder = new BorderFactory(GameParameters.BorderType.TOP).createEntity();
            addEntity(topBorder);
        }
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
        KeyPressedEvent escapeKeyEvent = new KeyPressedEvent(KeyBinding.PAUSE);
        escapeKeyEvent.addAction(new PauseToggleAction(backButton, pauseImage));
        pauseEntity.addComponent(escapeKeyEvent);

        pauseEntity.setPassable(true);
        addEntity(pauseEntity);
        addEntity(pauseImage);
        addEntity(backButton);
    }

    /**
     * Creates and adds a new ball to the game
     *
     * @param stateBasedGame this game instance
     * @param initialControllingPlayer the player who initially controls the ball and should gain the points for destroyed blocks
     * @return the created ball
     * @throws SlickException if the image cannot be loaded
     */
    public BallModel addBall(StateBasedGame stateBasedGame, PlayerModel initialControllingPlayer) throws SlickException {
        boolean secondPlayer = initialControllingPlayer.isSecondPlayer();

        // FORMAT: BALL_[ID][/_VIEW/_CONTROLLER]
        BallModel ballModel = new BallModel(GameParameters.BALL_ID + "_" + ballIdCounter, initialControllingPlayer);
        BallController ballController = new BallController(GameParameters.BALL_ID + "_" + ballIdCounter + GameParameters.EXT_CONTROLLER);
        ballModel.addComponent(ballController);
        BallRenderComponent ballView = new BallRenderComponent(GameParameters.BALL_ID + "_" + ballIdCounter + GameParameters.EXT_VIEW);
        ballModel.addComponent(ballView);
        balls.add(ballModel);

        ballView.init();
        ballController.init(gameContainer, stateBasedGame, secondPlayer);
        addEntity(ballModel);

        ballIdCounter++;

        return ballModel;
    }

    /**
     * Adds a player stick to the game
     *
     * @param stateBasedGame game instance
     * @param position start position
     * @return the created stick
     * @throws SlickException if the stick image cannot be loaded
     */
    private StickModel addStick(StateBasedGame stateBasedGame, int position, PlayerModel owner) throws SlickException {
        boolean secondPlayer = owner.isSecondPlayer();

        if (!secondPlayer || players.length == 2) {
            String stickId = (secondPlayer) ? GameParameters.STICK_ID_PLAYER2 : GameParameters.STICK_ID;
            StickModel stickModel = new StickModel(stickId, owner);
            StickController stickController = new StickController(stickId + GameParameters.EXT_CONTROLLER);
            stickModel.addComponent(stickController);
            StickRenderComponent stickRenderComponent = new StickRenderComponent();
            stickModel.addComponent(stickRenderComponent);

            owner.setStickController(stickController);

            stickController.init(stateBasedGame, position);
            stickRenderComponent.init();
            addEntity(stickModel);

            return stickModel;
        } else {
            logger.error("Some error occurred while loading second stick: Player count is " + players.length);
            return null;
        }
    }

    /**
     * @return the shared ram block movement controller
     */
    public RamBlockMovementController getRBMC(){
        return ramBlockMovementController;
    }

    /**
     * Get all playing player. In case of multiplayer this list will be bigger than 1.
     *
     * @return all currently playing players
     */
    public PlayerModel[] getPlayers() {
        return players;
    }

    /**
     * @return the shared map controller
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Gets the current game speed which can be slower and faster than the default.
     *
     * @return < 1f slower >1f faster
     */
    public float getGameSpeedFactor() {
        return gameSpeedFactor;
    }

    /**
     * Sets the current game speed which can be slower and faster than the default.
     *
     * @param gameSpeedFactor < 1f slower >1f faster
     */
    public void setGameSpeedFactor(float gameSpeedFactor) {
        this.gameSpeedFactorGoal = gameSpeedFactor;
    }

    /**
     * @return all balls that are ingame.
     */
    public List<BallModel> getBalls() {
        return balls;
    }

    /**
     * @return shared clock controller
     */
    public ClockController getClockController() {
        return clockController;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.startAsMultiplayer = multiplayer;
    }

    public boolean isMultiplayer() {
        return (this.players.length == 2);
    }

    /**
     * Check if particle effects should be displayed
     *
     * @return true if it's enabled
     */
    public boolean isParticleEffectsEnabled() {
        return particleEffects;
    }

    /**
     * Enable or disable particle effects
     *
     * @param particleEffects true if it should be enabled
     */
    public void setParticleEffectsEnabled(boolean particleEffects) {
        this.particleEffects = particleEffects;
    }

    public boolean isManuallyPaused() {
        return manuallyPaused;
    }

    public void setManuallyPaused(boolean manuallyPaused) {
        this.manuallyPaused = manuallyPaused;
    }
}
