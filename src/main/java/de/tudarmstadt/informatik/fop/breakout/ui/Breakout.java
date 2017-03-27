package de.tudarmstadt.informatik.fop.breakout.ui;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.controllers.SoundController;
import de.tudarmstadt.informatik.fop.breakout.exceptions.IllegalHighscoreFormat;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.states.*;
import de.tudarmstadt.informatik.fop.breakout.states.submenu.CreditsState;
import de.tudarmstadt.informatik.fop.breakout.states.submenu.HighscoreState;
import de.tudarmstadt.informatik.fop.breakout.states.submenu.SettingsState;
import eea.engine.entity.StateBasedEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class Breakout extends StateBasedGame implements GameParameters {

    private static final Logger logger = LogManager.getLogger();
    // Remember if the game runs in debug mode
    private static boolean debug = false;

    public static boolean getDebug() {
        return debug;
    }

    private final SoundController soundController = new SoundController();
    private final HighScoreController highScoreController = new HighScoreController();

    private final int initialLevelId;

    /**
     * Creates a new Breakout instance with a preset map id
     *
     * @param debug          if true, runs in debug mode
     * @param initialLevelId the map that should been load initially
     */
    public Breakout(boolean debug, int initialLevelId) {
        super("Breakout");
        Breakout.debug = debug;

        this.initialLevelId = initialLevelId;
    }

    /**
     * Creates a new Breakout instance
     *
     * @param debug if true, runs in debug mode
     */
    public Breakout(boolean debug) {
        this(debug, GameParameters.MAP_INITIAL_ID);
    }

    public static void main(String[] args) throws SlickException {
        String nativePath = System.getProperty("user.dir") + "/native/";
        String nativePathOsExt = System.getProperty("os.name").toLowerCase();

        // Set the library path depending on the operating system
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            logger.info("Using windows");
            nativePathOsExt = "windows";
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            logger.info("Using mac");
            nativePathOsExt = "macosx";
        } else {
            logger.info("Using linux/bsd");
        }

        try {
            System.setProperty("org.lwjgl.librarypath", nativePath + nativePathOsExt);
            System.setProperty("net.java.games.input.librarypath", nativePath + nativePathOsExt);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Breakout.class.getResourceAsStream("/fonts/ufonts.com_poplar.ttf")));

            // Is a specific level set as run parameter?
            int initialLevelId = GameParameters.MAP_INITIAL_ID;
            for (String arg : args) {
                if (arg.startsWith("--level=")) {
                    try {
                        initialLevelId = Integer.valueOf(arg.split("=")[1]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        logger.error("Can't parse given level id to int");
                    }
                }
            }

            // Add this StateBasedGame to an AppGameContainer
            Breakout game = new Breakout(false, initialLevelId);
            AppGameContainer app = new AppGameContainer(game);

            // Set the display mode and frame rate
            app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            app.setTargetFrameRate(FRAME_RATE);

            app.setShowFPS(false);

            app.setIcons(GameParameters.ICON);

            // now start the game!
            app.start();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public boolean closeRequested() {
        //release resources to gracefully free the native data
        soundController.close();

        try {
            highScoreController.saveToFile();
        } catch (IOException ex) {
            logger.error("Error saving highscores to file {}", ex);
        }

        return super.closeRequested();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        //load the sounds here in order to override the default sound volume from slick
        initMusic(container);

        //load highscores from file
        loadHighscores();

        // Add the game de.tudarmstadt.informatik.fop.breakout.states (the first added state will be started initially)
        // This may look as follows, assuming you use the associated class names and de.tudarmstadt.informatik.fop.breakout.constants:
        addState(new MainMenuState(MAINMENU_STATE));
        addState(new GameplayState(GAMEPLAY_STATE, initialLevelId));
        addState(new HighscoreState(HIGHSCORE_STATE));
        addState(new CreditsState(CREDITS_STATE));
        addState(new GameoverState(GAMEOVER_STATE));
        addState(new SettingsState(SETTINGS_STATE));

        // Add the de.tudarmstadt.informatik.fop.breakout.states to the StateBasedEntityManager
        StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEPLAY_STATE);
        StateBasedEntityManager.getInstance().addState(HIGHSCORE_STATE);
        StateBasedEntityManager.getInstance().addState(CREDITS_STATE);
        StateBasedEntityManager.getInstance().addState(SETTINGS_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEOVER_STATE);
    }

    private void loadHighscores() {
        try {
            highScoreController.loadFromFile();
        } catch (IOException e) {
            logger.error("Error loading highscores from file");
        } catch (IllegalHighscoreFormat illegalHighscoreFormat) {
            logger.error("Error loading highscores, invalid file format");
        }
    }

    /**
     * Load and play background music.
     *
     * @param container the game container
     */
    private void initMusic(GameContainer container) {
        if (debug) {
            return;
        }

        soundController.load(SoundType.BACKGROUND_MUSIC);
        soundController.playMusic(SoundType.BACKGROUND_MUSIC);

        container.setMusicVolume(GameParameters.DEFAULT_VOLUME);
        container.setSoundVolume(GameParameters.DEFAULT_VOLUME);
    }

    /**
     * Get the shared instance of handling the highscores.
     *
     * @return highscore controller
     */
    public HighScoreController getHighScoreController() {
        return highScoreController;
    }

    public SoundController getSoundController() {
        return soundController;
    }
}
