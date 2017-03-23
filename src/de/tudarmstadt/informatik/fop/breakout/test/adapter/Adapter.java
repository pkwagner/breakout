package de.tudarmstadt.informatik.fop.breakout.test.adapter;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.game.MapController;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import de.tudarmstadt.informatik.fop.breakout.models.game.BallModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.PlayerModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.StickModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.AbstractBlockModel;
import de.tudarmstadt.informatik.fop.breakout.models.game.blocks.SimpleBlock;
import de.tudarmstadt.informatik.fop.breakout.states.GameplayState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import de.tudarmstadt.informatik.fop.breakout.util.Utility;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.test.TestAppGameContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Adapter implements GameParameters {

    /*
     * the instance of our game, extends StateBasedGame
     */
    private Breakout breakout;

    /**
     * The TestAppGameContainer for running the tests
     */
    private TestAppGameContainer app;

    private StickModel stickModel;
    private PlayerModel playerModel = new PlayerModel(GameParameters.PLAYER_ID, false);
    private BallModel ballModel = new BallModel(GameParameters.BALL_ID, playerModel);
    private MapController mapController;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Use this constructor to initialize everything you need.
     */
    public Adapter() {
        breakout = new Breakout(true);
    }

	/* ***************************************************
     * ********* initialize, run, stop the game **********
	 * ***************************************************
	 *
	 * You can normally leave this code as it is.
	 */

    public StateBasedGame getStateBasedGame() {
        return breakout;
    }

    /**
     * Diese Methode initialisiert das Spiel im Debug-Modus, d.h. es wird ein
     * AppGameContainer gestartet, der keine Fenster erzeugt und aktualisiert.
     * <p>
     * Sie müssen diese Methode erweitern
     */
    public void initializeGame() {
        // Set the library path depending on the operating system
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("org.lwjgl.librarypath",
                    System.getProperty("user.dir") + "/native/windows");
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("org.lwjgl.librarypath",
                    System.getProperty("user.dir") + "/native/macosx");
        } else {
            System.setProperty("org.lwjgl.librarypath",
                    System.getProperty("user.dir") + "/native/"
                            + System.getProperty("os.name").toLowerCase());
        }

        // Initialize the game in debug mode (no GUI output)
        breakout = new Breakout(true, 0);

        ballModel.setVelocity(new Vector2f(1, 1));
        ballModel.setPosition(new Vector2f(GameParameters.WINDOW_WIDTH / 2, GameParameters.WINDOW_HEIGHT / 2));

        try {
            app = new TestAppGameContainer(breakout, GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT, false);
            app.start(0);

            // Start new game
            GameplayState gameplayState = ((GameplayState) breakout.getState(GameParameters.GAMEPLAY_STATE));
            gameplayState.newGame(false);
            mapController = gameplayState.getMapController();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop a running game
     */
    public void stopGame() {
        if (app != null) {
            app.exit();
            app.destroy();
        }

        StateBasedEntityManager.getInstance().clearAllStates();
        breakout = null;
    }

    public void changeToGameplayState() {
        this.getStateBasedGame().enterState(GAMEPLAY_STATE);
        try {
            app.updateGame(1);
        } catch (SlickException e) {
            e.printStackTrace();
        }


        GameplayState state = (GameplayState) breakout.getState(GameParameters.GAMEPLAY_STATE);

        playerModel = state.getPlayers()[0];
        stickModel = (StickModel) StateBasedEntityManager.getInstance().getEntity(GAMEPLAY_STATE, GameParameters.STICK_ID);
        ballModel = state.getBalls().get(0);

        setSpeed(0.3f);
        stickModel.setVelocity(new Vector2f(0.5f, 0));
    }

    public void changeToHighScoreState() {
        this.getStateBasedGame().enterState(HIGHSCORE_STATE);
        try {
            app.updateGame(1);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

	/* ***************************************************
     * ********************** Ball **********************
	 * ***************************************************
	 */

    /**
     * Returns a new Entity that represents a ball with ID ballID.
     * It was added for tests, as we do not know what class/package will represent
     * your "ball" entity.
     *
     * @param ballID the ID for the new ball instance
     * @return an entity representing a ball with the ID passed in as ballID
     */
    public Entity createBallInstance(String ballID) {
        BallModel ballModel = new BallModel(ballID, new PlayerModel("DUMMY", false));
        ballModel.setVelocity(new Vector2f(1, 1));
        return ballModel;
    }

    /**
     * Returns an instance of the IHitable interface that represents a block
     * with the ID as passed in and the requested number of hits left (1 = next
     * hit causes the block to vanish, 2 = it takes two hits, ...)
     *
     * @param blockID            the ID the returns block entity should have
     * @param hitsUntilDestroyed the number of hits (> 0) the block should have left
     *                           before it vanishes (1 = vanishes with next touch by ball)
     * @return an entity representing a block with the given ID and hits left
     */
    public IHitable createBlockInstance(String blockID, int hitsUntilDestroyed) {
        // and that requires hitsUntilDestroyed "hits" until it vanishes
        System.out.println("CREATE BLOCK");
        return new SimpleBlock(blockID, hitsUntilDestroyed);
    }

    /**
     * sets the ball's orientation (angle).
     * Note: the name of the method is somewhat unfortunate, but is taken from EEA's entity.
     *
     * @param i the new orientation angle for the ball (0...360)
     */
    public void setRotation(int i) {
        ballModel.setRotation(i);
        ballModel.setVelocityRotation(i);
    }

    /**
     * returns the ball's orientation (angle).
     * Note: the name of the method is somewhat unfortunate, but is taken from EEA's entity.
     *
     * @return the orientation angle for the ball (0...360)
     */
    public float getRotation() {
        return ballModel.getRotation();
    }

    /**
     * Sets the ball's position to the coordinate provide
     *
     * @param vector2f the target position for the ball
     */
    public void setPosition(Vector2f vector2f) {
        ballModel.setPosition(vector2f);
    }

    /**
     * returns a definition of the ball's size. Typically, the size of the ball will
     * be constant, but programmers may introduce bonus items that shrink or enlarge the ball.
     *
     * @return the size of the ball
     */
    public Vector2f getSize() {
        Shape shape = ballModel.getShape();
        return new Vector2f(shape.getWidth(), shape.getHeight());
    }

    /**
     * returns the current speed of the ball's movement
     *
     * @return the ball's speed
     */
    public float getSpeed() {
        // Round 0.001
        return Utility.round(ballModel.getVelocity().length(), 3);
    }

    /**
     * sets the current speed of the ball to the given value
     *
     * @param speed the new speed of the ball
     */
    public void setSpeed(float speed) {
        ballModel.getVelocity().normalise().scale(speed);
    }

    /**
     * provide a proper code mapping to a check if your ball entity collides with
     * 'otherEntity'. You will have to access your ball instance for this purpose.
     *
     * @param otherEntity another entity that the ball may (or may not) collide with
     * @return true if the two entities have collided. Note: your ball should by default
     * not collide with itself (or other balls, if there are any), null, the background,
     * or "passable" entities (e.g. other image you have placed on the screen). It should only
     * collide with the stick if the orientation is correct (>90 but <270 degrees, else it would
     * "collide with the underside of the stick") but should be "gone" then already).
     * It should also collide with the borders if the orientation is correct for this, e.g.,
     * only collide with the top border if the orientation is fitting).
     */
    public boolean collides(Entity otherEntity) {
        logger.debug("x before: " + ballModel.getShape().getMinX());
        ballModel.step();
        logger.debug("x after: " + ballModel.getShape().getMinX());
        boolean collides = ballModel.collides(otherEntity);
        ballModel.stepBackwards();
        return collides;
    }

	/* ***************************************************
     * ********************** Player *********************
	 * ***************************************************
	 */

    /**
     * ensures that the player has "value" additional lives (=additional balls left).
     *
     * @param value the number of additional balls/lives to be added.
     */
    public void addLives(int value) {
        playerModel.addHealthpoints(value);
    }

    /**
     * ensures that the player has exactly "playerLives" balls/lives left.
     *
     * @param playerLives the number of lives/balls the player shall have left
     */
    public void setLives(int playerLives) {
        playerModel.setRemainingHealthpoints(playerLives);
    }

    /**
     * queries your classes for the number of lives/balls the player has left
     *
     * @return the number of lives/balls left
     */
    public int getLivesLeft() {
        return playerModel.getRemainingHealthpoints();
    }

    /**
     * checks if the player still has at least one live/ball left
     *
     * @return true if the player still has at least one live/ball left, else false.
     */
    public boolean hasLivesLeft() {
        return !playerModel.isDead();
    }

	/* ***************************************************
	 * ********************** Block **********************
	 * ***************************************************
	 */

    /**
     * Sets a number of necessary hits for degrading this block
     *
     * @param hitsLeft number of necessary hits
     * @param blockID  blockID ID of the chosen block
     */
    public void setHitsLeft(int hitsLeft, String blockID) {
        logger.info("testing block: " + blockID);
        mapController.getBlock(blockID).setHitsLeft(hitsLeft);
    }

    /**
     * Returns the number of necessary hits for degrading this block
     *
     * @param blockID ID of the chosen block
     * @return number of hits
     */
    public int getHitsLeft(String blockID) {
        logger.info("testing block (getHitsLeft): " + blockID);
        AbstractBlockModel block = mapController.getBlock(blockID);

        return block.getHitsLeft();
    }

    /**
     * Adds a number of necessary hits for degrading this block
     *
     * @param hitsLeft number of hits added
     * @param blockID  blockID ID of the chosen block
     */
    public void addHitsLeft(int hitsLeft, String blockID) {
        mapController.getBlock(blockID).addHitsLeft(hitsLeft);
    }

    /**
     * Returns whether the block has hits left
     *
     * @param blockID blockID ID of the chosen block
     * @return true, if block has hits left, else false
     */
    public boolean hasHitsLeft(String blockID) {
        logger.info("testing block (hasHitsLeft): " + blockID);
        return mapController.getBlock(blockID) != null;
    }

	/* ***************************************************
	 * ********************** StickModel **********************
	 * ***************************************************
	 */

    /**
     * returns the current position of the stick
     *
     * @return the current position of the stick
     */
    public Vector2f getStickPosition() {
        return stickModel.getPosition();
    }

	/* ***************************************************
	 * ********************** Input **********************
	 * ***************************************************
	 */

    /**
     * This Method should emulate the key down event.
     *
     * @param updatetime : Zeitdauer bis update-Aufruf
     * @param input      : z.B. Input.KEY_K, Input.KEY_L
     */
    public void handleKeyDown(int updatetime, Integer input) {
        app.getTestInput().setKeyDown(input);
        // note: do not forget to call app.updateGame(updatetime);
        try {
            app.updateGame(updatetime);
        } catch (SlickException e) {
            //we are not allowed to change the method signature because then we would have to change the given Tests
            throw new RuntimeException(e);
        }
    }

    /**
     * This Method should emulate the pressing of the right arrow key.
     */
    public void handleKeyDownRightArrow() {
        // hint: you may use the above method.
        handleKeyDown(401, Input.KEY_RIGHT);
    }

    /**
     * This Method should emulate the pressing of the left arrow key.
     */
    public void handleKeyDownLeftArrow() {
        // hint: you may use the above method.
        handleKeyDown(401, Input.KEY_LEFT);
    }
}
