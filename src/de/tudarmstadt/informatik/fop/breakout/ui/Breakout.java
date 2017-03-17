package de.tudarmstadt.informatik.fop.breakout.ui;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.controllers.HighScoreController;
import de.tudarmstadt.informatik.fop.breakout.controllers.SoundController;
import de.tudarmstadt.informatik.fop.breakout.models.SoundType;
import de.tudarmstadt.informatik.fop.breakout.states.*;

import eea.engine.entity.StateBasedEntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Breakout extends StateBasedGame implements GameParameters {

	private static final Logger logger = LogManager.getLogger();
    // Remember if the game runs in debug mode
    private static boolean debug = false;

    public static boolean getDebug() {
        return debug;
    }

    private final SoundController soundController = new SoundController();
    private final HighScoreController highScoreController = new HighScoreController();

    /**
     * Creates a new Breakout instance
     *
     * @param debug if true, runs in debug mode
     */
    public Breakout(boolean debug) {
        super("Breakout");
        Breakout.debug = debug;
    }

    public static void main(String[] args) throws SlickException {
    	try{
	        // Set the library path depending on the operating system
	        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
	            logger.info("Using windows");
	        	System.setProperty("org.lwjgl.librarypath",
	        			 System.getProperty("user.dir") + "/native/windows");
	        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
	        	logger.info("Using mac");
	            System.setProperty("org.lwjgl.librarypath",
	                    System.getProperty("user.dir") + "/native/macosx");
	        } else {
	        	logger.info("Using linux/bsd");
	            System.setProperty("org.lwjgl.librarypath",
	                    System.getProperty("user.dir") + "/native/"
	                            + System.getProperty("os.name").toLowerCase());
	        }

	        // Add this StateBasedGame to an AppGameContainer
            Breakout game = new Breakout(false);
            AppGameContainer app = new AppGameContainer(game);

	        // Set the display mode and frame rate
	        app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
	        app.setTargetFrameRate(FRAME_RATE);

	        // now start the game!
	        app.start();
    	}catch(Exception e){
    		logger.error(e);
    	}
    }

    @Override
    public boolean closeRequested() {
        //release resources to gracefully free the native data
        soundController.close();

        return super.closeRequested();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        //load the sounds here in order to override the default sound volume from slick
        initMusic(container);

        // Add the game states (the first added state will be started initially)
        // This may look as follows, assuming you use the associated class names and constants:
        addState(new MainMenuState(MAINMENU_STATE));
        addState(new GameplayState(GAMEPLAY_STATE));
        addState(new HighscoreState(HIGHSCORE_STATE));
        addState(new CreditsState(CREDITS_STATE));
        addState(new GameoverState(GAMEOVER_STATE));
        addState(new SettingsState(SETTINGS_STATE));

        // Add the states to the StateBasedEntityManager
        StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEPLAY_STATE);
        StateBasedEntityManager.getInstance().addState(HIGHSCORE_STATE);
        StateBasedEntityManager.getInstance().addState(CREDITS_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEOVER_STATE);
        StateBasedEntityManager.getInstance().addState(SETTINGS_STATE);
    }

    /**
     * Load and play background music.
     *
     * @param container the game container
     */
    private void initMusic(GameContainer container) {
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
